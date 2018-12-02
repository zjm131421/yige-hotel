package com.yige.sys.domain;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @author zoujm
 * @since 2018/12/1 14:47
 */
@TableName("user_role_xrefs")
public class UserRoleDO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long userId;
    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
