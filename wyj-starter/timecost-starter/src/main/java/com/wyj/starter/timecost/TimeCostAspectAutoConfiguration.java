package com.wyj.starter.timecost;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created
 * Author: wyj
 * Date: 2019/11/28
 */
@Configuration
@AutoConfigureAfter(AopAutoConfiguration.class)
@ConditionalOnBean(AopAutoConfiguration.class)
@ConditionalOnProperty(prefix="aspect",name = "timecost.enable",havingValue = "true",matchIfMissing = true)
@Import({TimeCostRecordAspect.class})
public class TimeCostAspectAutoConfiguration {
}
