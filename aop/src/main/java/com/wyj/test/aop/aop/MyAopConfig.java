package com.wyj.test.aop.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/18
 */

@Configuration
public class MyAopConfig {

    @Bean
    public NameMatchMethodPointcutAdvisor myNameAdvisor(TestAdvice advice) {
        NameMatchMethodPointcutAdvisor nameAdvisor = new NameMatchMethodPointcutAdvisor(advice);
        nameAdvisor.setMappedNames("test*");
        return nameAdvisor;
    }

    @Bean
    public TestAdvice testAdvice() {
        return new TestAdvice();
    }
}
