package com.msrazavi.test.pooyabyte.task.model;

import com.msrazavi.test.pooyabyte.common.schema.entity.Request;
import com.msrazavi.test.pooyabyte.common.schema.entity.Voucher;
import com.msrazavi.test.pooyabyte.common.schema.entity.VoucherDetail;

import java.util.HashSet;
import java.util.Set;

/**
 * this class use to transfer data between process and writer
 *
 * @author Mehdi Sadat Razavi
 */
public class TransferStepDto {

    private Request request;
    private Voucher voucher;
    private Set<VoucherDetail> voucherDetails = new HashSet<>(2);

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public Set<VoucherDetail> getVoucherDetails() {
        return voucherDetails;
    }

    public void setVoucherDetails(Set<VoucherDetail> voucherDetails) {
        this.voucherDetails = voucherDetails;
    }

    @Override
    public String toString() {
        return "TransferStepDto{" +
                "request=" + request +
                '}';
    }
}
