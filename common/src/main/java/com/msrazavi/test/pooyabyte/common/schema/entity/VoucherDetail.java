package com.msrazavi.test.pooyabyte.common.schema.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Mehdi Sadat Razavi
 */
@Entity
@Table(name = "VOUCHER_DETAIL")
public class VoucherDetail extends BaseEntity {

    private Voucher voucher;
    private Account account;
    private BigDecimal debit;
    private BigDecimal credit;
    private String description;


    @JoinColumn(name = "FK_VOUCHER", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    @JoinColumn(name = "FK_ACCOUNT", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Column(name = "DEBIT", scale = 2, precision = 10, nullable = false)
    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    @Column(name = "CREDIT", scale = 2, precision = 10, nullable = false)
    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
