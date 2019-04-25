package com.tang.controller;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tang.config.LogAnnotation;
import com.tang.entity.SysLoginUser;
import com.tang.entity.SysPermission;
import com.tang.service.SysPermissionService;
import com.tang.util.UserUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 权限相关接口
 *
 */
@Api(tags = "权限管理")
@RestController
@RequestMapping("/permissions")
public class SysPermissionController {


	@Autowired
	private SysPermissionService permissionService;

	@ApiOperation(value = "当前登录用户拥有的权限")
	@GetMapping("/current")
	public List<SysPermission> permissionsCurrent() {
		SysLoginUser loginUser = UserUtil.getLoginUser();
		List<SysPermission> list = loginUser.getSysPermissions();
		final List<SysPermission> permissions = list.stream().filter(l -> l.getType().equals(new Byte("1")))
				.collect(Collectors.toList());

//		setChild(permissions);
//
//		return permissions.stream().filter(p -> p.getParentId().equals(0L)).collect(Collectors.toList());
		// 2018.06.09 支持多级菜单
        List<SysPermission> firstLevel = permissions.stream().filter(p -> p.getParentId().equals(0)).collect(Collectors.toList());
        firstLevel.parallelStream().forEach(p -> {
            setChild(p, permissions);
        });

        return firstLevel;
	}

	/**
	 * 设置子元素
	 * @param p
	 * @param permissions
	 */
	private void setChild(SysPermission p, List<SysPermission> permissions) {
		List<SysPermission> child = permissions.parallelStream().filter(
				a -> a.getParentId().equals(p.getId())).collect(Collectors.toList());
		//设置子菜单
		p.setChild(child);
		if (!CollectionUtils.isEmpty(child)) {
			child.parallelStream().forEach(c -> {
				//递归设置子元素，多级菜单支持
				setChild(c, permissions);
			});
		}
	}

//	private void setChild(List<SysPermission> permissions) {
//		permissions.parallelStream().forEach(per -> {
//			List<SysPermission> child = permissions.stream().filter(p -> p.getParentId().equals(per.getId()))
//					.collect(Collectors.toList());
//			per.setChild(child);
//		});
//	}

	/**
	 * 菜单列表
	 *
	 * @param pId
	 * @param permissionsAll
	 * @param list
	 */
	private void setSysPermissionsList(Integer pId, List<SysPermission> permissionsAll, List<SysPermission> list) {
		for (SysPermission per : permissionsAll) {
			if (per.getParentId().equals(pId)) {
				list.add(per);
				if (permissionsAll.stream().filter(p -> p.getParentId().equals(per.getId())).findAny() != null) {
					setSysPermissionsList(per.getId(), permissionsAll, list);
				}
			}
		}
	}

	@GetMapping
	@ApiOperation(value = "菜单列表")
//	@PreAuthorize("hasAuthority('sys:menu:query')")
	public List<SysPermission> permissionsList() {
		List<SysPermission> permissionsAll = permissionService.findAll();

		List<SysPermission> list = Lists.newArrayList();
		setSysPermissionsList(0, permissionsAll, list);

		return list;
	}

	@GetMapping("/all")
	@ApiOperation(value = "所有菜单")
//	@PreAuthorize("hasAuthority('sys:menu:query')")
	public JSONArray permissionsAll() {
		List<SysPermission> permissionsAll = permissionService.findAll();
		JSONArray array = new JSONArray();
		setSysPermissionsTree(0, permissionsAll, array);

		return array;
	}

	@GetMapping("/parents")
	@ApiOperation(value = "一级菜单")
//	@PreAuthorize("hasAuthority('sys:menu:query')")
	public List<SysPermission> parentMenu() {
		List<SysPermission> parents = permissionService.listParents();

		return parents;
	}

	/**
	 * 菜单树
	 *
	 * @param pId
	 * @param permissionsAll
	 * @param array
	 */
	private void setSysPermissionsTree(Integer pId, List<SysPermission> permissionsAll, JSONArray array) {
		for (SysPermission per : permissionsAll) {
			if (per.getParentId().equals(pId)) {
				String string = JSONObject.toJSONString(per);
				JSONObject parent = (JSONObject) JSONObject.parse(string);
				array.add(parent);

				if (permissionsAll.stream().filter(p -> p.getParentId().equals(per.getId())).findAny() != null) {
					JSONArray child = new JSONArray();
					parent.put("child", child);
					setSysPermissionsTree(per.getId(), permissionsAll, child);
				}
			}
		}
	}

	@GetMapping(params = "roleId")
	@ApiOperation(value = "根据角色id获取权限")
//	@PreAuthorize("hasAnyAuthority('sys:menu:query','sys:role:query')")
	public List<SysPermission> listByRoleId(Integer roleId) {
		return permissionService.listByRoleId(roleId);
	}

	@LogAnnotation(module = "增加菜单")
	@PostMapping
	@ApiOperation(value = "保存菜单")
//	@PreAuthorize("hasAuthority('sys:menu:add')")
	public void save(@RequestBody SysPermission permission) {
		permissionService.save(permission);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "根据菜单id获取菜单")
//	@PreAuthorize("hasAuthority('sys:menu:query')")
	public SysPermission get(@PathVariable Integer id) {
		return permissionService.findById(id);
	}

	@LogAnnotation(module = "修改菜单")
	@PutMapping
	@ApiOperation(value = "修改菜单")
//	@PreAuthorize("hasAuthority('sys:menu:add')")
	public void update(@RequestBody SysPermission permission) {
		permissionService.update(permission);
	}

	/**
	 * 校验权限
	 *
	 * @return
	 */
	@GetMapping("/owns")
	@ApiOperation(value = "校验当前用户的权限")
	public Set<String> ownsSysPermission() {
		List<SysPermission> permissions = UserUtil.getLoginUser().getSysPermissions();
		if (CollectionUtils.isEmpty(permissions)) {
			return Collections.emptySet();
		}

		return permissions.parallelStream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
				.map(SysPermission::getPermission).collect(Collectors.toSet());
	}

	@LogAnnotation(module = "删除菜单")
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除菜单")
//	@PreAuthorize("hasAuthority('sys:menu:del')")
	public void delete(@PathVariable Integer id) {
		permissionService.delete(id);
	}
}
