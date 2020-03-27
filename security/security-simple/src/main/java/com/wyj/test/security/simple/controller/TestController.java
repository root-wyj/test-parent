package com.wyj.test.security.simple.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyingjie <wuyingjie@kuaishou.com>
 * Created on 2020-02-10
 */

@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/simpleGet/{name}")
    public String simpleGet(@PathVariable("name") String name) {
        return "simpleGet:"+name;
    }

    @GetMapping("/noAuth/1")
    public String noAuth_1() {
        return "noAuth_1";
    }

    @GetMapping("/admin/1")
    public String admin_1() {
        return "admin_1";
    }

}
