package com.wyj.test.security.simple.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wyj.test.security.simple.filter.MyFilter;
import com.wyj.test.security.simple.filter.MyFilter2;

/**
 * @author wuyingjie
 * Created on 2020-03-31
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MyFilter> myFilter() {
        FilterRegistrationBean<MyFilter> myFilterBean = new FilterRegistrationBean<>();
        myFilterBean.setFilter(new MyFilter());
        myFilterBean.setOrder(100);
        return myFilterBean;
    }

    @Bean
    public FilterRegistrationBean<MyFilter2> myFilter2() {
        FilterRegistrationBean<MyFilter2> myFilter2Bean = new FilterRegistrationBean<>();
        myFilter2Bean.setFilter(new MyFilter2());
        myFilter2Bean.setOrder(100);
        return myFilter2Bean;
    }
}
