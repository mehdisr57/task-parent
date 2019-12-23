package com.msrazavi.test.pooyabyte.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Mehdi Sadat Razavi
 */
@ComponentScan(basePackages = {
        "com.msrazavi.test.pooyabyte.common",
        "com.msrazavi.test.pooyabyte.task"})
@SpringBootApplication
public class TaskServiceApplication {
    private static final Logger LOGGER = LogManager.getLogger(TaskServiceApplication.class);

    public static void main(String[] args) {
        EntryMessage traceEntry = LOGGER.traceEntry();
        SpringApplication.run(TaskServiceApplication.class);
        LOGGER.traceExit(traceEntry);
    }
}
