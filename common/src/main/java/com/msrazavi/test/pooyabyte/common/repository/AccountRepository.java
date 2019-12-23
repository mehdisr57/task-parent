package com.msrazavi.test.pooyabyte.common.repository;

import com.msrazavi.test.pooyabyte.common.schema.entity.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Mehdi Sadat Razavi
 */
public interface AccountRepository extends CrudRepository<Account, Long> {
}
