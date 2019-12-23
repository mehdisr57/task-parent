package com.msrazavi.test.pooyabyte.common.schema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Mehdi Sadat Razavi
 */
@Entity
@Table(name = "ACCOUNT")
public class Account extends BaseEntity{

    private String nationalCode;

    @Column(name = "NATIONAL_CODE", length = 10, nullable = false)
    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }
}
