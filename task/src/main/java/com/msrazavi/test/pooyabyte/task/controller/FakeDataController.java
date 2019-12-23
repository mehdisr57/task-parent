package com.msrazavi.test.pooyabyte.task.controller;

import com.msrazavi.test.pooyabyte.common.schema.dto.AccountDto;
import com.msrazavi.test.pooyabyte.common.schema.dto.RequestDto;
import com.msrazavi.test.pooyabyte.common.schema.dto.ResponseDto;
import com.msrazavi.test.pooyabyte.common.schema.enums.Course;
import com.msrazavi.test.pooyabyte.common.service.AccountService;
import com.msrazavi.test.pooyabyte.common.service.RequestService;
import com.msrazavi.test.pooyabyte.task.util.NextDateCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is used to generate random data
 *
 * @author Mehdi Sadat Razavi
 */
@RestController
@RequestMapping("/rest/api/v1/fake")
public class FakeDataController {
    private static final Logger LOGGER = LogManager.getLogger(FakeDataController.class);

    private static final int THREAD_COUNT = 4;
    private final RequestService requestService;
    private final AccountService accountService;

    public FakeDataController(@Qualifier("taskRequestServiceImpl") RequestService requestService,
                              AccountService accountService) {
        this.requestService = requestService;
        this.accountService = accountService;
    }

    /**
     * create fake records in the Request table with the number of input parameters
     *
     * @param count of record must be inserted
     * @return success or error message
     */
    @GetMapping("/{count}")
    public ResponseEntity<ResponseDto<String>> create(@PathVariable("count") Integer count) {
        EntryMessage traceEntry = LOGGER.traceEntry();
        if (Objects.isNull(count)) {
            count = 10_000;
        }
        Iterable<AccountDto> accounts = accountService.findAll();
        List<AccountDto> accountList = new ArrayList<>();
        for (AccountDto account : accounts) {
            accountList.add(account);
        }

        ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);
        Batch batch1 = new Batch(requestService, accountList, count / THREAD_COUNT);
        Batch batch2 = new Batch(requestService, accountList, count / THREAD_COUNT);
        Batch batch3 = new Batch(requestService, accountList, count / THREAD_COUNT);
        Batch batch4 = new Batch(requestService, accountList, count / THREAD_COUNT);
        service.submit(batch1);
        service.submit(batch2);
        service.submit(batch3);
        service.submit(batch4);
        return LOGGER.traceExit(traceEntry, ResponseEntity.ok(ResponseDto.ofSuccess("Start to add " + count + " record")));
    }

    private class Batch implements Runnable {

        private final RequestService requestService;
        private final List<AccountDto> accountList;
        private final long count;
        private Random random = new Random();

        Batch(RequestService requestService, List<AccountDto> accountList, long count) {
            this.requestService = requestService;
            this.accountList = accountList;
            this.count = count;
        }

        /**
         * Generates and stores random records
         */
        @Override
        public void run() {
            EntryMessage traceEntry = LOGGER.traceEntry();

            Course[] courses = Course.values();
            RequestDto request;
            for (int i = 0; i < count; i++) {
                request = new RequestDto();
                int[] a = randomAccount();
                request.setFrom(accountList.get(a[0]));
                request.setTo(accountList.get(a[1]));
                request.setCourse(courses[randBetween(0, courses.length - 1)]);
                request.setStartDate(randDate());
                request.setAmount(new BigDecimal(random.nextDouble()));
                NextDateCalculator calculator = new NextDateCalculator(request.getCourse(), request.getStartDate(), request.getLastDate());
                request.setNextDate(calculator.getNextDate());
                requestService.save(request);
            }
            LOGGER.traceExit(traceEntry);
        }

        /**
         * Creates two different random accounts
         *
         * @return two account
         */
        private int[] randomAccount() {
            int f = randBetween(0, accountList.size() - 1);
            int t = -1;
            boolean repeat = true;
            while (repeat) {
                t = randBetween(0, accountList.size() - 1);
                if (f != t) {
                    repeat = false;
                }
            }
            return new int[]{f, t};
        }

        /**
         * @return random date between 2017-2020
         */
        private Date randDate() {
            EntryMessage traceEntry = LOGGER.traceEntry();
            GregorianCalendar gc = new GregorianCalendar();
            int year = randBetween(2017, 2020);
            gc.set(Calendar.YEAR, year);
            int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
            gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
            return LOGGER.traceExit(traceEntry, gc.getTime());
        }

        /**
         * Generates a random number between two period;
         * this number can be equal to two period
         *
         * @param start of period
         * @param end   of period
         * @return random number
         */
        private int randBetween(int start, int end) {
            EntryMessage traceEntry = LOGGER.traceEntry("start: {}, end: {}", start, end);
            int result = start + (int) Math.round(Math.random() * (end - start));
            return LOGGER.traceExit(traceEntry, result);
        }
    }
}
