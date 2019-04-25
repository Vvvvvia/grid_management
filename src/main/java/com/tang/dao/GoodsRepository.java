package com.tang.dao;

import com.tang.entity.Goods;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public interface GoodsRepository extends JpaRepository<Goods,Integer>,JpaSpecificationExecutor {

    List<Goods> findByNameContaining(String name);
    List<Goods> findByNameLike( String name);

    Goods findByCreateTime(String barcode);
    @Transactional
    void deleteByIdIn(ArrayList<Integer> list);

    List<Goods> findByGridIdAndStatusIsTrue(Integer gridId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT goods.id FROM goods where goods.name LIKE ?1",nativeQuery = true)
    List<Integer>findIdByNameLike(String name);
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update goods set goods.status =?1 where goods.id = ?2",nativeQuery = true)
    int updateStatusById( Boolean status,  Integer id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update goods set goods.num =goods.num+?1 where goods.id = ?2",nativeQuery = true)
    int increaseNumById( Integer n,  Integer id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT goods.id FROM goods where goods.grid_id IN ?1",nativeQuery = true)
    List<Integer> IdListByGridIdIn(List<Integer> list);


}
