package com.msrazavi.test.pooyabyte.common.service;

import com.msrazavi.test.pooyabyte.common.schema.dto.RequestDto;
import com.msrazavi.test.pooyabyte.common.schema.entity.Request;

import java.util.Optional;

/**
 * @author Mehdi Sadat Razavi
 */
public interface RequestService extends BaseService<Request, RequestDto, Long> {
    Optional<RequestDto> findWithVouchers(Long id);
}
