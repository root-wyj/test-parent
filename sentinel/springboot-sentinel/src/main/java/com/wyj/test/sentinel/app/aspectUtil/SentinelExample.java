package com.wyj.test.sentinel.app.aspectUtil;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wuyingjie
 * Date: 2024/2/20
 */
@Component
@Slf4j
public class SentinelExample {


    @Sentinel(resourceName = "testSentinel", suffixExpression = "#args[0].uid + #args[0].name")
    public void testSentinel(RequestParam param) {
        log.info("testSentinel:{}", JSON.toJSONString(param));
    }


    @Data
    public static class RequestParam {
        private String uid;
        private String name;
    }


}
