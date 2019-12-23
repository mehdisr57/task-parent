package com.msrazavi.test.pooyabyte.task.util;

import com.msrazavi.test.pooyabyte.common.schema.enums.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Objects;

/**
 * @author Mehdi Sadat Razavi
 */
public final class NextDateCalculator {
    private static final Logger LOGGER = LogManager.getLogger(NextDateCalculator.class);

    private final Course course;
    private final Date lastDate;
    private Date nowDate;
    private LocalDate nowLocalDate;
    private LocalDate lastLocalDate;
    private LocalDate startLocalDate;


    /**
     * Constructor
     *
     * @param course    course of request
     * @param startDate start date of request
     * @param lastDate  last date of request
     * @throws IllegalArgumentException if startDate or course is null
     */
    public NextDateCalculator(Course course, Date startDate, Date lastDate) {
        EntryMessage traceEntry = LOGGER.traceEntry("course: {}, startDate: {}, lastDate: {}", course, startDate, lastDate);
        this.course = course;
        this.lastDate = lastDate;
        ZoneId zoneId = ZoneId.systemDefault();

        if (Objects.isNull(startDate) || Objects.isNull(course)) {
            throw new IllegalArgumentException("startDate or course is null");
        }

        LocalDateTime nowDateTime = LocalDateTime.now();
        this.nowLocalDate = nowDateTime.toLocalDate();
        this.nowDate = Date.from(nowDateTime.atZone(zoneId).toInstant());

        if (Objects.nonNull(this.lastDate)) {
            this.lastLocalDate = this.lastDate.toInstant().atZone(zoneId).toLocalDate();
        }

        LocalDateTime startDateTime = startDate.toInstant().atZone(zoneId).toLocalDateTime();
        this.startLocalDate = startDateTime.toLocalDate();
        LOGGER.traceExit(traceEntry);
    }

    /**
     * create next date base on course and start date and last date if exist
     *
     * @return next date
     */
    public Date getNextDate() {
        EntryMessage traceEntry = LOGGER.traceEntry();

        Date result;
        switch (course) {
            case DAILY:
                result = getNextDaily();
                break;
            case WEEKLY:
                result = getNextWeekly();
                break;
            case MONTHLY:
                result = getNextMonthly();
                break;
            case END_OF_MONTH:
                result = getNextEndOfMonth();
                break;
            case ONCE_EVERY_TWO_WEEK:
                result = getNextOnceEveryTwoWeeks();
                break;
            default:
                throw new IllegalArgumentException("course: " + course + " not supported");
        }

        return LOGGER.traceExit(traceEntry, result);
    }

    private Date getNextOnceEveryTwoWeeks() {
        EntryMessage traceEntry = LOGGER.traceEntry();

        LocalDate resultLocalDate;
        int w2DayOfWeek = startLocalDate.get(ChronoField.DAY_OF_WEEK);
        if (Objects.isNull(lastDate)) {
            resultLocalDate = startLocalDate.plusDays(14);
        } else {
            resultLocalDate = lastLocalDate.plusDays(14);
        }

        if (resultLocalDate.compareTo(nowLocalDate) < 1) {
            resultLocalDate = nowLocalDate.with(TemporalAdjusters.next(DayOfWeek.of(w2DayOfWeek)));
        }

        return LOGGER.traceExit(traceEntry, convertToDate(resultLocalDate));
    }

    private Date getNextEndOfMonth() {
        EntryMessage traceEntry = LOGGER.traceEntry();

        LocalDate resultLocalDate;
        if (Objects.isNull(lastDate)) {
            resultLocalDate = startLocalDate.with(TemporalAdjusters.lastDayOfMonth());
        } else {
            resultLocalDate = lastLocalDate.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        }

        if (resultLocalDate.compareTo(nowLocalDate) < 1) {
            resultLocalDate = nowLocalDate.with(TemporalAdjusters.lastDayOfMonth());
        }

        return LOGGER.traceExit(traceEntry, convertToDate(resultLocalDate));
    }

    private Date getNextMonthly() {
        EntryMessage traceEntry = LOGGER.traceEntry();

        LocalDate resultLocalDate;
        if (Objects.isNull(lastDate)) {
            resultLocalDate = startLocalDate.plusMonths(1);
        } else {
            resultLocalDate = lastLocalDate.plusMonths(1);
        }

        if (resultLocalDate.compareTo(nowLocalDate) < 1) {
            resultLocalDate = LocalDate.of(nowLocalDate.getYear(), nowLocalDate.getMonth(), startLocalDate.getDayOfMonth());
            if (resultLocalDate.compareTo(nowLocalDate) < 1) {
                resultLocalDate = resultLocalDate.plusMonths(1);
            }
        }

        return LOGGER.traceExit(traceEntry, convertToDate(resultLocalDate));
    }

    private Date getNextWeekly() {
        EntryMessage traceEntry = LOGGER.traceEntry();

        LocalDate resultLocalDate;
        int wDayOfWeek = startLocalDate.get(ChronoField.DAY_OF_WEEK);
        if (Objects.isNull(lastDate)) {
            resultLocalDate = startLocalDate.with(TemporalAdjusters.next(DayOfWeek.of(wDayOfWeek)));
        } else {
            resultLocalDate = nowLocalDate.with(TemporalAdjusters.next(DayOfWeek.of(wDayOfWeek)));
        }

        if (resultLocalDate.compareTo(nowLocalDate) < 1) {
            resultLocalDate = nowLocalDate.with(TemporalAdjusters.next(DayOfWeek.of(wDayOfWeek)));
        }

        return LOGGER.traceExit(traceEntry, convertToDate(resultLocalDate));
    }

    private Date getNextDaily() {
        EntryMessage traceEntry = LOGGER.traceEntry();

        LocalDate resultLocalDate;
        if (Objects.isNull(lastDate)) {
            resultLocalDate = startLocalDate.plusDays(1);
        } else {
            resultLocalDate = lastLocalDate.plusDays(1);
        }

        if (resultLocalDate.compareTo(nowLocalDate) < 1) {
            resultLocalDate = nowLocalDate;
        }

        return LOGGER.traceExit(traceEntry, convertToDate(resultLocalDate));
    }

    private static Date convertToDate(LocalDate localDate) {
        EntryMessage traceEntry = LOGGER.traceEntry("localDate: {}", localDate);
        LocalTime localTime = LocalTime.of(1, 1, 1);
        Date result = Date.from(localDate.atTime(localTime).atZone(ZoneId.systemDefault()).toInstant());
        return LOGGER.traceExit(traceEntry, result);
    }
}
