package com.wyj.test.aop;

import com.wyj.test.aop.service.AopTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/10
 */

@RestController
@RequestMapping("/monitor")
public class MonitorController {

    @Resource
    AopTestService aopTestService;

    @GetMapping("/test/aop/{name}")
    public String testAop(@PathVariable("name") String name) {
        return aopTestService.testAop(name);
    }
}
