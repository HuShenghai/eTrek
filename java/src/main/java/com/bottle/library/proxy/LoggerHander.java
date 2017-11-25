package com.bottle.library.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by lenovo on 2017/11/1.
 */

public class LoggerHander implements InvocationHandler {

    private Object target;

    public LoggerHander(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("start log");
        Object result = method.invoke(target, args);
        System.out.println("end log");
        return result;
    }
}
