package com.yige.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zoujm
 * @since 2018/12/1 13:57
 */
public class HttpContextUtils {

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getHttpServletResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static boolean isAjax(){
        HttpServletRequest httpRequest = HttpContextUtils.getHttpServletRequest();
        String requestHeader = httpRequest.getHeader("X-Requested-With");
        return  requestHeader != null && "XMLHttpRequest".equals(requestHeader);
    }

}
