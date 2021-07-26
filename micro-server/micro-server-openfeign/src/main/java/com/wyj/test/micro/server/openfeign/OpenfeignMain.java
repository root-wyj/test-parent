package com.wyj.test.micro.server.openfeign;

import feign.Feign;
import feign.Param;
import feign.Request.Options;
import feign.RequestLine;
import feign.Retryer.Default;
import feign.codec.StringDecoder;

/**
 * @author wuyingjie <13693653307@163.com>
 * Created on 2020-08-19
 */
public class OpenfeignMain {

    public static void main(String[] args) {
        HelloAction action = Feign.builder()
                .decoder(new StringDecoder())
                .options(new Options(1000, 3500))
                .retryer(new Default(5000, 5000, 3))
                .target(HelloAction.class, "http://localhost:8082");

        System.out.println(action.pong("zhangsan"));
    }

    public interface HelloAction {
        @RequestLine("GET /hello/pong?name={name}")
        String pong(@Param("name") String name);
    }
}
