package com.yige.sys.vo;

import com.yige.sys.domain.UserDO;
import lombok.Data;

/**
 * @author zoujm
 * @since 2018/12/1 15:48
 */
@Data
public class UserVO {

    /**
     * 更新的用户对象
     */
    private UserDO userDO = new UserDO();
    /**
     * 旧密码
     */
    private String pwdOld;
    /**
     * 新密码
     */
    private String pwdNew;

}
