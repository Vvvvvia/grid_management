package com.tang.dao;

import com.tang.entity.RentRecords;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface RentRecordsRepository extends JpaRepository<RentRecords,Integer>,JpaSpecificationExecutor {

    void deleteByIdIn(List list);
    //    通过格子查询
    Page<RentRecords> findByGridId(Pageable pageable, Integer gridId);
    //    通过格主查询
    Page<RentRecords> findByTenantIdOrderByIdDesc(Pageable pageable,Integer tenantId);
    //    通过时间查询

    RentRecords findByGridIdAndStatusIsTrue(Integer gridId);
    
    List<RentRecords> findByTenantIdAndStatus(Integer tenantId,Boolean status);
    @Transactional
//    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT * FROM rent_records where date BETWEEN ?1 AND ?2 ORDER BY date DESC ",nativeQuery = true)
    Page<RentRecords> findByDate(Pageable pageable,Date date,Date date2);

    List<RentRecords> findByTenantIdInAndStatus( List list,Boolean status);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update rent_records set rent_records.status =?1 where rent_records.id = ?2",nativeQuery = true)
    int updateStatusById( Boolean status,  Integer id);
}


