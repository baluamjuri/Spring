package org.balu.batchpoc.listener;

import java.util.List;

import org.balu.batchpoc.model.Person;
import org.balu.batchpoc.repository.PersonDao;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("jobCompletionNotificationListener")
public class JobCompletionNotificationListener extends
		JobExecutionListenerSupport {
	
	/*@Autowired
	private JdbcTemplate jdbcTemplate;*/
	
	@Autowired
	@Qualifier("personDao")
	private PersonDao personDao;
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {

			/*List<Person> results = jdbcTemplate.query("SELECT first_name, last_name FROM person", new RowMapper<Person>() {
				@Override
				public Person mapRow(ResultSet rs, int row) throws SQLException {
					return new Person(rs.getString(1), rs.getString(2));
				}
			});
*/
			List<Person> results = (List<Person>) personDao.findAll();
			for (Person person : results) {
				System.out.println("Found <" + person + "> in the database.");
			}

		}
	}
}
