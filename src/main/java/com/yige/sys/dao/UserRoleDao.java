package com.yige.sys.dao;

import com.yige.common.base.BaseDao;
import com.yige.sys.domain.UserRoleDO;

import java.io.Serializable;
import java.util.List;

public interface UserRoleDao extends BaseDao<UserRoleDO> {

    List<Long> listRoleId(Serializable userId);

    int removeByUserId(Serializable userId);

    int batchSave(List<UserRoleDO> list);

    int batchRemoveByUserId(Long[] ids);

}
