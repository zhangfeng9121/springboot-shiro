package com.itzf.springbootshiro.dao;

import com.itzf.springbootshiro.domain.Permission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @AUTHOR ZF
 * @DATE 2019/7/25
 */
public interface PermissionMapper {
    /**
     * 根据角色Id查询权限列表
     * @param roleId
     * @return
     */
    @Select("SELECT p.id as id, p.name as name, p.url as url FROM permission p " +
            "LEFT JOIN role_permission rp on p.id = rp.permission_id " +
            "WHERE rp.role_id = #{roleId}")
    List<Permission> findPermListByRoleId(@Param("roleId")Integer roleId);
}
