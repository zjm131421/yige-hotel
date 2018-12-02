package com.yige.sys.service.impl;

import com.yige.common.base.CoreServiceImpl;
import com.yige.sys.dao.RoleDao;
import com.yige.sys.dao.RoleMenuDao;
import com.yige.sys.dao.UserRoleDao;
import com.yige.sys.domain.RoleDO;
import com.yige.sys.domain.RoleMenuDO;
import com.yige.sys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zoujm
 * @since 2018/12/1 14:46
 */
@Service
public class RoleServiceImpl extends CoreServiceImpl<RoleDao, RoleDO> implements RoleService {

    public static final String ROLE_ALL_KEY = "\"role_all\"";

    public static final String DEMO_CACHE_NAME = "role";

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleMenuDao roleMenuDao;

    @Override
    public List<RoleDO> findAll() {
        List<RoleDO> roles = selectList(null);
        return roles;
    }

    @Override
    public List<RoleDO> findListByUserId(Serializable userId) {
        List<Long> rolesIds = userRoleDao.listRoleId(userId);
        List<RoleDO> roles = selectList(null);
        for (RoleDO roleDO : roles) {
            roleDO.setRoleSign("false");
            for (Long roleId : rolesIds) {
                if (Objects.equals(roleDO.getId(), roleId)) {
                    roleDO.setRoleSign("true");
                    break;
                }
            }
        }
        return roles;
    }

    @CacheEvict(value = DEMO_CACHE_NAME, key = ROLE_ALL_KEY)
    @Transactional
    @Override
    public boolean insert(RoleDO role) {
        int count = baseMapper.insert(role);
        List<Long> menuIds = role.getMenuIds();
        Long roleId = role.getId();
        List<RoleMenuDO> rms = handleRoleMenu(roleId,menuIds);
        roleMenuDao.removeByRoleId(roleId);
        if (rms.size() > 0) {
            roleMenuDao.batchSave(rms);
        }
        return retBool(count);
    }

    @CacheEvict(value = DEMO_CACHE_NAME, key = ROLE_ALL_KEY)
    @Override
    public boolean updateById(RoleDO role) {
        int r = baseMapper.updateById(role);
        List<Long> menuIds = role.getMenuIds();
        Long roleId = role.getId();
        roleMenuDao.removeByRoleId(roleId);
        List<RoleMenuDO> rms = handleRoleMenu(roleId,menuIds);
        if (rms.size() > 0) {
            roleMenuDao.batchSave(rms);
        }
        return retBool(r);
    }

    /**
     * 处理角色与菜单的关系
     * @param roleId 角色id
     * @param menuIds 菜单ids
     * @return List
     */
    private List<RoleMenuDO> handleRoleMenu(Long roleId,List<Long> menuIds){
        return menuIds.stream().map(menuId -> {
            RoleMenuDO rmDo = new RoleMenuDO();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            return rmDo;
        }).collect(Collectors.toList());
    }

    @CacheEvict(value = DEMO_CACHE_NAME, key = ROLE_ALL_KEY)
    @Transactional
    @Override
    public boolean deleteById(Serializable id) {
        int count = baseMapper.deleteById(id);
        roleMenuDao.removeByRoleId(id);
        return retBool(count);
    }

}
