package com.yige.hotel.enums;

/**
 * @author zoujm
 * @since 2018/12/17 1:43
 */
public enum  Payments {

    CASH(1,"现金"),
    ALIPAY(2,"支付宝"),
    WECHAT(3,"微信"),

    ;

    private int code;
    private String name;

    Payments(int code, String name) {
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
