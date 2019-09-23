package com.wyj.proxy.cglib;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/20
 */
public class HelloService {

    public String sayHello(String name) {
        System.out.println("HelloService sayHello:"+name);
        return "hello, " + name;
    }
}
