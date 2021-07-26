package com.wyj.test.micro.server.openfeign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import feign.Param;

/**
 * @author wuyingjie <13693653307@163.com>
 * Created on 2020-08-19
 */
@FeignClient(name = "REMOTE", url = "localhost:8082/hello")
public interface RemoteClient {

    @GetMapping("/pong")
    String pong(@RequestParam("name") String name);
}
