package com.learnbatch;

import com.learnbatch.constants.SpringBatchMultiThreadingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBatchMultithreadingApplication {

	private static final Logger log= LoggerFactory.getLogger(SpringBatchMultithreadingApplication.class);


	public static void main(String[] args) {

		{

			try{
				ConfigurableApplicationContext ctx = SpringApplication.run(SpringBatchMultithreadingApplication.class, args);

				JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
				Job job = ctx.getBean(SpringBatchMultiThreadingConstants.MULTI_THREAD_JOB, Job.class);

				JobExecution execution = jobLauncher.run(job, new JobParametersBuilder().addLong("timestamp", System.currentTimeMillis()).
						toJobParameters());

				BatchStatus batchStatus = execution.getStatus();

				ExitStatus exitStatus = execution.getExitStatus();
				String exitCode = exitStatus.getExitCode();
				log.info(String.format("*********** Exit status: %s", exitCode));


				JobInstance jobInstance = execution.getJobInstance();
				log.info(String.format("********* Name of the job %s", jobInstance.getJobName()));

				log.info(String.format("*********** job instance Id: %d", jobInstance.getId()));
			}catch(Exception e){
				log.error("Exception in main method : ShipmentLoaderApplication : " + e);
			}finally {
				System.exit(0);
			}

		}
	}
}
