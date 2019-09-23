package com.wyj.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/20
 */
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("调用之前："+method);
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("调用之后："+method);
        return result;
    }
}
