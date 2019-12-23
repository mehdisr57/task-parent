package com.msrazavi.test.pooyabyte.common.schema.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.math.BigDecimal;

/**
 * @author Mehdi Sadat Razavi
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = RequestDto.class, property = "id")
public class VoucherDetailDto extends BaseDto {

    private VoucherDto voucher;
    private AccountDto account;
    private BigDecimal debit;
    private BigDecimal credit;
    private String description;

    public VoucherDto getVoucher() {
        return voucher;
    }

    public void setVoucher(VoucherDto voucher) {
        this.voucher = voucher;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
