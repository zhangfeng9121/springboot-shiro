package com.itzf.springbootshiro.domain;

/**
 * @AUTHOR ZF
 * @DATE 2019/7/25
 */
public class RolePerm {
    private Integer id;
    private String roleId;
    private String permissionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
}
