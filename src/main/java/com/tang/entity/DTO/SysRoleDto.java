package com.tang.entity.DTO;



import com.tang.entity.SysRole;

import java.util.List;

public class SysRoleDto extends SysRole {

	private static final long serialVersionUID = 4218495592167610193L;

	private List<Integer> permissionIds;

	public List<Integer> getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(List<Integer> permissionIds) {
		this.permissionIds = permissionIds;
	}
}
