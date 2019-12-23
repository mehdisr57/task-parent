package com.msrazavi.test.pooyabyte.common.service.impl;

import com.msrazavi.test.pooyabyte.common.repository.AccountRepository;
import com.msrazavi.test.pooyabyte.common.schema.dto.AccountDto;
import com.msrazavi.test.pooyabyte.common.schema.entity.Account;
import com.msrazavi.test.pooyabyte.common.schema.mapper.AccountMapper;
import com.msrazavi.test.pooyabyte.common.schema.mapper.BaseMapper;
import com.msrazavi.test.pooyabyte.common.service.AccountService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * @author Mehdi Sadat Razavi
 */
@Service
public class AccountServiceImpl
        extends BaseServiceImpl<Account, AccountDto, Long>
        implements AccountService {

    private final AccountRepository repository;
    private final AccountMapper mapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.repository = accountRepository;
        this.mapper = accountMapper;
    }

    @Override
    CrudRepository<Account, Long> getRepository() {
        return repository;
    }

    @Override
    BaseMapper<Account, AccountDto> getMapper() {
        return mapper;
    }
}
