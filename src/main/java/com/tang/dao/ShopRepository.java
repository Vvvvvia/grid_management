package com.tang.dao;

import com.tang.entity.Shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop,Integer>,JpaSpecificationExecutor {
    List<Shop> findByNameContaining( String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update shop set shop.status =?1 where shop.id = ?2",nativeQuery = true)
    int updateStatusById( Boolean status,  Integer id);

    void deleteByIdIn(List<Integer> list);

    List<Shop>findByIdInAndStatus(List<Integer> list,Boolean status);

}
