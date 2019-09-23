package com.wyj.test.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * Created
 * Author: wyj
 * Date: 2019/8/30
 */

@SpringBootApplication
public class MybatisApplication {

    @Autowired
    StringRedisTemplate redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }
}
