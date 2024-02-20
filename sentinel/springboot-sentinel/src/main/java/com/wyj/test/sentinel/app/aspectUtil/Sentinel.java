package com.wyj.test.sentinel.app.aspectUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jonny_wang
 * @date 2021/08/26 19:21
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sentinel {
    String resourceName() default "";
    String suffixExpression() default "";
}
