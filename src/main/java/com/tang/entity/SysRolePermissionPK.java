package com.tang.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
@Embeddable
public class SysRolePermissionPK implements Serializable {
    private Integer roleId;
    private Integer permissionId;

    public SysRolePermissionPK(Integer roleId, Integer permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public SysRolePermissionPK() {
    }

    @Column(name = "role_id")
    @Id
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Column(name = "permission_id")
    @Id
    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysRolePermissionPK that = (SysRolePermissionPK) o;

        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;
        if (permissionId != null ? !permissionId.equals(that.permissionId) : that.permissionId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId != null ? roleId.hashCode() : 0;
        result = 31 * result + (permissionId != null ? permissionId.hashCode() : 0);
        return result;
    }
}
