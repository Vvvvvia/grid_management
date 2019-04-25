package com.tang.dao;



import com.tang.entity.SysRole;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SysRoleRepository extends JpaRepository<SysRole,Integer> {
    SysRole findByName(String name);

}
