package com.msrazavi.test.pooyabyte.common.service.impl;

import com.msrazavi.test.pooyabyte.common.repository.VoucherRepository;
import com.msrazavi.test.pooyabyte.common.schema.dto.VoucherDto;
import com.msrazavi.test.pooyabyte.common.schema.entity.Voucher;
import com.msrazavi.test.pooyabyte.common.schema.mapper.BaseMapper;
import com.msrazavi.test.pooyabyte.common.schema.mapper.VoucherMapper;
import com.msrazavi.test.pooyabyte.common.service.VoucherService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * @author Mehdi Sadat Razavi
 */
@Service
public class VoucherServiceImpl
        extends BaseServiceImpl<Voucher, VoucherDto, Long>
        implements VoucherService {

    private final VoucherMapper mapper;
    private final VoucherRepository repository;

    public VoucherServiceImpl(VoucherMapper mapper, VoucherRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    CrudRepository<Voucher, Long> getRepository() {
        return repository;
    }

    @Override
    BaseMapper<Voucher, VoucherDto> getMapper() {
        return mapper;
    }
}
