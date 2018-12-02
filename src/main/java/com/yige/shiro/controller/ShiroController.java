package com.yige.shiro.controller;

import com.yige.common.base.AdminBaseController;
import com.yige.common.enums.EnumErrorCode;
import com.yige.common.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zoujm
 * @since 2018/12/1 18:34
 */
@RestController
@RequestMapping("/shiro")
public class ShiroController extends AdminBaseController {

    @RequestMapping("/405")
    public Result<String> http405() {
        return Result.build(EnumErrorCode.apiAuthorizationInvalid.getCode(), EnumErrorCode.apiAuthorizationInvalid.getMsg());
    }

    @RequestMapping("/500")
    public Result<String> http500() {
        return Result.build(EnumErrorCode.unknowFail.getCode(), EnumErrorCode.unknowFail.getMsg());
    }
}

