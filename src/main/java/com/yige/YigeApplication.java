package com.yige;

import com.yige.common.config.AppConfig;
import com.yige.common.utils.SpringContextHolder;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 入口
 */
@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.yige.*.dao")
@SpringBootApplication
public class YigeApplication {

    private static Logger log = LoggerFactory.getLogger(YigeApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(YigeApplication.class, args);
        printProjectConfigs();
    }

    /**
     * 打印配置参数
     */
    private static void printProjectConfigs() {
        ServerProperties serverProperties = SpringContextHolder.getApplicationContext().getBean(ServerProperties.class);
        DataSourceProperties dataSourceProperties = SpringContextHolder.getApplicationContext().getBean(DataSourceProperties.class);
        AppConfig config = SpringContextHolder.getApplicationContext().getBean(AppConfig.class);
        log.info("开启演示模式：" + config.isDemoMode());
        log.info("开启调试模式：" + config.isDevMode());
        log.info("数据库：" + dataSourceProperties.getUrl());
        log.info("==================> run at http://localhost:" + serverProperties.getPort() + "  <==================");
    }
}
