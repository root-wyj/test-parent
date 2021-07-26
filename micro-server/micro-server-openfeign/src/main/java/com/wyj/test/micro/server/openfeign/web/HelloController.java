package com.wyj.test.micro.server.openfeign.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wyj.test.micro.server.openfeign.client.RemoteClient;

/**
 * @author wuyingjie <13693653307@163.com>
 * Created on 2020-08-19
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private RemoteClient remoteClient;

    @GetMapping("/ping")
    public String ping(@RequestParam("name") String name) {
        return remoteClient.pong(name);
    }

    @GetMapping("/pong")
    public String pong(@RequestParam("name") String name) {
        return "pong:"+name;
    }
}
