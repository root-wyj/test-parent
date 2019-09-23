package com.wyj.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

/**
 * 参考自： https://blog.csdn.net/yhl_jxy/article/details/80633194 将cglib 还是比较透彻的
 * Author: wyj
 * Date: 2019/9/20
 */
public class Main {


    public static void main(String[] args) {
        String cglibClassDir = System.getProperty("user.dir")+"/proxy/target/cglib";
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, cglibClassDir);
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(HelloService.class);
        enhancer.setCallback(new MyMethodInterceptor());

        HelloService proxy = (HelloService) enhancer.create();
        proxy.sayHello("proxy");
    }
}
