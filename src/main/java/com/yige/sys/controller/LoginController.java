package com.yige.sys.controller;

import com.yige.common.annotation.Log;
import com.yige.common.base.AdminBaseController;
import com.yige.common.domain.Tree;
import com.yige.common.enums.EnumErrorCode;
import com.yige.common.utils.MD5Utils;
import com.yige.common.utils.Result;
import com.yige.common.utils.ShiroUtils;
import com.yige.sys.domain.MenuDO;
import com.yige.sys.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.yige.common.utils.ShiroUtils.getUserId;

/**
 * @author zoujm
 * @since 2018/12/1 13:34
 */
@Controller
public class LoginController extends AdminBaseController {

    @Autowired
    private MenuService menuService;

    @GetMapping({ "/", "" })
    @Log("重定向到登录")
    public String welcome(Model model) {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @Log("登录")
    @PostMapping("/login")
    @ResponseBody
    public Result<String> ajaxLogin(String username, String password) {
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);//记住我是可选项，但只有会话缓存到redis等持久存储才能真正记住
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return Result.ok();
        } catch (AuthenticationException e) {
            return Result.build(EnumErrorCode.userLoginFail.getCode(), EnumErrorCode.userLoginFail.getMsg());
        }
    }

    @GetMapping("/logout")
    @Log("退出")
    String logout() {
        ShiroUtils.logout();
        return "redirect:/login";
    }

    @Log("请求访问主页")
    @GetMapping({ "/index" })
    public String index(Model model) {
        List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
        model.addAttribute("menus", menus);
        model.addAttribute("name", getUser().getName());
        model.addAttribute("username", getUser().getUsername());
//        FileDO fileDO = fileService.selectById(getUser().getPicId());
//        model.addAttribute("picUrl", fileDO == null ? "/img/photo_s.jpg" : fileDO.getUrl());
        return "index";
    }

    @Log("主页")
    @GetMapping("/main")
    String main() {
        return "main";
    }

    @Log("错误403")
    @GetMapping("/403")
    String error403() {
        return "403";
    }
}
