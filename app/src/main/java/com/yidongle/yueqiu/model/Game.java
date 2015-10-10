/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yidongle.yueqiu.model;

import java.io.Serializable;
import java.util.List;

/**
 * TODO 在这里编写类的功能描述
 *
 * @author lidehua
 * @version 1.0
 * @since 4.1
 */
public class Game implements Serializable {

    public static final int DATE_TYPE_ALL = 0;
    public static final int DATE_TYPE_TODAY = 1;
    public static final int DATE_TYPE_TOMM = 2;
    public static final int DATE_TYPE_LATER = 3;

    private String id;
    private String title;
    private int type;
    private Stadium stadium;
    private String date;
    private float price;
    private float value;
    private int total;
    private int attend;
    private User organizer;
    private List<User> players;
    private int status;
    private String statusStr;
    private OrderInfo orderInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getAttend() {
        return attend;
    }

    public void setAttend(int attend) {
        this.attend = attend;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public List<User> getPlayers() {
        return players;
    }

    public void setPlayers(List<User> players) {
        this.players = players;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public static class OrderInfo {
        private boolean canBuy;
        private boolean hasBuy;
        private boolean isPayed;
        private String orderId;

        public boolean isCanBuy() {
            return canBuy;
        }

        public void setCanBuy(boolean canBuy) {
            this.canBuy = canBuy;
        }

        public boolean isHasBuy() {
            return hasBuy;
        }

        public void setHasBuy(boolean hasBuy) {
            this.hasBuy = hasBuy;
        }

        public boolean isPayed() {
            return isPayed;
        }

        public void setIsPayed(boolean isPayed) {
            this.isPayed = isPayed;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }
}
