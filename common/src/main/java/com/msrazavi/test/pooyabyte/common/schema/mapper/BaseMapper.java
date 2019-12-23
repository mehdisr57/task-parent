package com.msrazavi.test.pooyabyte.common.schema.mapper;

import com.msrazavi.test.pooyabyte.common.schema.dto.BaseDto;
import com.msrazavi.test.pooyabyte.common.schema.entity.BaseEntity;
import org.mapstruct.Context;

/**
 * @author Mehdi Sadat Razavi
 */
public interface BaseMapper<ENTITY extends BaseEntity, DTO extends BaseDto> {

    DTO entityToDto(ENTITY entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    ENTITY dtoToEntity(DTO dto, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);
}
