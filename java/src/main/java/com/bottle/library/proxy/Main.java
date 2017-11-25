package com.bottle.library.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by lenovo on 2017/11/1.
 * 动态代理
 */

public class Main {

    public static void main(String[] args){
        IHelloWorld hw = new HelloWorld();
        LoggerHander hander = new LoggerHander(hw);
        IHelloWorld proxy = (IHelloWorld) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                hw.getClass().getInterfaces(), hander);
        proxy.sayHello();
        proxy.sayGoodbye();
    }
}
