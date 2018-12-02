package com.yige.sys.domain;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @author zoujm
 * @since 2018/12/1 15:01
 */
@TableName("role_menu_xrefs")
public class RoleMenuDO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long  roleId;
    private Long menuId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
