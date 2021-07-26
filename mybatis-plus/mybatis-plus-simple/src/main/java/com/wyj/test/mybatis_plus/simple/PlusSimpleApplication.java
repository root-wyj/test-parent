package com.wyj.test.mybatis_plus.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * @author wuyingjie
 * Created on 2020-02-19
 */
@SpringBootApplication
public class PlusSimpleApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlusSimpleApplication.class, args);
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
