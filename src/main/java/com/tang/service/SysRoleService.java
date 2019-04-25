package com.tang.service;

import com.tang.dao.SysRolePermissionRepository;
import com.tang.dao.SysRoleRepository;
import com.tang.dao.SysRoleUserRepository;
import com.tang.entity.SysRole;
import com.tang.entity.DTO.SysRoleDto;
import com.tang.entity.SysRolePermission;
import com.tang.entity.SysRoleUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class SysRoleService {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");

	@Autowired
	private SysRoleRepository sysRoleRepository;
	@Autowired
	private SysRolePermissionRepository sysRolePermissionRepository;

	@Autowired
	private SysRoleUserRepository sysRoleUserRepository;

	@Transactional
	public SysRole saveRole(SysRoleDto roleDto) {
		SysRole role = new SysRole();
		Integer id = roleDto.getId();
		Timestamp createTime = roleDto.getCreateTime();
		Timestamp updateTime = new Timestamp(new Date().getTime());
		if (id==null){
			createTime = updateTime;
		}
		role.setId(id);
		role.setName(roleDto.getName());
		role.setDescription(roleDto.getDescription());
		role.setSalary(roleDto.getSalary());
		role.setStatus(roleDto.getStatus());
		role.setCreateTime(createTime);
		role.setUpdateTime(updateTime);

		System.out.println(role);
		List<Integer> permissionIds = roleDto.getPermissionIds();
		permissionIds.remove(0);
		
		SysRole rrr ;
		
		if (role.getId() != null) {// 修改
			rrr = updateRole(role, permissionIds);
		} else {// 新增
			rrr =  saveRole(role, permissionIds);
		}
		return rrr;
	}

	private SysRole saveRole(SysRole role, List<Integer> permissionIds) {
		//更新之得到新的role
		SysRole role1 = sysRoleRepository.save(role);
		if (!CollectionUtils.isEmpty(permissionIds)) {
			for (Integer pid : permissionIds){
				SysRolePermission s = new SysRolePermission(role1.getId(), pid);
				sysRolePermissionRepository.save(s);
			}
		}
		log.debug("新增角色{}", role.getName());
		return role1;
	}

	private SysRole updateRole(SysRole role, List<Integer> permissionIds) {
		SysRole r = sysRoleRepository.findByName(role.getName());
		if (r != null && r.getId() != role.getId()) {
			throw new IllegalArgumentException(role.getName() + "已存在");
		}

		SysRole role1 = sysRoleRepository.save(role);

		//删除之前的权限授权 
		
		sysRolePermissionRepository.deleteByRoleId(role.getId());
		//添加新的权限授权
		if (!CollectionUtils.isEmpty(permissionIds)) {
			for (Integer pid:permissionIds){
				sysRolePermissionRepository.save(new SysRolePermission(role.getId(), pid));
			}
			
		}
		log.debug("修改角色{}", role.getName());
		return role1;
	}

	@Transactional
	public void deleteRole(Integer roleId) {
		sysRolePermissionRepository.deleteByRoleId(roleId);
		sysRoleUserRepository.deleteByRoleId(roleId);
		sysRoleRepository.deleteById(roleId);

		log.debug("删除角色id:{}", roleId);
	}
	public SysRole findById(Integer id) {
		return sysRoleRepository.getOne(id);
	}


	public List<SysRole> findAll() {
		return  sysRoleRepository.findAll();
	}

	public List<SysRoleUser> findByUserId(Integer userId){
		return sysRoleUserRepository.findByUserId(userId);
	}

	public SysRole findByName(String name) {
		return sysRoleRepository.findByName(name);
	}

	public List<SysRole> findByIdIn(Collection<String> ids) {
		return findByIdIn(ids);
	}
}
