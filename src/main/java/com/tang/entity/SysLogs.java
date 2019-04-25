package com.tang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sys_logs", schema = "gzp", catalog = "")
public class SysLogs {
    private Integer id;
    private Integer userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp createTime;
    private String content;
    private SysUser sysUserByUserId;
    private Boolean flag;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "flag")
    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysLogs sysLogs = (SysLogs) o;

        if (id != null ? !id.equals(sysLogs.id) : sysLogs.id != null) return false;
        if (userId != null ? !userId.equals(sysLogs.userId) : sysLogs.userId != null) return false;
        if (createTime != null ? !createTime.equals(sysLogs.createTime) : sysLogs.createTime != null)
            return false;
        if (content != null ? !content.equals(sysLogs.content) : sysLogs.content != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public SysUser getSysUserByUserId() {
        return sysUserByUserId;
    }

    public void setSysUserByUserId(SysUser sysUserByUserId) {
        this.sysUserByUserId = sysUserByUserId;
    }

    @Override
    public String toString() {
        return "SysLogs{" +
                "id=" + id +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                ", sysUserByUserId=" + sysUserByUserId +
                ", flag=" + flag +
                '}';
    }
}
