package com.yige.sys.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zoujm
 * @since 2018/12/1 12:58
 */
@TableName("users")
@Data
public class UserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    // 用户名
    private String username;
    // 用户真实姓名
    private String name;
    private String password;
    private Long deptId;
    @TableField(exist = false)
    private String deptName;
    private String email;
    private String mobile;
    //性别
    private Long sex;
    //出身日期
    private LocalDate birth;
    //现居住地
    private String liveAddress;
    //爱好
    private String hobby;
    //省份
    private String province;
    //所在城市
    private String city;
    //所在地区
    private String district;
    private Long userIdCreate;
    private boolean enabled;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    //角色
    @TableField(exist = false)
    private List<Long> roleIds;

}
