package com.yige.sys.service;

import com.yige.common.base.CoreService;
import com.yige.common.domain.Tree;
import com.yige.sys.domain.MenuDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface MenuService extends CoreService<MenuDO> {

    Tree<MenuDO> getSysMenuTree(Long id);

    List<Tree<MenuDO>> listMenuTree(Long id);

    Tree<MenuDO> getTree();

    Tree<MenuDO> getTree(Long id);

    Set<String> listPerms(Long userId);

}
