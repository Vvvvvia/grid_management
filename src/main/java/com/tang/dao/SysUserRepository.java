package com.tang.dao;

import com.tang.entity.SysUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
@Repository
public interface SysUserRepository extends JpaRepository <SysUser,Integer>,JpaSpecificationExecutor{
    SysUser findByUserNameAndPassword(String name,String password);
    Page<SysUser>findByUserNameLike(Pageable pageable, String name);
    SysUser findByUserName(String name);
    SysUser findByPhone(String phone);
    List<SysUser> findByUserNameLike(String name);

    void deleteByIdIn(List<Integer> list);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT sys_user.id FROM sys_user where sys_user.username LIKE ?1",nativeQuery = true)
    List<Integer>findIdByNameLike(String name);
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update sys_user set sys_user.money = sys_user.money+?1 where sys_user.id = ?2",nativeQuery = true)
    int updateMoneyById( Double profit,  Integer id);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update sys_user set sys_user.salary = sys_user.salary+?1 where sys_user.id = ?2",nativeQuery = true)
    int updateSalaryById( Double salary,  Integer id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update sys_user set sys_user.password = ?2 where sys_user.id = ?1",nativeQuery = true)
    int changePassword( Integer id, String newPwd);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update sys_user set sys_user.status = ?1 where sys_user.id = ?2",nativeQuery = true)
    int updateStatusById( Byte status,  Integer id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update sys_user set sys_user.pay_time = ?1 where sys_user.id = ?2",nativeQuery = true)
    int updatePayTimeById(Timestamp timestamp, Integer id);


}
