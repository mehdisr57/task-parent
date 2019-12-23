package com.msrazavi.test.pooyabyte.common.schema.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author Mehdi Sadat Razavi
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = AccountDto.class, property = "id")
public class AccountDto extends BaseDto {

    private String nationalCode;

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }
}
