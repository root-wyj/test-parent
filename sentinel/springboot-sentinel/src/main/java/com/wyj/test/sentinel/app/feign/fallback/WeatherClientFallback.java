package com.wyj.test.sentinel.app.feign.fallback;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.node.Node;
import com.wyj.test.sentinel.app.feign.WeatherClient;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyingjie <13693653307@163.com>
 * Created on 2020-12-02
 */
@Slf4j
@Component
public class WeatherClientFallback implements FallbackFactory<WeatherClient> {
    @Override
    public WeatherClient create(Throwable cause) {
        return new WeatherClient() {
            @Override
            public Map<String, Object> weather(String cityCode) {
                log.error("WeatherClient fallback. cityCode:{}", cityCode);
                Map<String, Object> map = new HashMap<>();
                map.put("fallback", "error:" + cityCode);
                Node curNode = ContextUtil.getContext().getCurEntry().getCurNode();
                log.error("WeatherClient fallback. node: eQps:{}, eTotal:{}", curNode.exceptionQps(), curNode.totalException());
                return map;
            }
        };
    }
}
