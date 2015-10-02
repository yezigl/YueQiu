/**
 * Copyright 2015 yezi.gl. All Rights Reserved.
 */
package com.yidongle.yueqiu.model;

import java.io.Serializable;
import java.util.List;

/**
 * description here
 *
 * @author yezi
 * @since 2015年6月14日
 */
public class Stadium implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String thumbnail;
    private float longitude;
    private float latitude;
    private int size;
    private List<String> gallery;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }
}
