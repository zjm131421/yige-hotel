package com.yige.common.service.impl;

import com.yige.common.base.CoreServiceImpl;
import com.yige.common.dao.DictDao;
import com.yige.common.domain.DictDO;
import com.yige.common.service.DictService;
import com.yige.sys.domain.UserDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author zoujm
 * @since 2018/12/1 14:35
 */
@Service
public class DictServiceImpl extends CoreServiceImpl<DictDao, DictDO> implements DictService {

    @Autowired
    private DictDao dictDao;

    @Override
    public List<DictDO> listType() {
        return dictDao.listType();
    }

    @Override
    public String getName(String type, String value) {
        DictDO selectOne = DictDO.dao.selectOne("type = {0} and value = {1}", type, value);
        return selectOne == null ? "" : selectOne.getName();
    }

    @Override
    public List<DictDO> getHobbyList(UserDO userDO) {
        List<DictDO> hobbyList = DictDO.dao.selectList("type = {0}", "hobby");

        if (StringUtils.isNotEmpty(userDO.getHobby())) {
            String userHobbys[] = userDO.getHobby().split(";");
            for (String userHobby : userHobbys) {
                for (DictDO hobby : hobbyList) {
                    if (!Objects.equals(userHobby, hobby.getId().toString())) {
                        continue;
                    }
                    hobby.setRemarks("true");
                    break;
                }
            }
        }

        return hobbyList;
    }

    public List<DictDO> getListByType(String type) {
        return DictDO.dao.selectList("type = {0}", type);
    }

}
