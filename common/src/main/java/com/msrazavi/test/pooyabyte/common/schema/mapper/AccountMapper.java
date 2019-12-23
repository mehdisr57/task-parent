package com.msrazavi.test.pooyabyte.common.schema.mapper;

import com.msrazavi.test.pooyabyte.common.schema.dto.AccountDto;
import com.msrazavi.test.pooyabyte.common.schema.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Mehdi Sadat Razavi
 */
@Mapper(componentModel = "spring")
public interface AccountMapper extends BaseMapper<Account, AccountDto> {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
}
