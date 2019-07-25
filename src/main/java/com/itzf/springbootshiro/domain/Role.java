package com.itzf.springbootshiro.domain;

import java.util.List;

/**
 * @AUTHOR ZF
 * @DATE 2019/7/25
 */
public class Role {
    private Integer id;
    private String name;
    private String description;
    private List<Permission> permList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Permission> getPermList() {
        return permList;
    }

    public void setPermList(List<Permission> permList) {
        this.permList = permList;
    }
}
