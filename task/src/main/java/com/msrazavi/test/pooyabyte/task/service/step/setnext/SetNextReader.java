package com.msrazavi.test.pooyabyte.task.service.step.setnext;

import com.msrazavi.test.pooyabyte.common.repository.RequestRepository;
import com.msrazavi.test.pooyabyte.common.schema.entity.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Mehdi Sadat Razavi
 */
@Service
public class SetNextReader implements ItemReader<Request> {
    private static final Logger LOGGER = LogManager.getLogger(SetNextReader.class);
    private static final int READ_PAGE_SIZE = 10_000;

    private static Queue<Request> items = new ConcurrentLinkedQueue<>();
    private final RequestRepository requestRepository;
    private Date endDate;

    public SetNextReader(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;

    }

    @BeforeStep
    public void init() {
        EntryMessage traceEntry = LOGGER.traceEntry();
        setDate();
        LOGGER.info("Step SetNext started - endDate is: " + endDate);
        LOGGER.traceExit(traceEntry);
    }

    private void setDate() {
        EntryMessage traceEntry = LOGGER.traceEntry();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        endDate = calendar.getTime();
        LOGGER.traceExit(traceEntry);
    }

    private synchronized void addData() {
        EntryMessage traceEntry = LOGGER.traceEntry();

        if (!items.isEmpty()) return;

        List<Request> list = requestRepository.findErrorNextDate(endDate, PageRequest.of(0, READ_PAGE_SIZE));
        if (Objects.isNull(list) || list.isEmpty()) {
            items = new ConcurrentLinkedQueue<>();
        }
        for (Request request : list) {
            items.offer(request);
        }

        LOGGER.traceExit(traceEntry);
    }

    @Override
    public Request read() {
        EntryMessage traceEntry = LOGGER.traceEntry();

        if (items.isEmpty()) {
            LOGGER.trace("collection is empty retry to get data");
            addData();
        }

        return LOGGER.traceExit(traceEntry, items.poll());
    }
}
