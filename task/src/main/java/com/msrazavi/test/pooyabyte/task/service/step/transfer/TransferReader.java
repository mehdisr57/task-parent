package com.msrazavi.test.pooyabyte.task.service.step.transfer;

import com.msrazavi.test.pooyabyte.common.repository.RequestRepository;
import com.msrazavi.test.pooyabyte.common.schema.entity.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Mehdi Sadat Razavi
 */
@Service
public class TransferReader implements ItemReader<Request> {
    private static final Logger LOGGER = LogManager.getLogger(TransferReader.class);
    private static final int READ_PAGE_SIZE = 10_000;

    private static BlockingQueue<Request> items = new LinkedBlockingQueue<>(READ_PAGE_SIZE * 2);
    private final RequestRepository requestRepository;
    private Date endDate;
    private Date startDate;

    public TransferReader(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @BeforeStep
    public void init() {
        EntryMessage traceEntry = LOGGER.traceEntry();
        setDate();
        items.clear();
        LOGGER.info("Step Transfer started - startDate id: " + startDate + " endDate is: " + endDate);
        LOGGER.traceExit(traceEntry);
    }

    @AfterStep
    public void exit() {
        EntryMessage traceEntry = LOGGER.traceEntry();
        items.clear();
        LOGGER.info("Step Transfer finished - startDate id: " + startDate + " endDate is: " + endDate);
        LOGGER.traceExit(traceEntry);
    }

    private void setDate() {
        EntryMessage traceEntry = LOGGER.traceEntry();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate now = LocalDate.now();
        LocalDateTime start = now.atTime(0, 0, 0);
        LocalDateTime end = now.atTime(23, 59, 59);
        startDate = Date.from(start.atZone(defaultZoneId).toInstant());
        endDate = Date.from(end.atZone(defaultZoneId).toInstant());
        LOGGER.traceExit(traceEntry);
    }

    private synchronized void addData() {
        EntryMessage traceEntry = LOGGER.traceEntry();

        if (!items.isEmpty()) {
            LOGGER.trace("addData ignored ");
            return;
        }

        List<Request> list = requestRepository.findByNextDate(startDate, endDate, PageRequest.of(0, READ_PAGE_SIZE));
        if (Objects.isNull(list) || list.isEmpty()) {
            items.clear();
        }

        for (Request request : list) {
            LOGGER.trace("offer request: " + request + ", thread: " + Thread.currentThread().getName());
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


        Request request = items.poll();
        LOGGER.trace("poll request: " + request + ", thread: " + Thread.currentThread().getName());

        return LOGGER.traceExit(traceEntry, request);
    }
}
