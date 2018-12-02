package com.yige.common.helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;

public class TypeHelpers {

    public static boolean isPrimitive(Class<?> clazz) {
        return  isNumber(clazz)
                || clazz.equals(boolean.class)
                || clazz.equals(Boolean.class)
                || clazz.equals(char.class)
                || clazz.equals(Character.class)
                || clazz.equals(String.class)
                || clazz.equals(LocalTime.class)
                || clazz.equals(LocalDate.class)
                || clazz.equals(LocalDateTime.class)
                ;
    }

    public static boolean isNotPrimitive(Class<?> clazz) {
        return !isPrimitive(clazz);
    }

    public static boolean isCollection(Class<?> clazz) {
        return isOrSubClass(clazz, Collection.class);
    }

    public static boolean isNotCollection(Class<?> clazz) {
        return ! isCollection(clazz);
    }

    public static boolean isMap(Class<?> clazz) {
        return isOrSubClass(clazz, Map.class);
    }

    public static boolean isNotMap(Class<?> clazz) {
        return ! isMap(clazz);
    }

    public static boolean isNumber(Class<?> clazz) {
        return clazz.equals(byte.class)
                || clazz.equals(Byte.class)
                || clazz.equals(short.class)
                || clazz.equals(Short.class)
                || clazz.equals(int.class)
                || clazz.equals(Integer.class)
                || clazz.equals(long.class)
                || clazz.equals(Long.class)
                || clazz.equals(float.class)
                || clazz.equals(Float.class)
                || clazz.equals(double.class)
                || clazz.equals(Double.class);
    }

    public static boolean isArray(Class<?> clazz) {
        return clazz.isArray();
    }

    public static boolean isOrSubClass(Class<?> clazz, Class<?> target) {
        return target.isAssignableFrom(clazz);
    }

    static Class<?> getWrapperClass(Class<?> clazz) {
        if (clazz.equals(boolean.class)) {
            return Boolean.class;
        }
        if (clazz.equals(byte.class)) {
            return Byte.class;
        }
        if (clazz.equals(short.class)) {
            return Short.class;
        }
        if (clazz.equals(int.class)) {
            return Integer.class;
        }
        if (clazz.equals(long.class)) {
            return Long.class;
        }
        if (clazz.equals(float.class)) {
            return Float.class;
        }
        if (clazz.equals(double.class)) {
            return Double.class;
        }
        if (clazz.equals(char.class)) {
            return Character.class;
        }

        return clazz;
    }

    static boolean isFloatOrDouble(Class<?> clazz) {
        return clazz.equals(float.class) || clazz.equals(Float.class) || clazz.equals(double.class) || clazz.equals(Double.class);
    }
}
