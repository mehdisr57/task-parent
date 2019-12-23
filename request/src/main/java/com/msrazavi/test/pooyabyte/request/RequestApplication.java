package com.msrazavi.test.pooyabyte.request;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.msrazavi.test.pooyabyte.common", "com.msrazavi.test.pooyabyte.request"})
@SpringBootApplication
public class RequestApplication {
    private static final Logger LOGGER = LogManager.getLogger(RequestApplication.class);

    public static void main(String[] args) {
        EntryMessage traceEntry = LOGGER.traceEntry();
        SpringApplication.run(RequestApplication.class);
        LOGGER.traceExit(traceEntry);
    }
}
