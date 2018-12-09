package com.yige.hotel.enums;

/**
 * @author zoujm
 * @since 2018/12/9 22:23
 */
public enum  RoomBookStatus {

    YYD(0,"已预定"),
    YRZ(1,"已入住"),
    WRZ(2,"未入住"),
    ;

    private int code;
    private String name;

    RoomBookStatus(int code, String name) {
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
