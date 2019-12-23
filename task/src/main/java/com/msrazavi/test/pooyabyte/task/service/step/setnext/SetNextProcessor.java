package com.msrazavi.test.pooyabyte.task.service.step.setnext;

import com.msrazavi.test.pooyabyte.common.schema.entity.Request;
import com.msrazavi.test.pooyabyte.task.util.NextDateCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * add next date to request
 *
 * @author Mehdi Sadat Razavi
 */
@Service
public class SetNextProcessor implements ItemProcessor<Request, Request> {

    private static final Logger LOGGER = LogManager.getLogger(SetNextProcessor.class);

    @Override
    public Request process(Request request) {
        EntryMessage traceEntry = LOGGER.traceEntry("Request: {}", request);
        if (Objects.isNull(request)) {
            return null;
        }

        try {
            NextDateCalculator calculator = new NextDateCalculator(request.getCourse(), request.getStartDate(), request.getLastDate());
            request.setNextDate(calculator.getNextDate());
        } catch (Exception e) {
            LOGGER.error("error on process - " + e.getMessage(), e);
        }

        return LOGGER.traceExit(traceEntry, request);
    }
}
