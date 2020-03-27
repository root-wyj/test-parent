package com.wyj.test.log4j2.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author wuyingjie <wuyingjie@kuaishou.com>
 * Created on 2020-01-21
 */
public class Log4j2Only {

    public static void main(String[] args) {
        Logger log = LogManager.getLogger(Log4j2Only.class);
//        log.trace("test1");
//        log.debug("test1");
        log.info("test1");
//        log.warn("test1");
        log.error("test1");

        new TestForLog().log();
    }

}
