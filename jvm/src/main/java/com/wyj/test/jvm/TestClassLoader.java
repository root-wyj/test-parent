package com.wyj.test.jvm;

import sun.reflect.Reflection;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/11
 */
public class TestClassLoader {
    public static void main(String[] args) {

        ClassLoader classLoader = Test1.class.getClassLoader();

        while (classLoader != null) {
            System.out.println(classLoader);
            classLoader = classLoader.getParent();
        }
        System.out.println(ClassLoader.getSystemClassLoader());
//        System.out.println(Reflection.getCallerClass());
        System.out.println(Thread.currentThread().getContextClassLoader());
    }
}
