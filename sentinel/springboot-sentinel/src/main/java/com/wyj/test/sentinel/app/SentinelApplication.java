package com.wyj.test.sentinel.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wuyingjie <13693653307@163.com>
 * Created on 2020-11-23
 */
@SpringBootApplication
@EnableFeignClients
public class SentinelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelApplication.class, args);
    }
}
