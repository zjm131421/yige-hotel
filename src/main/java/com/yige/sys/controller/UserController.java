package com.yige.sys.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.yige.common.annotation.Log;
import com.yige.common.base.AdminBaseController;
import com.yige.common.domain.DictDO;
import com.yige.common.domain.Tree;
import com.yige.common.service.DictService;
import com.yige.common.utils.MD5Utils;
import com.yige.common.utils.Result;
import com.yige.sys.domain.DeptDO;
import com.yige.sys.domain.RoleDO;
import com.yige.sys.domain.UserDO;
import com.yige.sys.service.RoleService;
import com.yige.sys.service.UserService;
import com.yige.sys.vo.UserVO;
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
import java.util.Map;

import static org.apache.naming.SelectorContext.prefix;

/**
 * @author zoujm
 * @since 2018/12/1 13:33
 */
@RequestMapping("/sys/user")
@Controller
public class UserController extends AdminBaseController {
    private static final String PREFIX = "sys/user";
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DictService dictService;

    @Log("进入系统用户列表页面")
    @RequiresPermissions("sys:user:user")
    @GetMapping("")
    String user(Model model) {
        return PREFIX + "/user";
    }

    @Log("查询系统用户列表")
    @GetMapping("/list")
    @ResponseBody
    public Result<Page<UserDO>> list(UserDO userDTO) {
        // 查询列表数据
        Page<UserDO> page = userService.selectPage(getPage(UserDO.class), userService.convertToEntityWrapper("name", userDTO.getName(), "dept_id", userDTO.getDeptId()));
        return Result.ok(page);
    }


    @RequiresPermissions("sys:user:add")
    @Log("添加用户")
    @GetMapping("/add")
    String add(Model model) {
        List<RoleDO> roles = roleService.selectList(null);
        model.addAttribute("roles", roles);
        return PREFIX + "/add";
    }

    @RequiresPermissions("sys:user:edit")
    @Log("编辑用户")
    @GetMapping("/edit/{id}")
    String edit(Model model, @PathVariable("id") Long id) {
        UserDO userDO = userService.selectById(id);
        model.addAttribute("user", userDO);
        List<RoleDO> roles = roleService.findListByUserId(id);
        model.addAttribute("roles", roles);
        return PREFIX + "/edit";
    }

    @RequiresPermissions("sys:user:add")
    @Log("保存用户")
    @PostMapping("/save")
    @ResponseBody
    Result<String> save(UserDO user) {
        user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
        userService.insert(user);
        return Result.ok();
    }

    @RequiresPermissions("sys:user:edit")
    @Log("更新用户")
    @PostMapping("/update")
    @ResponseBody
    Result<String> update(UserDO user) {
        userService.updateById(user);
        return Result.ok();
    }

    @RequiresPermissions("sys:user:edit")
    @Log("更新用户")
    @PostMapping("/updatePeronal")
    @ResponseBody
    Result<String> updatePeronal(UserDO user) {
        userService.updatePersonal(user);
        return Result.ok();
    }

    @RequiresPermissions("sys:user:remove")
    @Log("删除用户")
    @PostMapping("/remove")
    @ResponseBody
    Result<String> remove(Long id) {
        userService.deleteById(id);
        return Result.ok();
    }

    @RequiresPermissions("sys:user:batchRemove")
    @Log("批量删除用户")
    @PostMapping("/batchRemove")
    @ResponseBody
    Result<String> batchRemove(@RequestParam("ids[]") Long[] userIds) {
        userService.deleteBatchIds(Arrays.asList(userIds));
        return Result.ok();
    }

    @Log("退出")
    @PostMapping("/exit")
    @ResponseBody
    boolean exit(@RequestParam Map<String, Object> params) {
        // 存在，不通过，false
        return !userService.exit(params);
    }

    @RequiresPermissions("sys:user:resetPwd")
    @Log("请求更改用户密码")
    @GetMapping("/resetPwd/{id}")
    String resetPwd(@PathVariable("id") Long userId, Model model) {

        UserDO userDO = new UserDO();
        userDO.setId(userId);
        model.addAttribute("user", userDO);
        return PREFIX + "/reset_pwd";
    }

    @Log("提交更改用户密码")
    @PostMapping("/resetPwd")
    @ResponseBody
    Result<String> resetPwd(UserVO userVO) {
        userService.resetPwd(userVO, getUser());
        return Result.ok();
    }

    @RequiresPermissions("sys:user:resetPwd")
    @Log("admin提交更改用户密码")
    @PostMapping("/adminResetPwd")
    @ResponseBody
    Result<String> adminResetPwd(UserVO userVO) {
        userService.adminResetPwd(userVO);
        return Result.ok();

    }

    @Log("查询系统用户属性树形数据")
    @GetMapping("/tree")
    @ResponseBody
    public Tree<DeptDO> tree() {
        Tree<DeptDO> tree = new Tree<>();
        tree = userService.getTree();
        return tree;
    }

    @Log("进入系统用户树形显示页面")
    @GetMapping("/treeView")
    String treeView() {
        return PREFIX + "/userTree";
    }

    @Log("进入个人中心")
    @GetMapping("/personal")
    String personal(Model model) {
        UserDO userDO = userService.selectById(getUserId());
        model.addAttribute("user", userDO);
        List<DictDO> hobbyList = dictService.getHobbyList(userDO);
        model.addAttribute("hobbyList", hobbyList);
        List<DictDO> sexList = dictService.getListByType("sex");
        model.addAttribute("sexList", sexList);
        return PREFIX + "/personal";
    }



}
