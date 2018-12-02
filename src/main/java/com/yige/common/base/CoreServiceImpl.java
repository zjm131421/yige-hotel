package com.yige.common.base;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用业务层实现
 * @author zoujm
 * @since 2018/12/1 12:54
 */
public abstract class CoreServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> implements CoreService<T> {
    /**
     *
     */
    public Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<T> findByKv(Object... params) {
        if (params == null) {
            return new ArrayList<>();
        }
        return baseMapper.selectByMap(convertToMap(params));
    }

    @Override
    public T findOneByKv(Object... params) {
        return selectOne(this.convertToEntityWrapper(params));
    }

    @Override
    public Map<String, Object> convertToMap(Object... params) {
        Map<String, Object> map = new HashMap<>();
        if (params == null) {
            return map;
        }
        for (int i = 0; i < params.length; i++) {
            if (i % 2 == 1 && params[i] != null && StringUtils.isNotBlank(params[i].toString())) {
                map.put((String) params[i - 1], params[i]);
            }
        }
        return map;
    }
    @Override
    public EntityWrapper<T> convertToEntityWrapper(Object... params) {
        EntityWrapper<T> ew = new EntityWrapper<>();
        if (params == null) {
            return ew;
        }
        for (int i = 0; i < params.length; i++) {
            if (i % 2 == 1 && params[i] != null && StringUtils.isNotBlank(params[i].toString())) {
                ew.eq((String) params[i - 1], params[i]);
            }
        }
        return ew;
    }

}
