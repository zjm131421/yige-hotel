package com.yige.sys.service;

import com.yige.common.base.CoreService;
import com.yige.sys.domain.RoleDO;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author zoujm
 * @since 2018/12/1 14:45
 */
@Service
public interface RoleService extends CoreService<RoleDO> {

    List<RoleDO> findAll();
    List<RoleDO> findListByUserId(Serializable id);

}
