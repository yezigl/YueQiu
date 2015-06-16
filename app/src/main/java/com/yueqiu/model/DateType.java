package com.yueqiu.model;

/**
 * Created on 15/6/16.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public enum DateType {

    ALL(0, "全部"),
    TODAY(1, "今天"),
    TOMORROW(2, "明天"),
    LATER(3, "两天后");

    public int type;
    public String text;

    DateType(int type, String text) {
        this.type = type;
        this.text = text;
    }
}
