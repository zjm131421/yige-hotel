package com.yige.common.config;

import com.yige.common.helper.DataHelpers;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

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

}
