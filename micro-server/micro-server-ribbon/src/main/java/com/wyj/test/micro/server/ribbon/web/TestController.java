package com.wyj.test.micro.server.ribbon.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyingjie
 * Created on 2020-05-28
 */

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

//    private static final String MONITOR_URL = "http://161.117.15.122:11111/monitor/index";
    private static final String MONITOR_URL = "http://users/monitor/index";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/restTemplate")
    public String restTemplate() {
        String result = restTemplate.getForObject(MONITOR_URL, String.class);
        log.info("result:{}", result);
        return "ok";
    }


}
