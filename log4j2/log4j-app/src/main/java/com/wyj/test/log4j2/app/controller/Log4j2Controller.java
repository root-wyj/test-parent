package com.wyj.test.log4j2.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyingjie <wuyingjie@kuaishou.com>
 * Created on 2020-01-20
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class Log4j2Controller {



    @GetMapping("/test1")
    public String test1() {
        log.trace("test1");
        log.debug("test1");
        log.info("test1");
        log.warn("test1");
        log.error("test1");
        return "test1";
    }
}
