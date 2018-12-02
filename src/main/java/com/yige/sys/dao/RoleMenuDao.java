package com.yige.sys.dao;

import com.yige.common.base.BaseDao;
import com.yige.sys.domain.RoleMenuDO;

import java.io.Serializable;
import java.util.List;

/**
 * 角色与菜单的对应关系
 */
public interface RoleMenuDao extends BaseDao<RoleMenuDO> {

    List<Long> listMenuIdByRoleId(Serializable roleId);

    int removeByRoleId(Serializable roleId);

    int batchSave(List<RoleMenuDO> list);

}
