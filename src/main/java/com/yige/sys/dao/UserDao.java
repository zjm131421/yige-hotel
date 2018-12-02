package com.yige.sys.dao;

import com.yige.common.base.BaseDao;
import com.yige.sys.domain.UserDO;

/**
 * @author zoujm
 * @since 2018/12/1 13:29
 */
public interface UserDao extends BaseDao<UserDO> {

    Long[] listAllDept();

}
