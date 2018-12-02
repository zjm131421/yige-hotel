package com.yige.sys.controller;

import com.yige.common.annotation.Log;
import com.yige.common.base.AdminBaseController;
import com.yige.common.utils.Result;
import com.yige.sys.domain.RoleDO;
import com.yige.sys.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * 部门
 * @author zoujm
 * @since 2018/12/2 11:02
 */
@RequestMapping("/sys/role")
@Controller
public class RoleController extends AdminBaseController {
    String prefix = "sys/role";
    @Autowired
    private RoleService roleService;
    
    @Log("进入系统角色页面")
    @RequiresPermissions("sys:role:role")
    @GetMapping()
    String role() {
        return prefix + "/role";
    }
    
    @Log("查询系统角色菜单")
    @RequiresPermissions("sys:role:role")
    @GetMapping("/list")
    @ResponseBody()
    List<RoleDO> list() {
        List<RoleDO> roles = roleService.findAll();
        return roles;
    }

    @Log("添加角色")
    @RequiresPermissions("sys:role:add")
    @GetMapping("/add")
    String add() {
        return prefix + "/add";
    }

    @Log("编辑角色")
    @RequiresPermissions("sys:role:edit")
    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Long id, Model model) {
        RoleDO roleDO = roleService.selectById(id);
        model.addAttribute("role", roleDO);
        return prefix + "/edit";
    }

    @Log("保存角色")
    @RequiresPermissions("sys:role:add")
    @PostMapping("/save")
    @ResponseBody()
    Result<String> save(RoleDO role) {
        roleService.insert(role);
        return Result.ok();
    }

    @Log("更新角色")
    @RequiresPermissions("sys:role:edit")
    @PostMapping("/update")
    @ResponseBody()
    Result<String> update(RoleDO role) {
        roleService.updateById(role);
        return Result.ok();
    }
    
    @Log("删除角色")
    @RequiresPermissions("sys:role:remove")
    @PostMapping("/remove")
    @ResponseBody()
    Result<String> save(Long id) {
        roleService.deleteById(id);
        return Result.ok();
    }
    
    @RequiresPermissions("sys:role:batchRemove")
    @Log("批量删除角色")
    @PostMapping("/batchRemove")
    @ResponseBody
    Result<String> batchRemove(@RequestParam("ids[]") Long[] ids) {
        roleService.deleteBatchIds(Arrays.asList(ids));
        return Result.ok();
    }
}
