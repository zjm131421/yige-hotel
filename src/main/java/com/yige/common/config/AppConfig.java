package com.yige.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.yige.api.config.JWTConfig;
import com.yige.common.helper.DataHelpers;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author zoujm
 * @since 2018/12/1 12:34
 */
@Component
@ConfigurationProperties(prefix = "yige")
@Data
public class AppConfig {

    /**
     * 项目名，末尾不带 "/"
     */
    private String projectName;
    /**
     * 项目根目录，末尾带 "/"
     */
    private String projectRootURL;

    /**
     * 演示模式
     */
    private boolean demoMode;
    /**
     * 调试模式
     */
    private boolean devMode;

    private String timezone;

    private JWTConfig jwt;

    public ZoneOffset getTimezone() {
        try {
            if (DataHelpers.isEmptyString(timezone)) {
                return ZoneOffset.UTC;
            }
            return ZoneOffset.of(timezone);
        }
        catch (Throwable ignored) {
            return ZoneOffset.UTC;
        }
    }

//    @Bean(name = "mapperObject")
//    public ObjectMapper getObjectMapper() {
//        ObjectMapper om = new ObjectMapper();
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
//        om.registerModule(javaTimeModule);
//        return om;
//    }



}
