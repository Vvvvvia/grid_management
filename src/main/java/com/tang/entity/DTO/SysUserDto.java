package com.tang.entity.DTO;

import com.tang.entity.SysUser;

import java.util.List;

public class SysUserDto extends SysUser {
    private List<Integer> roleIds;

    public SysUserDto(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public SysUserDto() {
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return "SysUserDto{" +
                "roleIds=" + roleIds +
                "} " + super.toString();
    }
}
