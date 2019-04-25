package com.tang.dao;

import com.tang.entity.Grid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface GridRepository extends JpaRepository<Grid,Integer> ,JpaSpecificationExecutor{

    List<Grid> findByNameContaining(String name);
    List<Grid> findByNameLike(String name);
    @Transactional
    void deleteByIdIn(ArrayList<Integer> list);

    //更新总收入total_income
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update grid set grid.total_income = grid.total_income+?1 where grid.id = ?2",nativeQuery = true)
    int updateTotalIncomeById( Double totalIncome,  Integer id);
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT grid.id FROM grid where grid.name LIKE ?1",nativeQuery = true)
    List<Integer> findIdByName(String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update grid set grid.rent_id=null, grid.status = true where grid.id = ?1",nativeQuery = true)
    int relieve( Integer id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update grid set grid.rent_id=?1 ,grid.status = FALSE where grid.id = ?2",nativeQuery = true)
    int rent( Integer rentId, Integer id);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT status FROM grid where id IN ?1 AND status = FALSE ",nativeQuery = true)
    List<Boolean> findStatusByIdIn( List list);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "select grid.id FROM grid WHERE grid.shop_id=?1",nativeQuery = true)
    List<Integer> isNull_ShopId(  Integer shopId);

}
