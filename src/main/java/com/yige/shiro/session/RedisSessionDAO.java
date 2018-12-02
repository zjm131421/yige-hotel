package com.yige.shiro.session;

import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

/**
 * shiro会话缓存
 * @author zoujm
 * @since 2018/12/1 16:17
 */
public class RedisSessionDAO extends EnterpriseCacheSessionDAO {

    private String activeSessionsCacheName;

    public RedisSessionDAO(String activeSessionsCacheName){
        this.activeSessionsCacheName = activeSessionsCacheName;
    }

    @Override public String getActiveSessionsCacheName() {
        return this.activeSessionsCacheName;
    }


}
