package com.wyj.test.aop.annoaop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/23
 */
@Aspect
@Component
public class AnnoAspectJ {

    @Pointcut("@annotation(com.wyj.test.aop.annoaop.AopAnno)")
    public void pointcut1() {}

    @Around("pointcut1()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        System.out.println("do before");
        Object proceed = point.proceed(point.getArgs());
        System.out.println("do after");
        return proceed;
    }
}
