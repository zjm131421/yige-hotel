package com.yige.common.base;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.Version;
import lombok.Data;

import java.util.Date;

/**
 * @author zoujm
 * @since 2018/12/1 12:50
 */
@Data
public class BaseDO {
    /** 由mybatis-plus.global-config.sql-injector:com.baomidou.mybatisplus.mapper.LogicSqlInjector自动维护 */
    @TableLogic
    private boolean deleted;
    /** 由MyBatisConfig.optimisticLockerInterceptor自动维护 */
    @Version
    private int version;
    /** 由MySQL自动维护 */
    private Date createAt;
    private Date updateAt;
    /** 由LogAspect.logMapper自动维护 */
    private Long createBy;
    private Long updateBy;

}
