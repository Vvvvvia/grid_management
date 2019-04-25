package com.tang.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;


import java.util.List;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import jdk.nashorn.internal.ir.annotations.Ignore;

@Entity
@Table(name = "sys_permission", schema = "gzp", catalog = "")
public class SysPermission {
    private Integer id;
    private Integer parentId;
    private String name;
    private String css;
    private String href;
    private Byte type;
    private String permission;
    private Integer sort;

    private List<SysPermission> child;
    @Transient@org.springframework.data.annotation.Transient
    public List<SysPermission>getChild(){
        return child;
    }
    public void setChild(List<SysPermission> child) {
        this.child = child;
    }
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "parent_id")
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "css")
    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    @Basic
    @Column(name = "href")
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Basic
    @Column(name = "type")
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Basic
    @Column(name = "permission")
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Basic
    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysPermission that = (SysPermission) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (css != null ? !css.equals(that.css) : that.css != null) return false;
        if (href != null ? !href.equals(that.href) : that.href != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (permission != null ? !permission.equals(that.permission) : that.permission != null)
            return false;
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (css != null ? css.hashCode() : 0);
        result = 31 * result + (href != null ? href.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (permission != null ? permission.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        return result;
    }





}
