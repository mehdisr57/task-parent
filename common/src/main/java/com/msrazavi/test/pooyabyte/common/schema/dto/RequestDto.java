package com.msrazavi.test.pooyabyte.common.schema.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.msrazavi.test.pooyabyte.common.schema.enums.Course;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @author Mehdi Sadat Razavi
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = RequestDto.class, property = "id")
public class RequestDto extends BaseDto {

    @NotNull
    private AccountDto from;
    @NotNull
    private AccountDto to;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Course course;
    @NotNull
    private Date startDate;
    private Date lastDate;
    private Date nextDate;
    private Set<VoucherDto> vouchers;

    public AccountDto getFrom() {
        return from;
    }

    public void setFrom(AccountDto from) {
        this.from = from;
    }

    public AccountDto getTo() {
        return to;
    }

    public void setTo(AccountDto to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getNextDate() {
        return nextDate;
    }

    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public Set<VoucherDto> getVouchers() {
        return vouchers;
    }

    public void setVouchers(Set<VoucherDto> vouchers) {
        this.vouchers = vouchers;
    }
}
