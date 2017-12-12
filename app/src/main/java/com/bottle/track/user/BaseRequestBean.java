package com.bottle.track.user;

import com.bottle.track.api.BaseInfoBean;

/**
 * Created by Administrator on 2017/12/12 0012.
 */

public class BaseRequestBean<T> {
    private BaseInfoBean baseInfoBean;
    private T requestBody;


     BaseRequestBean(T requestBody){
        this.requestBody = requestBody;
        this.baseInfoBean = new BaseInfoBean();
    }
    public BaseInfoBean getBaseInfoBean() {
        return baseInfoBean;
    }

    public void setBaseInfoBean(BaseInfoBean baseInfoBean) {
        this.baseInfoBean = baseInfoBean;
    }

    public T getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(T requestBody) {
        this.requestBody = requestBody;
    }
}
