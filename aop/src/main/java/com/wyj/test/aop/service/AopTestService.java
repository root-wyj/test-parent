package com.wyj.test.aop.service;

import org.springframework.stereotype.Service;
import static com.wyj.test.aop.AopApplication.LOG;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/10
 */

@Service
public class AopTestService {


    public String testAop(String name) {
        LOG.info("testAop:{}", name);
        return "testAop " + name;
    }
}
