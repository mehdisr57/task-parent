package com.msrazavi.test.pooyabyte.task.service.step.setnext;

import com.msrazavi.test.pooyabyte.common.repository.RequestRepository;
import com.msrazavi.test.pooyabyte.common.schema.entity.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * write changed request
 *
 * @author Mehdi Sadat Razavi
 */
@Service
public class SetNextWriter implements ItemWriter<Request> {
    private static final Logger LOGGER = LogManager.getLogger(SetNextWriter.class);

    private final RequestRepository requestRepository;

    public SetNextWriter(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
        LOGGER.info("SetNextWriter started");
    }

    @AfterStep
    public void init() {
        EntryMessage traceEntry = LOGGER.traceEntry();
        LOGGER.info("Step SetNext finished");
        LOGGER.traceExit(traceEntry);
    }

    @Override
    public void write(List<? extends Request> items) {
        EntryMessage traceEntry = LOGGER.traceEntry("Requests: {}", items);
        requestRepository.saveAll(items);
        LOGGER.traceExit(traceEntry);
    }
}
