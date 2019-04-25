package com.tang.dao;

import com.tang.entity.GoodsRecords;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

import java.util.List;

@Repository
public interface GoodsRecordsRepository extends JpaRepository<GoodsRecords,Integer> ,JpaSpecificationExecutor{

    Page<GoodsRecords> findByGoodsId(Pageable pageable, Integer goodsId);

    Page<GoodsRecords> findByTenantId(Pageable pageable, Integer tenantId);

    Page<GoodsRecords> findByCreateTimeOrderByCreateTimeDesc(Pageable pageable,Timestamp timestamp);

    @Transactional
    void deleteByIdIn(List list);

}
