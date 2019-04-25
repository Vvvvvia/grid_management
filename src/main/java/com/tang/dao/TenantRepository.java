package com.tang.dao;


import com.tang.entity.Tenant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface TenantRepository extends JpaRepository<Tenant,Integer>,JpaSpecificationExecutor{


    List<Tenant> findByNameLike(String name);

    
    Tenant findByPhone(String phone);
    
    @Transactional
    void deleteByIdIn(ArrayList<Integer> list);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT tenant.id FROM tenant where tenant.name LIKE ?1",nativeQuery = true)
    List<Integer>findIdByNameLike(String name);
    
    //更新账户余额money
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update tenant set tenant.money = tenant.money+?1 where tenant.id = ?2",nativeQuery = true)
    int updateMoneyById( Double money,  Integer id);

    //更新总收入total_income
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update tenant set tenant.total_income = tenant.total_income+?1 where tenant.id = ?2",nativeQuery = true)
    int updateTotalIncomeById( Double totalIncome,  Integer id);

}
