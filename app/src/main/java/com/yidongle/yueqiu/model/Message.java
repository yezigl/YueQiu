package com.yidongle.yueqiu.model;

/**
 * Created on 15/6/23.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class Message {

    private String title;
    private String content;
    private String createTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
