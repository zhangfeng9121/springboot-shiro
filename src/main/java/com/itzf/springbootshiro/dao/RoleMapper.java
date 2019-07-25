package com.itzf.springbootshiro.dao;

import com.itzf.springbootshiro.domain.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @AUTHOR ZF
 * @DATE 2019/7/25
 */
public interface RoleMapper {

    @Select("SELECT r.id, r.name, r.description FROM role r " +
            "LEFT JOIN user_role ur on r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    @Results(
            value = {
                    @Result(id = true, property = "id", column = "id"),
                    @Result(property = "name", column = "name"),
                    @Result(property = "description", column = "description"),
                    @Result(property = "permList", column = "id",
                    many = @Many(select = "com.itzf.springbootshiro.dao.PermissionMapper.findPermListByRoleId", fetchType = FetchType.DEFAULT)
                    )
            }
    )
    List<Role> findRoleListByUserId(@Param("userId")Integer userId);
}
