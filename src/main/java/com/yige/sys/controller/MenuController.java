package com.yige.sys.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yige.common.annotation.Log;
import com.yige.common.base.AdminBaseController;
import com.yige.common.domain.Tree;
import com.yige.common.utils.Result;
import com.yige.sys.domain.MenuDO;
import com.yige.sys.service.MenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zoujm
 * @since 2018/12/1 20:15
 */
@RequestMapping("/sys/menu")
@Controller
public class MenuController extends AdminBaseController {

    @Autowired
    private MenuService menuService;

    private static final String PREFIX = "sys/menu";

    @Log("进入系统菜单页面")
    @RequiresPermissions("sys:menu:menu")
    @GetMapping()
    String menu(Model model) {
        return PREFIX + "/menu";
    }

    @Log("查询菜单列表")
    @RequiresPermissions("sys:menu:menu")
    @RequestMapping("/list")
    @ResponseBody
    List<MenuDO> list() {
        return menuService.selectList(null);
    }

    @Log("添加菜单")
    @RequiresPermissions("sys:menu:add")
    @GetMapping("/add/{pId}")
    String add(Model model, @PathVariable("pId") Long pId) {
        model.addAttribute("pId", pId);
        if (pId == 0) {
            model.addAttribute("pName", "根目录");
        } else {
            model.addAttribute("pName", menuService.selectById(pId).getName());
        }
        return PREFIX + "/add";
    }

    @Log("编辑菜单")
    @RequiresPermissions("sys:menu:edit")
    @GetMapping("/edit/{id}")
    String edit(Model model, @PathVariable("id") Long id) {
        MenuDO mdo = menuService.selectById(id);
        Long pId = mdo.getParentId();
        model.addAttribute("pId", pId);
        if (pId == 0) {
            model.addAttribute("pName", "根目录");
        } else {
            model.addAttribute("pName", menuService.selectById(pId).getName());
        }
        model.addAttribute("menu", mdo);
        return PREFIX + "/edit";
    }

    @Log("保存菜单")
    @RequiresPermissions("sys:menu:add")
    @PostMapping("/save")
    @ResponseBody
    Result<String> save(MenuDO menu) {
        menuService.insert(menu);
        return Result.ok();
    }

    @Log("更新菜单")
    @RequiresPermissions("sys:menu:edit")
    @PostMapping("/update")
    @ResponseBody
    Result<String> update(MenuDO menu) {
        menuService.updateById(menu);
        return Result.ok();
    }

    @Log("删除菜单")
    @RequiresPermissions("sys:menu:remove")
    @PostMapping("/remove")
    @ResponseBody
    @SuppressWarnings("unchecked")
    Result<String> remove(Long id) {
//        menuService.deleteById(id);
        MenuDO menuDO = new MenuDO();
        menuDO.setParentId(id);
        EntityWrapper ew = new EntityWrapper<>(menuDO);
        menuService.deleteById(id);
        menuService.delete(ew);
        return Result.ok();
    }

    @Log("查询菜单树形数据")
    @GetMapping("/tree")
    @ResponseBody
    Tree<MenuDO> tree() {
        Tree<MenuDO> tree = new Tree<>();
        tree = menuService.getTree();
        return tree;
    }

    @Log("根据角色ID查询菜单树形数据")
    @GetMapping("/tree/{roleId}")
    @ResponseBody
    Tree<MenuDO> tree(@PathVariable("roleId") Long roleId) {
        Tree<MenuDO> tree = menuService.getTree(roleId);
        return tree;
    }
}
