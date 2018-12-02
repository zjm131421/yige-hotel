package com.yige.common.service;

import com.yige.common.base.CoreService;
import com.yige.common.domain.DictDO;
import com.yige.sys.domain.UserDO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DictService extends CoreService<DictDO> {

    List<DictDO> listType();

    String getName(String type, String value);

    /**
     * 获取爱好列表
     *
     * @return
     * @param userDO
     */
    List<DictDO> getHobbyList(UserDO userDO);

    /**
     * 根据类型获取字典列表
     *
     * @return List
     */
    List<DictDO> getListByType(String type);

}
