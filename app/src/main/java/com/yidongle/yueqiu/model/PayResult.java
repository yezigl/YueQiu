package com.yidongle.yueqiu.model;

/**
 * Created on 15/10/1.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class PayResult {

    private int status;
    private String type;
    private String payTime;
    private Order order;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
