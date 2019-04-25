package com.tang.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sys_role_permission", schema = "gzp", catalog = "")
@IdClass(SysRolePermissionPK.class)
public class SysRolePermission {
    private Integer roleId;
    private Integer permissionId;
    private SysRole sysRoleByRoleId;
    private SysPermission sysPermissionByPermissionId;

    public SysRolePermission(Integer roleId, Integer permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public SysRolePermission() {
    }

    @Id
    @Column(name = "role_id")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Id
    @Column(name = "permission_id")
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

        SysRolePermission that = (SysRolePermission) o;

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

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "ID", nullable = false,insertable = false,updatable = false)
    public SysRole getSysRoleByRoleId() {
        return sysRoleByRoleId;
    }

    public void setSysRoleByRoleId(SysRole sysRoleByRoleId) {
        this.sysRoleByRoleId = sysRoleByRoleId;
    }

    @ManyToOne
    @JoinColumn(name = "permission_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public SysPermission getSysPermissionByPermissionId() {
        return sysPermissionByPermissionId;
    }

    public void setSysPermissionByPermissionId(SysPermission sysPermissionByPermissionId) {
        this.sysPermissionByPermissionId = sysPermissionByPermissionId;
    }
}
