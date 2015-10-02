package com.yidongle.yueqiu.model;

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
    private int quantity;
    private int status;
    private String createTime;
    private String payTime;
    private String payType;
    private Game activity;
    private Coupon coupon;

    public String getPayTypeCN() {
        if ("ALIPAY".equals(payType)) {
            return "支付宝";
        } else if ("WEIXIN".equals(payType)) {
            return "微信";
        }
        return "";
    }

    public boolean isNew() {
        return status == 0;
    }

    public boolean isCreated() {
        return status == 1;
    }

    public boolean isPayed() {
        return  status == 2;
    }

    public boolean isRefund() {
        return status == 4;
    }

    public boolean isSignin() {
        return status == 16;
    }

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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
