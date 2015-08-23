package com.yueqiu.model;

import java.io.Serializable;

/**
 * Created on 15/6/20.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String desc;
    private float price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
