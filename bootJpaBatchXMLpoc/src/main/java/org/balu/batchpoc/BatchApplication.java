package org.balu.batchpoc;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableBatchProcessing
@ImportResource("classpath:batch-config.xml")
public class BatchApplication {
	public static void main(String[] args) {
		 SpringApplication.run(BatchApplication.class, args);
	}
}
