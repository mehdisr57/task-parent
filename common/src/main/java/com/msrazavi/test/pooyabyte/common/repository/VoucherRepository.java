package com.msrazavi.test.pooyabyte.common.repository;

import com.msrazavi.test.pooyabyte.common.schema.entity.Voucher;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Mehdi Sadat Razavi
 */
public interface VoucherRepository extends CrudRepository<Voucher, Long> {
}
