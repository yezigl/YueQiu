package com.yidongle.yueqiu.model;

/**
 * Created on 15/6/16.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class Representation<T> {

    private AppError error;
    private T data;
    private long timestamp;

    public AppError getError() {
        return error;
    }

    public void setError(AppError error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
