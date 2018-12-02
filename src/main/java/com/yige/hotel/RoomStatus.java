package com.yige.hotel;

/**
 * 房间状态枚举
 * @author zoujm
 * @since 2018/12/2 17:52
 */
public enum RoomStatus {

    WRZ(0,"未入住"),
    YYD(1,"已预订"),
    YRZ(2,"已入住"),
    DTF(3,"待退房"),
    YTF(4,"已退房"),

    ;

    private int bh;
    private String name;

    RoomStatus(int bh, String name) {
        this.bh = bh;
        this.name = name;
    }

    public int getBh() {
        return bh;
    }

    public void setBh(int bh) {
        this.bh = bh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
