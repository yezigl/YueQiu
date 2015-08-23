/**
 * Copyright 2014 yezi.gl. All Rights Reserved.
 */
package com.yueqiu.model;

import java.io.Serializable;

/**
 * description here
 *
 * @author yezi
 * @since 2014年11月7日
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String mobile;
    private String nickname;
    private String avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? "" : nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? "" : avatar;
    }

}
