package com.yige.common.aspect;

import com.yige.common.annotation.Log;
import com.yige.common.base.BaseDO;
import com.yige.common.dao.LogDao;
import com.yige.common.domain.LogDO;
import com.yige.common.helper.DateHelpers;
import com.yige.common.utils.HttpContextUtils;
import com.yige.common.utils.IPUtils;
import com.yige.common.utils.JSONUtils;
import com.yige.common.utils.ShiroUtils;
import com.yige.sys.domain.UserDO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * 日志切面
 * @author zoujm
 * @since 2018/12/1 13:36
 */
@Aspect
@Component
public class LogAspect {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private LogDao logMapper;

    @Pointcut("@annotation(com.yige.common.annotation.Log)")
    public void logPointCut(){

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        saveLog(point, time);
        return result;
    }

    @Pointcut("execution(public * com.yige.*.controller.*.*(..))")
    public void logController(){}

    /** 记录controller日志，包括请求、ip、参数、响应结果 */
    @Around("logController()")
    public Object controller(ProceedingJoinPoint point) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("{} {} {} {}.{}{}", request.getMethod(), request.getRequestURI(), IPUtils.getIpAddr(request), point.getTarget().getClass().getSimpleName(), point.getSignature().getName(), Arrays.toString(point.getArgs()));

        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;

        log.info("result({}) {}", time, JSONUtils.beanToJson(result));
        return result;
    }

    @Pointcut("execution(public * com.yige.*.service.*.*(..))")
    public void logService(){}

    /** 记录自定义service接口日志，如果要记录CoreService所有接口日志请仿照logMapper切面 */
    @Around("logService()")
    public Object service(ProceedingJoinPoint point) throws Throwable {
        log.info("call {}.{}{}", point.getTarget().getClass().getSimpleName(), point.getSignature().getName(), Arrays.toString(point.getArgs()));

        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;

        log.info("result({}) {}", time, JSONUtils.beanToJson(result));
        return result;
    }

    @Pointcut("within(com.baomidou.mybatisplus.mapper.BaseMapper+)")
    public void logMapper(){}

    /** 记录mapper所有接口日志，设置createBy和updateBy基础字段，logback会记录sql，这里记录查库返回对象 */
    @Around("logMapper()")
    public Object mapper(ProceedingJoinPoint point) throws Throwable {
        String methodName = point.getSignature().getName();
        try {
            Subject subject = SecurityUtils.getSubject();
            if(subject.isAuthenticated()) {
                switch(methodName) {
                    case "insert":
                    case "insertAllColumn":
                        Object insert = point.getArgs()[0];
                        if(insert instanceof BaseDO) {
                            ((BaseDO)insert).setCreateBy(ShiroUtils.getUserId());
                        }
                        break;
                    case "update":
                    case "updateById":
                    case "updateAllColumnById":
                        Object update = point.getArgs()[0];
                        if(update instanceof BaseDO) {
                            ((BaseDO)update).setUpdateBy(ShiroUtils.getUserId());
                        }
                        break;
                }
            }
        }catch(Exception ignore) {}
        log.info("call {}.{}{}", point.getTarget().getClass().getSimpleName(), methodName, Arrays.toString(point.getArgs()));

        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;

        log.info("result({}) {}", time, JSONUtils.beanToJson(result));
        return result;
    }


    /**
     * 保存日志
     * @param joinPoint
     * @param time
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogDO logDO = new LogDO();
        Log log = method.getAnnotation(Log.class);
        if (log != null) {
            // 注解上的描述
            logDO.setOperation(log.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        logDO.setMethod(className + "." + methodName + "()");
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        Map<String, String[]> parameterMap = HttpContextUtils.getHttpServletRequest().getParameterMap();
        try{
            String params = JSONUtils.beanToJson(parameterMap);
            int maxLength = 4999;
            if(params.length() > maxLength){
                params = params.substring(0, maxLength);
            }
            logDO.setParams(params);
        }catch (Exception e){
            this.log.error("保存日志异常：",e);
        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        logDO.setIp(IPUtils.getIpAddr(request));
        // 用户名
        UserDO currUser = ShiroUtils.getSysUser();
        if (null == currUser) {
            if (null != logDO.getParams()) {
                logDO.setUserId(-1L);
                logDO.setUsername(logDO.getParams());
            } else {
                logDO.setUserId(-1L);
                logDO.setUsername("获取用户信息为空");
            }
        } else {
            logDO.setUserId(ShiroUtils.getUserId());
            logDO.setUsername(ShiroUtils.getSysUser().getUsername());
        }
        logDO.setTime((int) time);
        logDO.setCreateTime(DateHelpers.now());
//        logMapper.insert(logDO);
    }

}
