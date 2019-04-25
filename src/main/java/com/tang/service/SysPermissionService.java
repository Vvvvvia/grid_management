package com.tang.service;


import com.tang.dao.SysPermissionRepository;
import com.tang.dao.SysRolePermissionRepository;
import com.tang.entity.SysPermission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysPermissionService {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");

	@Autowired
	private SysPermissionRepository permissionDao;
	@Autowired
	private SysRolePermissionRepository sysRolePermissionRepository;


	public SysPermission save(SysPermission permission) {

		log.debug("新增菜单{}", permission.getName());
		return permissionDao.save(permission);
	}


	public SysPermission update(SysPermission permission) {
		return permissionDao.save(permission);
	}


	@Transactional
	public void delete(Integer pid) {
		sysRolePermissionRepository.deleteByPermissionId(pid);
		permissionDao.deleteById(pid);
		permissionDao.deleteByParentId(pid);

		log.debug("删除菜单id:{}", pid);
	}

	public List<SysPermission> findAll(){
		return permissionDao.findAll();
	}

	public SysPermission  findById(Integer id){
		return permissionDao.getOne(id);
	}

	public List<SysPermission> listParents(){
		return permissionDao.listParents();
	}

	public List<SysPermission> listByRoleId(Integer roleId){
		return permissionDao.listByRoleId(roleId);
	}

}
