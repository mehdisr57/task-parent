package com.msrazavi.test.pooyabyte.common.schema.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Mehdi Sadat Razavi
 */
@Entity
@Table(name = "VOUCHER")
public class Voucher extends BaseEntity {

    private Date date;
    private Request request;
    private Set<VoucherDetail> voucherDetails;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @OneToMany(mappedBy = "voucher")
    public Set<VoucherDetail> getVoucherDetails() {
        if(Objects.isNull(voucherDetails)){
            voucherDetails = new HashSet<>();
        }
        return voucherDetails;
    }

    public void setVoucherDetails(Set<VoucherDetail> voucherDetails) {
        this.voucherDetails = voucherDetails;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_REQUEST")
    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
