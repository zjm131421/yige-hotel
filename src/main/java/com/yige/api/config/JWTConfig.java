package com.yige.api.config;

import lombok.Data;

/**
 * @author zoujm
 * @since 2018/12/1 12:36
 */
@Data
public class JWTConfig {
    private String userPrimaryKey;
    /**
     * jwt过期时间,默认2小时，单位为毫秒
     */
    private Long expireTime = 7200000L;
    /**
     *  refresh_token过期时间，默认7天，单位为毫秒
     */
    private Long refreshTokenExpire = 604800000L;
}
