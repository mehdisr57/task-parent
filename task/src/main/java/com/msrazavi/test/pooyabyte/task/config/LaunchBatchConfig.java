package com.msrazavi.test.pooyabyte.task.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * @author Mehdi Sadat Razavi
 */
@EnableScheduling
@Configuration
public class LaunchBatchConfig {
    private static final Logger LOGGER = LogManager.getLogger(LaunchBatchConfig.class);

    private final JobLauncher jobLauncher;
    private final Job transferJob;

    public LaunchBatchConfig(JobLauncher jobLauncher,
                             Job transferJob) {
        EntryMessage traceEntry = LOGGER.traceEntry("jobLauncher: {}, transferJob: {}",
                jobLauncher, transferJob);
        this.jobLauncher = jobLauncher;
        this.transferJob = transferJob;
        LOGGER.traceExit(traceEntry);
    }

    //    @Scheduled(cron = "0 0 6 * * *")
    @Scheduled(cron = "0 0/5 * * * *")
    public void perform() throws Exception {
        EntryMessage traceEntry = LOGGER.traceEntry();

        LOGGER.info("Job Started at:" + new Date());

        JobParameters param = new JobParametersBuilder()
                .addLong("transferJob", System.nanoTime())
                .toJobParameters();

        JobExecution execution = jobLauncher.run(transferJob, param);
        LOGGER.info("Job finished with status::" + execution.getStatus());

        LOGGER.traceExit(traceEntry);
    }
}
