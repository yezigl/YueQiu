package com.yidongle.yueqiu.model;

/**
 * Created on 15/6/20.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public enum OrderStatus {

    ALL("全部", 0),
    UNPAY("未付款", 1),
    PAYED("已付款", 2);

    public String text;
    public int code;

    OrderStatus(String text, int code) {
        this.text = text;
        this.code = code;
    }
}
