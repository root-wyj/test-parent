package com.wyj.starter.timecost;

import java.lang.annotation.*;

/**
 * Created
 * Author: wyj
 * Date: 2019/11/28
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TimeCost {
}
