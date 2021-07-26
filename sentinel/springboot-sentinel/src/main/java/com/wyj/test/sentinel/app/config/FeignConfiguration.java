package com.wyj.test.sentinel.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import feign.Logger.Level;

/**
 * @author wuyingjie <13693653307@163.com>
 * Created on 2020-12-23
 */
@Configuration
public class FeignConfiguration {

    @Bean
    public Logger.Level level() {
        return Level.FULL;
    }
}
