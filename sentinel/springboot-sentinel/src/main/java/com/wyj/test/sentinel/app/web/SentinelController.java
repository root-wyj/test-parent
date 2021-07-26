package com.wyj.test.sentinel.app.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wyj.test.sentinel.app.feign.WeatherClient;
import com.wyj.test.sentinel.app.service.FakeService;

/**
 * @author wuyingjie <13693653307@163.com>
 * Created on 2020-11-23
 */
@RestController
@RequestMapping("/api/sentinel")
public class SentinelController {

    @Autowired
    private WeatherClient weatherClient;
    @Autowired
    private FakeService fakeService;

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name) {
        return String.format("hello %s!", name);
    }


    @GetMapping("/weather")
    public Map<String, Object> weather(@RequestParam(name = "cityCode", defaultValue = "101010100", required = false) String cityCode) {
        return weatherClient.weather(cityCode);
    }

    @GetMapping("/degrade/exception/{point}")
    public String degrade(@PathVariable("point") int point) {
        return fakeService.degradeWithException(point);
    }



}
