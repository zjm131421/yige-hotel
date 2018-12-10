package com.yige.hotel.enums;

/**
 * 房间状态枚举
 * @author zoujm
 * @since 2018/12/2 17:52
 */
public enum RoomBookStatus {

    WRZ(0,"未入住"),
    YYD(1,"已预订"),
    YRZ(2,"已入住"),
    DTF(3,"待退房"),
    YTF(4,"已退房"),

    ;

    private int code;
    private String name;

    RoomBookStatus(int bh, String name) {
        this.code = bh;
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
