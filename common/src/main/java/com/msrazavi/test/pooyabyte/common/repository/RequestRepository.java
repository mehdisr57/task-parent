package com.msrazavi.test.pooyabyte.common.repository;

import com.msrazavi.test.pooyabyte.common.schema.entity.Request;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Mehdi Sadat Razavi
 */
public interface RequestRepository extends CrudRepository<Request, Long> {

    @Query("SELECT e " +
            "FROM Request e " +
            "WHERE e.nextDate >= :startDate " +
            "AND e.nextDate <= :endDate " +
            "AND (e.lastDate IS NULL OR e.lastDate <= :startDate)")
    List<Request> findByNextDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

    @Query("SELECT e " +
            "FROM Request e " +
            "WHERE e.nextDate IS NULL " +
            "OR e.nextDate <= :currentDate")
    List<Request> findErrorNextDate(@Param("currentDate") Date currentDate, Pageable pageable);

    @Query("SELECT e " +
            "FROM Request e " +
            "LEFT JOIN FETCH e.vouchers v " +
            "WHERE e.id = :id")
    Request findWithVouchers(@Param("id") Long id);
}
