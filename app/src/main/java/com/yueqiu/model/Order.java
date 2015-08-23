package com.yueqiu.model;

import java.io.Serializable;

/**
 * Created on 15/6/11.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private float amount;
    private float discount;
    private int status;
    private String createTime;
    private String paytime;
    private String paytype;
    private Game activity;
    private Coupon coupon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public Game getActivity() {
        return activity;
    }

    public void setActivity(Game activity) {
        this.activity = activity;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
}
