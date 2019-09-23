package com.wyj.test.aop.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import static com.wyj.test.aop.AopApplication.LOG;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/16
 */

//@Component
public class TestAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        LOG.info("my advice:{}", invocation.getMethod().toString());
        return invocation.proceed();
    }
}
