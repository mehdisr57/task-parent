package com.msrazavi.test.pooyabyte.common.schema.mapper;

import com.msrazavi.test.pooyabyte.common.schema.dto.VoucherDto;
import com.msrazavi.test.pooyabyte.common.schema.entity.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Mehdi Sadat Razavi
 */
@Mapper(componentModel = "spring")
public interface VoucherMapper extends BaseMapper<Voucher, VoucherDto> {
    VoucherMapper INSTANCE = Mappers.getMapper(VoucherMapper.class);
}
