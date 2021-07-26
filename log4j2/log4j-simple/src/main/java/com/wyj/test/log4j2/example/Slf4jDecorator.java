package com.wyj.test.log4j2.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wuyingjie
 * Created on 2020-01-21
 */
public class Slf4jDecorator {

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(Slf4jDecorator.class);
        log.trace("test1");
        log.debug("test1");
        log.info("test1");
        log.warn("test1");
        log.error("test1");
    }
}
