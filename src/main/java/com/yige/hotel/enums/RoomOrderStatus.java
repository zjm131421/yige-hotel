package com.yige.hotel.enums;

/**
 * @author zoujm
 * @since 2018/12/13 10:57
 */
public enum RoomOrderStatus {

    CSZT(0,"初始"),
    WJZT(1,"完结状态")
    ;

    private int code;
    private String name;

    RoomOrderStatus(int code, String name) {
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
