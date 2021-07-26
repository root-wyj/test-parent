package com.wyj.test.log4j2.app.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wuyingjie
 * Created on 2020-01-20
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    public TraceInterceptor traceInterceptor() {
        return new TraceInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(traceInterceptor()).addPathPatterns("/**");
    }
}
