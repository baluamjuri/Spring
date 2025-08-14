Job
Step
ItemReader<I> - read(), JpaPagingItemReader - setEntityManagerFactory(), setQueryString(), setPageSize(), setName()
ItemProcessor<I,O> - process()
ItemWriter<O> - write(), JpaItemWriter - setEntityManagerFactory()
ItemReadListener - beforeRead(), afterRead()
ItemWriteListener - beforeWrite(), afterWrite()
ItemProcessListener - beforeProcess(), afterProcess()
SkipListener<I,O> - onSkipInRead(), onSkipInWrite(), onSkipInProcess()

Job Completion Listener 
=========================
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    
    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
    
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("API Import Job completed successfully!");
        } else {
            log.error("API Import Job failed with status: " + jobExecution.getStatus());
        }
    }
}



Example:
=========
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
public class JobConfig {

	private final JobProperties jobProperties;

    public JobConfig(JobProperties jobProperties) {
        this.jobProperties = jobProperties;
    }

    @Bean
	public Job dataJob(JobRepository jobRepository,
									  Step importDataStep) {
		return new JobBuilder("dataJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.start(importDataStep)
				.build();
	}


	@Bean
	public Step importDataStep(JobRepository jobRepository,
								  DataReader dataReader,
								  DataProcessor dataProcessor,
								  DataWriter dataWriter,
								  DataSkipListener dataSkipListener,
								  PlatformTransactionManager transactionManager) {
		return new StepBuilder("importDataStep", jobRepository)
				.<DataRecord,DataRecord>chunk(jobProperties.getChunkSize(), transactionManager)
				.reader(dataReader)
				.processor(dataProcessor)
				.writer(dataWriter)
				.listener(dataReader)
				.listener(dataProcessor)
				.listener(dataWriter)
				.listener(dataSkipListener)
				.faultTolerant()
				.skip(Exception.class)
				.noSkip(MappingsNotFoundException.class)
				.skipLimit(Integer.MAX_VALUE)
				.build();
	}
}


//Retry mechanism
@Bean
public Step importDataStep() {
    return new StepBuilder("importDataStep", jobRepository)
        .<DataRecord, DataRecord>chunk(1000)
        .reader(paginatedApiItemReader(null))
        .writer(repositoryItemWriter())
        .faultTolerant()
        .skipLimit(100)
        .skip(HttpClientErrorException.class) // Skip client errors (4xx)
        .retryLimit(3)
        .retry(HttpServerErrorException.class) // Retry server errors (5xx)
        .backOffPolicy(new ExponentialBackOffPolicy() {{
            setInitialInterval(1000);
            setMultiplier(2.0);
            setMaxInterval(10000);
        }})
		.build();
}


//Parallel processing
@Bean
public Step importDataStep() {
    return new StepBuilder("importDataStep", jobRepository)
        .<DataRecord, DataRecord>chunk(1000)
        .reader(paginatedApiItemReader(null))
        .writer(repositoryItemWriter())
        .taskExecutor(taskExecutor()) // Add parallel processing
        .throttleLimit(10) // Limit concurrent threads
        .build();
}

@Bean
public TaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10);
    executor.setMaxPoolSize(20);
    executor.setQueueCapacity(30);
    return executor;
}
