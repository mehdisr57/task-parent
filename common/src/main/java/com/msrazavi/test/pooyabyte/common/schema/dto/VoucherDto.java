package com.msrazavi.test.pooyabyte.common.schema.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Date;
import java.util.Set;

/**
 * @author Mehdi Sadat Razavi
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = RequestDto.class, property = "id")
public class VoucherDto extends BaseDto {

    private Date date;
    private RequestDto request;
    private Set<VoucherDetailDto> voucherDetails;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public RequestDto getRequest() {
        return request;
    }

    public void setRequest(RequestDto request) {
        this.request = request;
    }

    public Set<VoucherDetailDto> getVoucherDetails() {
        return voucherDetails;
    }

    public void setVoucherDetails(Set<VoucherDetailDto> voucherDetails) {
        this.voucherDetails = voucherDetails;
    }
}
