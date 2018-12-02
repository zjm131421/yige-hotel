package com.yige.sys.dao;

import com.yige.common.base.BaseDao;
import com.yige.sys.domain.DeptDO;

/**
 * @author zoujm
 * @since 2018/12/1 15:40
 */
public interface DeptDao extends BaseDao<DeptDO> {

    Long[] listParentDept();

    int getDeptUserNumber(Long deptId);


}
