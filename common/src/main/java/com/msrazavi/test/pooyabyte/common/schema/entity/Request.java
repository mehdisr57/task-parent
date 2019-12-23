package com.msrazavi.test.pooyabyte.common.schema.entity;

import com.msrazavi.test.pooyabyte.common.schema.converter.CourseConverter;
import com.msrazavi.test.pooyabyte.common.schema.enums.Course;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @author Mehdi Sadat Razavi
 */
@Entity
@Table(name = "REQUEST")
public class Request extends BaseEntity {

    private Account from;
    private Account to;
    private BigDecimal amount;
    private Course course;
    private Date startDate;
    private Date nextDate;
    private Date lastDate;
    private Set<Voucher> vouchers;

    @JoinColumn(name = "FROM_ACCOUNT", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public Account getFrom() {
        return from;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    @JoinColumn(name = "TO_ACCOUNT", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public Account getTo() {
        return to;
    }

    public void setTo(Account to) {
        this.to = to;
    }

    @Column(name = "AMOUNT", scale = 2, precision = 10, nullable = false)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Convert(converter = CourseConverter.class)
    @Column(name = "COURSE", nullable = false, length = 2)
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "NEXT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getNextDate() {
        return nextDate;
    }

    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }

    @Column(name = "LAST_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    @OneToMany(mappedBy = "request")
    public Set<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(Set<Voucher> vouchers) {
        this.vouchers = vouchers;
    }
}
