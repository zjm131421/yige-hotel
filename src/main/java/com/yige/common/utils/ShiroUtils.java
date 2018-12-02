package com.yige.common.utils;

import com.yige.sys.domain.UserDO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class ShiroUtils {
	public static Subject getSubjct() {
		return SecurityUtils.getSubject();
	}
	

	// admin
	public static UserDO getSysUser() {
		return (UserDO)getSubjct().getPrincipal();
	}

	public static Long getUserId() {
		return getSysUser().getId();
	}
	
	public static void logout() {
		getSubjct().logout();
	}
}
