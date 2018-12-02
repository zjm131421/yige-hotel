package com.yige.common.base;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 通用业务层实现
 * @author zoujm
 * @since 2018/12/1 12:52
 */
public interface CoreService<T> extends IService<T> {

    List<T> findByKv(Object... param);

    T findOneByKv(Object... param);

    Map<String, Object> convertToMap(Object... param);

    EntityWrapper<T> convertToEntityWrapper(Object... params);
}
