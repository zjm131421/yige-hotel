package com.yige.hotel.enums;

/**
 * @author zoujm
 * @since 2018/12/9 22:26
 */
public enum  Enabled {
    WX(0,"无效"),
    YX(1,"有效")
    ;

    private int code;
    private String name;

    Enabled(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
