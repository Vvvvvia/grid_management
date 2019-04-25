package com.tang.service;



import com.tang.dao.SysPermissionRepository;
import com.tang.entity.SysLoginUser;
import com.tang.entity.SysPermission;
import com.tang.entity.SysUser;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * spring security登陆处理<br>
 * <p>
 * 密码校验请看文档（02 框架及配置），第三章第4节
 *
 */
@Service
public class SysUserDetailsService implements UserDetailsService {

	@Autowired
	private SysUserService userService;
	@Autowired
	private SysPermissionRepository sysPermissionRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser sysUser = userService.findByName(username);
		if (sysUser == null) {
			throw new AuthenticationCredentialsNotFoundException("用户名不存在");
		} else if (sysUser.getStatus().toString().equals("2")) {
			throw new LockedException("用户被锁定,请联系管理员");
		} else if (sysUser.getStatus().toString().equals("0")) {
			throw new DisabledException("用户已作废");
		}

		SysLoginUser loginUser = new SysLoginUser();
		BeanUtils.copyProperties(sysUser, loginUser);

		List<SysPermission> permissions = sysPermissionRepository.listByUserId(sysUser.getId());
		loginUser.setSysPermissions(permissions);

		return loginUser;
	}

}
