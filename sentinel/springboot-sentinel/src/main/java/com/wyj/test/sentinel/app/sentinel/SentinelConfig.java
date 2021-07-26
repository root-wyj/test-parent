package com.wyj.test.sentinel.app.sentinel;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.UrlCleaner;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * @author wuyingjie <13693653307@163.com>
 * Created on 2020-11-23
 */
@Configuration
public class SentinelConfig {


    /**
     * 灵感来源于，灵感出自 源码
     * [git-servlet 适配](https://github.com/alibaba/Sentinel/wiki/主流框架的适配#web-适配)
     * [知乎-sentinel限流、降级统一处理](https://zhuanlan.zhihu.com/p/150058613)
     */
    @Bean
    public UrlCleaner urlCleaner() {
        return new UrlCleaner() {

            private final Logger log = LoggerFactory.getLogger(UrlCleaner.class);

            @Override
            public String clean(String originUrl) {
                log.info("cleaner originUrl:{}", originUrl);
                if (originUrl == null || originUrl.isEmpty()) {
                    return originUrl;
                }

                // 不希望统计资源文件，可以转化为 empty str (since 1.6.3)
                if (!originUrl.startsWith("/api")) {
                    return "";
                }

                // 对于 /foo/{id} 的 都算作 /foo/*
                return formatPattern(originUrl);
            }

            private String formatPattern(String url) {

                int start = url.indexOf("{");
                int end = url.indexOf("}");
                if (start > 0 && end > 0) {
                    return formatPattern(url.substring(0, start) + "*" + url.substring(end + 1));
                } else {
                    return url;
                }
            }
        };
    }

    @Bean
    public BlockExceptionHandler blockExceptionHandler() {
        return new SentinelBlockHandler();
    }

    public void datasouce() {
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<List<FlowRule>>(
                "https://is-nacos.test.gifshow.com", "76d94ab2-abf6-4698-ac90-a39cac4ad1c3", "sentinel-wyj.json", source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }
}
