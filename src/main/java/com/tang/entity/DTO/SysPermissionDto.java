package com.tang.entity.DTO;

import com.tang.entity.SysPermission;

import java.util.List;

public class SysPermissionDto extends SysPermission {
    private List<SysPermission> child;



    public List<SysPermission> getChild() {
        return child;
    }

    public void setChild(List<SysPermission> child) {
        this.child = child;
    }
}
