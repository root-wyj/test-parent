package com.wyj.test.log4j2.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author wuyingjie <wuyingjie@kuaishou.com>
 * Created on 2020-01-21
 */
public class TestForLog {


    public void log() {
        Logger logger = LogManager.getLogger(TestForLog.class);
        logger.info("this is log");
    }
}
