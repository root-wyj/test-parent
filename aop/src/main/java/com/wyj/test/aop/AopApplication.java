package com.wyj.test.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/10
 */

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AopApplication {

    public static Logger LOG = LoggerFactory.getLogger(AopApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class, args);
    }
}
