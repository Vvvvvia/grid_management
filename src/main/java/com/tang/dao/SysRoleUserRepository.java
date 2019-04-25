package com.tang.dao;

import com.tang.entity.SysRoleUser;
import com.tang.entity.SysRoleUserPK;
import com.tang.entity.SysUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
@Repository
public interface SysRoleUserRepository extends JpaRepository <SysRoleUser,Integer>{
    int deleteByUserId(Integer userId);
    int deleteByRoleId(Integer roleId);
    List<SysRoleUser> findByUserId(Integer userID);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO sys_role_user VALUES (?1,?2,?3) ",nativeQuery = true)
    int insert(Integer userId, Integer roleId, Timestamp createTime);

}
