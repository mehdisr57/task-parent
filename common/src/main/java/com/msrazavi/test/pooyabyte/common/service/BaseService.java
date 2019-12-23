package com.msrazavi.test.pooyabyte.common.service;

import com.msrazavi.test.pooyabyte.common.schema.dto.BaseDto;
import com.msrazavi.test.pooyabyte.common.schema.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author Mehdi Sadat Razavi
 */
public interface BaseService<ENTITY extends BaseEntity, DTO extends BaseDto, ID extends Serializable> {

    Optional<DTO> findById(ID id);

    Optional<DTO> save(DTO model);

    List<DTO> findAll();
}
