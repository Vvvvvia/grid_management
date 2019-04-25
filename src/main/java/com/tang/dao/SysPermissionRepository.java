package com.tang.dao;


import com.tang.entity.SysPermission;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SysPermissionRepository extends JpaRepository<SysPermission,Integer> {
    int deleteByParentId(Integer parentId);

    @Query(value = "select p.* from sys_permission p inner join sys_role_permission " +
            "rp on p.id = rp.permission_id where rp.role_id = ?1 order by p.sort",nativeQuery = true)
    List<SysPermission> listByRoleId(Integer roleId);

    @Transactional
    @Query(nativeQuery = true,value = "select distinct * from sys_permission p " +
            "inner join sys_role_permission rp on p.id = rp.permission_id inner join " +
            "sys_role_user ru on ru.role_id = rp.role_id where ru.user_id = ?1 order by p.sort")
    List<SysPermission> listByUserId(Integer userId);

    @Transactional
    @Query(nativeQuery = true,value = "select * from sys_permission t where t.type = 1 order by t.sort")
    List<SysPermission> listParents();

}
