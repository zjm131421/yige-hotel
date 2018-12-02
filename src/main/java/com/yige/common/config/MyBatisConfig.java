package com.yige.common.config;

import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zoujm
 * @since 2018/12/2 14:32
 */
@Configuration
public class MyBatisConfig {

    /**
     * 分页插件
     *
     * @return
     * @author zhongweiyuan
     * @date 2018年4月14日下午4:13:15
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

}
