package com.wyj.test.aop.config.request;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/19
 */
@Configuration
public class ShortUrlConfig {

//    @Bean
//    public SimpleUrlHandlerMapping shortUrlHandlerMapping() {
//        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
//        Map<String, Object> urlMap = new HashMap<>();
//        urlMap.put("/s/**", shortUrlController());
//        handlerMapping.setUrlMap(urlMap);
//        return handlerMapping;
//    }

    @Bean
    public ShortUrlHandlerMapping shortUrlHandlerMapping() {
        ShortUrlHandlerMapping shortUrlHandlerMapping = new ShortUrlHandlerMapping();
        shortUrlHandlerMapping.setOrder(-1); // 比 RequestHandlerMapping 高一点
        return shortUrlHandlerMapping;
    }

    @Bean
    public ShortUrlController shortUrlController() {
        return new ShortUrlController();
    }

}
