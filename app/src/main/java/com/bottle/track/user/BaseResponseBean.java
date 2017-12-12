package com.bottle.track.user;

/**
 * Created by Administrator on 2017/12/12 0012.
 */

public class BaseResponseBean<T> {

    private int rescode;
    private T result;
    private String info;

    public int getRescode() {
        return rescode;
    }

    public void setRescode() {
        this.rescode = rescode;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo() {
        this.info = info;
    }
}
