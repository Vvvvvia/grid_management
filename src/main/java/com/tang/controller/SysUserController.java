package com.tang.controller;


import com.tang.config.LogAnnotation;

import com.tang.entity.SysUser;
import com.tang.entity.DTO.SysUserDto;
import com.tang.interceptor.ResponseBean;
import com.tang.interceptor.UnicomResponseEnums;

import com.tang.service.SysUserService;
import com.tang.util.MyDateFormat;
import com.tang.util.UserUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(tags = "员工（用户）管理")
@RequestMapping("/users")
public class SysUserController {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");

	@Autowired
	private SysUserService userService;



	@LogAnnotation(module = "添加用户")
	@ApiOperation(value = "添加用户")
	@PostMapping
//	@PreAuthorize("hasAuthority('sys:user:add')")
	public ResponseBean<SysUser> saveUser(@RequestBody SysUserDto userDto) {
		SysUser u = userService.findByName(userDto.getUserName());
		if (u != null) {
			throw new IllegalArgumentException(userDto.getUserName() + "已存在");
		}
		return  new ResponseBean(true,
				userService.insertUser(userDto)
				,UnicomResponseEnums.SUCCESS_OPTION);

	}

	@LogAnnotation(module = "更新用户信息")
	@ApiOperation(value = "更新用户信息")
	@PutMapping
//	@PreAuthorize("hasAuthority('sys:user:add')")
	public ResponseBean<SysUser> updateUser(@RequestBody SysUserDto userDto) {
		System.out.println(userDto);
		return  new ResponseBean(true,
				userService.insertUser(userDto)
				,UnicomResponseEnums.SUCCESS_OPTION);
	}

	@LogAnnotation(module = "修改密码")
	@ApiOperation(value = "修改密码")
	@PutMapping("/{name}")
//	@PreAuthorize("hasAuthority('sys:user:password')")
	public ResponseBean changePassword(@PathVariable("name") String username,
									   @RequestParam("oldPassword") String oldPassword,
									   @RequestParam("newPassword") String newPassword) {
		userService.changePassword(username, oldPassword, newPassword);
		return new ResponseBean(true, UnicomResponseEnums.SUCCESS_OPTION);
	}
//	查询全部
	@ApiOperation(value = "分页查询全部用户")
	@GetMapping
//	@PreAuthorize("hasAuthority('sys:user:query')")
	public ResponseBean<Page<SysUser>> listUsers(
			@RequestParam(name = "page" ,required = false,defaultValue = "0") Integer page,
			@RequestParam(value = "start",required = false) String start,
			@RequestParam(value = "end",required = false) String end,
			@RequestParam(value = "name",required = false) String name,
			@RequestParam(value = "phone",required = false)String phone,
			@RequestParam(value = "account",required = false,defaultValue = "false")Boolean account
												 ) {
		return new ResponseBean(true,
				userService.findAll(new PageRequest(page,10),
						MyDateFormat.dateFormat(start),
						MyDateFormat.dateFormat(end),name,phone,account)
				,UnicomResponseEnums.SUCCESS_OPTION);
	}

	@ApiOperation(value = "获取当前登录用户")
	@GetMapping("/current")
	public SysUser currentUser() {
		return UserUtil.getLoginUser();
	}

	@ApiOperation(value = "根据用户id获取用户")
	@GetMapping("/{id}")
//	@PreAuthorize("hasAuthority('sys:user:query')")
	public ResponseBean<SysUser> user(@PathVariable Integer id) {
		return new ResponseBean(true,
				userService.findById(id),
				UnicomResponseEnums.SUCCESS_OPTION);

	}
	@GetMapping("/name/{name}")
	@ApiOperation(value = "根据用户姓名获取用户")
//	@PreAuthorize("hasAuthority('sys:user:query')")
	public ResponseBean<SysUser> findByUserName(@PathVariable String name) {
		return new ResponseBean(true,
				userService.findByName(name),
				UnicomResponseEnums.SUCCESS_OPTION);

	}
	@GetMapping("/phone/{phone}")
	@ApiOperation(value = "根据用户手机获取用户")
//	@PreAuthorize("hasAuthority('sys:user:query')")
	public ResponseBean<SysUser> findByPhone(@PathVariable String phone) {
		return new ResponseBean(true,
				userService.findByPhone(phone),
				UnicomResponseEnums.SUCCESS_OPTION);

	}
	@LogAnnotation(module = "更改用户状态")
	@PostMapping("/status/{id}/{status}")
	@ApiOperation(value = "更改用户状态")
//	@PreAuthorize("hasAuthority('sys:user:query')")
	public ResponseBean<SysUser> updateStatusById(@PathVariable("status") Byte status,
											 @PathVariable("id") Integer id) {
		return new ResponseBean(true,
				userService.updateStatusById(status,id),
				UnicomResponseEnums.SUCCESS_OPTION);

	}

	@LogAnnotation(module = "删除用户")
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除用户")
//	@PreAuthorize("hasAuthority('sys:user:delete')")
	public ResponseBean<SysUser> deleteById(
		@PathVariable("id") Integer id) {
		userService.delete(id);
		return new ResponseBean(true,
				UnicomResponseEnums.SUCCESS_OPTION);

	}
	@LogAnnotation(module = "批量删除用户")
	@DeleteMapping
	@ApiOperation(value = "批量删除用户")
//	@PreAuthorize("hasAuthority('sys:user:delete')")
	public ResponseBean<SysUser> deleteByIdIn(@RequestBody List<Integer> list) {
		userService.deleteByIdIn(list);
		return new ResponseBean(true,
				UnicomResponseEnums.SUCCESS_OPTION);

	}

}
