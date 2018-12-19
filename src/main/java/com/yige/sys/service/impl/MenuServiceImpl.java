package com.yige.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yige.common.base.CoreServiceImpl;
import com.yige.common.domain.Tree;
import com.yige.common.utils.BuildTree;
import com.yige.sys.dao.MenuDao;
import com.yige.sys.dao.RoleMenuDao;
import com.yige.sys.domain.MenuDO;
import com.yige.sys.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zoujm
 * @since 2018/12/1 15:21
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl extends CoreServiceImpl<MenuDao, MenuDO> implements MenuService {

    /**
     *
     */
    @Autowired
    private RoleMenuDao roleMenuDao;

    @Override
    public Tree<MenuDO> getTree() {
        List<Tree<MenuDO>> trees = new ArrayList<>();
        List<MenuDO> menuDOs = baseMapper.selectList(null);
        for (MenuDO sysMenuDO : menuDOs) {
            Tree<MenuDO> tree = new Tree<>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        return BuildTree.build(trees);
    }

    /**
     * 获取系统菜单树
     * @param id 菜单id
     * @return Tree
     */
    @Override
    public Tree<MenuDO> getSysMenuTree(Long id) {
        List<MenuDO> menuDOs = baseMapper.listMenuByUserId(id);
        // 默认顶级菜单为０，根据数据库实际情况调整
        return BuildTree.build(handleMenuDOs(menuDOs));
    }

    /**
     * 处理菜单
     * @param menuDOs 菜单列表
     * @return List
     */
    private List<Tree<MenuDO>> handleMenuDOs(List<MenuDO> menuDOs){
        return menuDOs.stream().map(menuDO -> {
            Tree<MenuDO> tree = new Tree<MenuDO>();
            tree.setId(menuDO.getId().toString());
            tree.setParentId(menuDO.getParentId().toString());
            tree.setText(menuDO.getName());
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", menuDO.getUrl());
            attributes.put("icon", menuDO.getIcon());
            tree.setAttributes(attributes);
            return tree;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Tree<MenuDO>> listMenuTree(Long id) {
        List<MenuDO> menuDOs = baseMapper.listMenuByUserId(id);
        // 默认顶级菜单为０，根据数据库实际情况调整
        return BuildTree.buildList(handleMenuDOs(menuDOs), "0");
    }

    @Override
    public Tree<MenuDO> getTree(Long id) {
        // 根据roleId查询权限
        List<MenuDO> menus = baseMapper.selectList(null);
        List<Long> menuIds = roleMenuDao.listMenuIdByRoleId(id);
        List<Long> temp = menuIds;
        for (MenuDO menu : menus) {
            if (temp.contains(menu.getParentId())) {
                menuIds.remove(menu.getParentId());
            }
        }
        List<Tree<MenuDO>> trees = new ArrayList<Tree<MenuDO>>();
        List<MenuDO> menuDOs = baseMapper.selectList(null);
        for (MenuDO sysMenuDO : menuDOs) {
            Tree<MenuDO> tree = new Tree<MenuDO>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            Map<String, Object> state = new HashMap<>(16);
            Long menuId = sysMenuDO.getId();
            if (menuIds.contains(menuId)) {
                state.put("selected", true);
            } else {
                state.put("selected", false);
            }
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<MenuDO> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public Set<String> listPerms(Long userId) {
        List<String> perms = baseMapper.listUserPerms(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotBlank(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

}
