package com.yige.sys.dao;

import com.yige.common.base.BaseDao;
import com.yige.sys.domain.MenuDO;

import java.util.List;

public interface MenuDao extends BaseDao<MenuDO> {

    List<MenuDO> listMenuByUserId(Long id);

    List<String> listUserPerms(Long id);

}
