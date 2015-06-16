/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 */
package com.yueqiu.model;

import java.util.List;

/**
 * TODO 在这里编写类的功能描述
 *
 * @author lidehua
 * @version 1.0
 * @since 4.1
 */
public class Game {

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
    private String orderId;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getAttend() {
        return attend;
    }

    public void setAttend(Integer attend) {
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

    public void setType(int type) {
        this.type = type;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setAttend(int attend) {
        this.attend = attend;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
