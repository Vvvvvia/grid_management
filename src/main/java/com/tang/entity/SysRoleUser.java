package com.tang.entity;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sys_role_user", schema = "gzp", catalog = "")
@IdClass(SysRoleUserPK.class)
public class SysRoleUser {
    private Integer userId;
    private Integer roleId;
    private Timestamp createTime;
    private SysRole sysRoleByRoleId;

    @Id
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "role_id")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysRoleUser that = (SysRoleUser) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
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
}
