package com.tang.dao;

import com.tang.entity.SysRolePermission;
import com.tang.entity.SysRolePermissionPK;
import com.tang.entity.SysRoleUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRolePermissionRepository extends JpaRepository <SysRolePermission,SysRolePermissionPK>{

    int deleteByRoleId(Integer roleId);
    int deleteByPermissionId(Integer pId);

}
