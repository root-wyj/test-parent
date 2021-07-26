package com.wyj.test.security.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;

/**
 * @author wuyingjie
 * Created on 2020-02-10
 */

@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class,
//                SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class
        }
)
public class SecuritySimpleApp {
    public static void main(String[] args) {
        SpringApplication.run(SecuritySimpleApp.class);
    }
}
