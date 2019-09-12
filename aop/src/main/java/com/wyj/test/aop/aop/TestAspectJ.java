package com.wyj.test.aop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import static com.wyj.test.aop.AopApplication.LOG;


/**
 * Created
 * Author: wyj
 * Date: 2019/9/10
 */

@Component
@Aspect
public class TestAspectJ {

    @Before("execution(* com.wyj.test.aop.service.*.*(..))")
    public void doBefore(JoinPoint joinPoint) {
        LOG.info("aop before");
    }

}
