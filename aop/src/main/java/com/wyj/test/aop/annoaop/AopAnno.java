package com.wyj.test.aop.annoaop;

import java.lang.annotation.*;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/23
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AopAnno {
    String value() default "";
}
