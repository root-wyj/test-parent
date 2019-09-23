package com.wyj.test.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 具体一些使用可以看git：https://github.com/admin801122/springboot2-spring5-studying
 * Created
 * Author: wyj
 * Date: 2019/9/10
 */

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableAspectJAutoProxy(proxyTargetClass = false, exposeProxy = true)
public class AopApplication {

    public static Logger LOG = LoggerFactory.getLogger(AopApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class, args);
    }

}