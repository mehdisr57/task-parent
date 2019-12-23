package com.msrazavi.test.pooyabyte.common.schema.mapper;

import com.msrazavi.test.pooyabyte.common.schema.dto.RequestDto;
import com.msrazavi.test.pooyabyte.common.schema.entity.Request;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Mehdi Sadat Razavi
 */
@Mapper(componentModel = "spring")
public interface RequestMapper extends BaseMapper<Request, RequestDto> {
    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);
}
