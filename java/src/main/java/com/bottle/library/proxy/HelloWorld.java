package com.bottle.library.proxy;

/**
 * Created by lenovo on 2017/11/1.
 */

public class HelloWorld implements IHelloWorld{

    @Override
    public void sayHello() {
        System.out.println("Hello World!");
    }

    @Override
    public void sayGoodbye() {
        System.out.println("Goodbye!");
    }
}
