package com.wyj.starter.timecost;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created
 * Author: wyj
 * Date: 2019/11/28
 */
@Slf4j
@Aspect
public class TimeCostRecordAspect {

    /**
     * @description 切点
     * @author yzMa
     * @date 2019/10/22
     * @param
     * @return
     */
    @Pointcut("@annotation(com.wyj.starter.timecost.TimeCost)")
    public void timeCostPointcut(){}


    /**
     * @description 切面逻辑
     * @author yzMa
     * @date 2019/10/22
     * @param
     * @return
     */
    @Around("timeCostPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("Method Name : [" + methodName + "] ---> AOP around start");

        long startTimeMillis = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long execTimeMillis = System.currentTimeMillis() - startTimeMillis;

        log.info("Method Name : [" + methodName + "] ---> AOP around end exec time millis : " + execTimeMillis);
        return result;
    }
}
