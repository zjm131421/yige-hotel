package com.yige.common.dao;

import com.yige.common.base.BaseDao;
import com.yige.common.domain.DictDO;

import java.util.List;

/**
 * 字典表
 * @author zoujm
 * @since 2018/12/1 13:45
 */
public interface DictDao extends BaseDao<DictDO> {

    List<DictDO> listType();
    
}
