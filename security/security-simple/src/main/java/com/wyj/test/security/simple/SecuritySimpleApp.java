package com.wyj.test.security.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author wuyingjie <wuyingjie@kuaishou.com>
 * Created on 2020-02-10
 */

@SpringBootApplication(
        exclude = DataSourceAutoConfiguration.class
)
public class SecuritySimpleApp {
    public static void main(String[] args) {
        SpringApplication.run(SecuritySimpleApp.class);
    }
}
