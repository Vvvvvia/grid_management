package com.tang.controller;



import com.tang.config.LogAnnotation;
import com.tang.dao.SysRoleUserRepository;
import com.tang.entity.SysRole;
import com.tang.entity.DTO.SysRoleDto;
import com.tang.entity.SysRoleUser;
import com.tang.interceptor.ResponseBean;
import com.tang.interceptor.UnicomResponseEnums;
import com.tang.service.SysRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "职位管理")
@RestController
@RequestMapping("/roles")
public class SysRoleController {

	@Autowired
	private SysRoleService roleService;
	@Autowired
	private SysRoleUserRepository roleUserRepository;

	@LogAnnotation(module = "增加职位")
	@PostMapping
	@ApiOperation(value = "增加职位")
//	@PreAuthorize("hasAuthority('sys:role:add')")
	public SysRole saveSysRole(@RequestBody SysRoleDto roleDto) {
		return roleService.saveRole(roleDto);
	}

	@LogAnnotation(module = "更新职位信息")
	@PutMapping
	@ApiOperation(value = "更新职位信息")
//	@PreAuthorize("hasAuthority('sys:role:update')")
	public SysRole updateSysRole(@RequestBody SysRoleDto roleDto) {
		return roleService.saveRole(roleDto);
	}


	@GetMapping
	@ApiOperation(value = "职位列表")
//	@PreAuthorize("hasAuthority('sys:role:query')")
	public List<SysRole> listRoles() {
		return roleService.findAll();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "根据ID查询")
//	@PreAuthorize("hasAuthority('sys:role:query')")
	public SysRole get(@PathVariable Integer id) {

		return roleService.findById(id);

	}
	@GetMapping("/user/{id}")
	@ApiOperation(value = "根据UserId查询")
//	@PreAuthorize("hasAuthority('sys:role:query')")
	public List<SysRoleUser> getByUserId(@PathVariable Integer id) {

		return roleUserRepository.findByUserId(id);

	}
	@GetMapping("/name/{name}")
	@ApiOperation(value = "根据RoleName查询")
//	@PreAuthorize("hasAuthority('sys:role:query')")
	public SysRole getByRoleName(@PathVariable String name) {

		return roleService.findByName(name);

	}

//	@GetMapping("/all")
////	@ApiOperation(value = "所有角色")
//	@PreAuthorize("hasAnyAuthority('sys:user:query','sys:role:query')")
//	public List<SysRole> roles() {
//		return roleDao.list(Maps.newHashMap(), null, null);
//	}

	@GetMapping(params = "userId")
	@ApiOperation(value = "根据UserID获取拥有的职位")
//	@PreAuthorize("hasAnyAuthority('sys:user:query','sys:role:query')")
	public List<SysRoleUser> roles(Integer userId) {
		return roleService.findByUserId(userId);
	}
	@LogAnnotation(module = "删除职位")
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除职位")
//	@PreAuthorize("hasAuthority('sys:role:del')")
	public void delete(@PathVariable Integer id) {
		roleService.deleteRole(id);
	}
}
