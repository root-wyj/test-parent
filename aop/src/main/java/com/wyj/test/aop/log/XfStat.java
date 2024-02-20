package com.wyj.test.aop.log;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XfStat {
    String name() default "";
    String xfRetLength() default "";
    String paramType() default "";
    String returnSizeType() default "";
    boolean printSlsLog() default false;
    boolean printParamOrRes() default false;
    int timeout() default 50;
    LogsOPEnum logsOp() default LogsOPEnum.UNKNOWN;


}
