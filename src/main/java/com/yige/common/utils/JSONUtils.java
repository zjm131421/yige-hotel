package com.yige.common.utils;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

/**
 * Jackson工具类
 * @author zoujm
 * @since 2018/12/1 13:58
 */
public class JSONUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Bean对象转JSON
     */
    public static String beanToJson(Object object) {
        if (object != null) {
            try {
                return mapper.writeValueAsString(object);
            }catch (JsonProcessingException e){
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }


    /**
     * 将json字符串转换成对象
     */
    public static <T> T jsonToBean(String json, Class<T> beanType) {
        try {
            T t = mapper.readValue(json, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = mapper.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * json字符串转map
     *
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMap(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return jsonToBean(json, Map.class);
    }

}
