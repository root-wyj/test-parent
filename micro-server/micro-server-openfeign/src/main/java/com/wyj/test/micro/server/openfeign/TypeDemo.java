package com.wyj.test.micro.server.openfeign;

import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wuyingjie <13693653307@163.com>
 * Created on 2020-08-21
 */
public class TypeDemo {

    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException {
        ParameterizedTypeTest<TypeDemo> demo1 = new ParameterizedTypeTest<>();
        Class<? extends ParameterizedTypeTest> demo1Class = demo1.getClass();
        Field list = ParameterizedTypeTest.class.getDeclaredField("list");
        Type fieldListType = list.getGenericType();
        Method fun1 = ParameterizedTypeTest.class.getDeclaredMethod("fun1", Class.class);
        Type[] funParamTypes = fun1.getGenericParameterTypes();
        Type funReturnType = fun1.getGenericReturnType();
        GenericDeclaration genericDeclaration = ((TypeVariable) funReturnType).getGenericDeclaration();


    }

    public static class ParameterizedTypeTest<T>{
        private List<T> list = null;
        private Set<T> set = null;
        private Map<String ,T> map = null;
        private Map.Entry<String,Integer>  map2;

        public T fun1(Class<? extends T> clz) throws IllegalAccessException, InstantiationException {
            System.out.println("fun1");
            return clz.newInstance();
        }
    }
}
