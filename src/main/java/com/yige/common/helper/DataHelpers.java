package com.yige.common.helper;

import org.apache.commons.lang.StringUtils;

public class DataHelpers {

    public static boolean isEmptyString(String str) {
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmptyString(String str) {
        return !isEmptyString(str);
    }

    public static boolean isEmptyInt(long data) {
        return data == 0;
    }

    public static boolean isNotEmptyInt(long data) {
        return !isEmptyInt(data);
    }




}