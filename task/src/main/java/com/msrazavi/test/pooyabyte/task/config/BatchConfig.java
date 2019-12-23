package com.msrazavi.test.pooyabyte.task.config;

import com.msrazavi.test.pooyabyte.common.schema.entity.Request;
import com.msrazavi.test.pooyabyte.task.model.TransferStepDto;
import com.msrazavi.test.pooyabyte.task.service.step.setnext.SetNextProcessor;
import com.msrazavi.test.pooyabyte.task.service.step.setnext.SetNextReader;
import com.msrazavi.test.pooyabyte.task.service.step.setnext.SetNextWriter;
import com.msrazavi.test.pooyabyte.task.service.step.transfer.TransferProcessor;
import com.msrazavi.test.pooyabyte.task.service.step.transfer.TransferReader;
import com.msrazavi.test.pooyabyte.task.service.step.transfer.TransferWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

/**
 * @author Mehdi Sadat Razavi
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private static final Logger LOGGER = LogManager.getLogger(BatchConfig.class);

    private final JobBuilderFactory jobs;
    private final StepBuilderFactory steps;

    public BatchConfig(JobBuilderFactory jobs,
                       StepBuilderFactory steps) {
        EntryMessage traceEntry = LOGGER.traceEntry("JobBuilderFactory: {}, StepBuilderFactory: {}", jobs, steps);
        this.jobs = jobs;
        this.steps = steps;
        LOGGER.traceExit(traceEntry);
    }

    /**
     * @param itemReader    reader an item reader
     * @param itemProcessor processor an item processor
     * @param itemWriter    writer an item writer
     * @return transferStep
     */
    @Bean
    protected Step transferStep(TransferReader itemReader,
                                TransferProcessor itemProcessor,
                                TransferWriter itemWriter) {
        EntryMessage traceEntry = LOGGER.traceEntry("itemReader: {}, itemProcessor: {}, itemWriter: {}", itemReader, itemProcessor, itemWriter);
        TaskletStep step = steps.get("transferStep").<Request, TransferStepDto>chunk(30)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .faultTolerant()
                .retryLimit(5)
                .retry(Exception.class)
                .taskExecutor(taskExecutor())
                .build();
        return LOGGER.traceExit(traceEntry, step);
    }

    /**
     * step to fix next date of request entity
     *
     * @param itemReader    reader an item reader
     * @param itemProcessor processor an item processor
     * @param itemWriter    writer an item writer
     * @return setNextDate Step
     */
    @Bean
    protected Step setNextStep(SetNextReader itemReader,
                               SetNextProcessor itemProcessor,
                               SetNextWriter itemWriter) {
        EntryMessage traceEntry = LOGGER.traceEntry("itemReader: {}, itemProcessor: {}, itemWriter: {}", itemReader, itemProcessor, itemWriter);
        TaskletStep step = steps.get("setNextStep").<Request, Request>chunk(30)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .faultTolerant()
                .retryLimit(5)
                .retry(Exception.class)
                .taskExecutor(taskExecutor())
                .build();
        return LOGGER.traceExit(traceEntry, step);
    }

    /**
     * create transferJob, this job has two step: transferStep & setNextStep
     *
     * @param transferStep step to insert voucher
     * @param setNextStep  step to fix next date of request entity
     * @return transferJob to run
     */
    @Bean
    public Job transferJob(Step transferStep,
                           Step setNextStep) {
        EntryMessage traceEntry = LOGGER.traceEntry("transferStep: {}, setNextStep: {}", transferStep, setNextStep);
        Job job = jobs
                .get("transferJob")
                .start(transferStep)
                .next(setNextStep)
                .build();
        return LOGGER.traceExit(traceEntry, job);
    }

    /**
     * for run job in concurrently
     *
     * @return TaskExecutor
     */
    @Bean
    public TaskExecutor taskExecutor() {
        EntryMessage traceEntry = LOGGER.traceEntry();
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor("spring_batch");
        taskExecutor.setConcurrencyLimit(5);
        return LOGGER.traceExit(traceEntry, taskExecutor);
    }
}
