package com.tang.dao;

import com.tang.entity.GridSellRecords;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
@Repository
public interface GridSellRecordsRepository extends JpaRepository <GridSellRecords,Integer>,JpaSpecificationExecutor {

    Page<GridSellRecords> findByDate(Pageable pageable, Date date);

    Page<GridSellRecords> findByGridId(Pageable pageable, Integer gridId);

    GridSellRecords findByGridIdAndDate(Integer gridId,Date date);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update grid_sell_records set grid_sell_records.today_income = today_income+?1 " +
            "where grid_sell_records.date = ?2 AND grid_sell_records.grid_id = ?3",nativeQuery = true)
    int updateIncomeById( Double n,  Date date, Integer grid_id);



}
