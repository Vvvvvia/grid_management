package com.tang.dao;

import com.tang.entity.SellRecords;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public interface SellRecordsRepository extends JpaRepository <SellRecords,Integer>,JpaSpecificationExecutor {

    Page<SellRecords> findByGoodsId(Pageable pageable, Integer goodsId);

    Page<SellRecords> findByGridId(Pageable pageable, Integer gridId);

    Page<SellRecords> findByTenantId(Pageable pageable, Integer tenantId);

    Page<SellRecords> findByMemberId(Pageable pageable,Integer memberId);

    Page<SellRecords> findByUserId(Pageable pageable,Integer userId);


    @Transactional
    void deleteByIdIn(List list);


    @Transactional
//    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT * FROM sell_records where date BETWEEN ?1 AND ?2 ORDER BY date DESC ",nativeQuery = true)
    Page<SellRecords> findByDate(Pageable pageable, Date date, Date date2);
}
