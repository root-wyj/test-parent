package com.wyj.shorturl;

import com.wyj.distribute.deviceid.DeviceIdAutoConfiguration;
import com.wyj.distribute.deviceid.strategy.IDeviceIdGenerator;
import com.wyj.shorturl.controller.ShortUrlController;
import com.wyj.shorturl.handler.ShortUrlHandlerMapping;
import com.wyj.shorturl.service.ShortUrlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletResponse;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/20
 */

@Configuration
@EnableConfigurationProperties(ShortUrlProperties.class)
@AutoConfigureAfter({WebMvcAutoConfiguration.class, RedisAutoConfiguration.class, DeviceIdAutoConfiguration.class})
@ConditionalOnBean({StringRedisTemplate.class, IDeviceIdGenerator.class})
@ConditionalOnClass({DispatcherServlet.class, HttpServletResponse.class})
public class ShortUrlAutoConfiguration {

    @Autowired
    ShortUrlProperties shortUrlProperties;

    @Bean
    public ShortUrlController shortUrlController(ShortUrlService shortUrlService) {
        return new ShortUrlController(shortUrlService);
    }

    @Bean
    public ShortUrlHandlerMapping shortUrlHandlerMapping() {
        ShortUrlHandlerMapping shortUrlHandlerMapping = new ShortUrlHandlerMapping();
        shortUrlHandlerMapping.setOrder(-1); // 比 RequestHandlerMapping 高一点
        return shortUrlHandlerMapping;
    }

    @Bean
    public ShortUrlService shortUrlService(StringRedisTemplate stringRedisTemplate, ShortUrlConfig shortUrlConfig, IDeviceIdGenerator deviceIdGenerator) {
        SimpleSnowId simpleSnowId = new SimpleSnowId(shortUrlConfig.getDeviceBit(), deviceIdGenerator.getDeviceId());
        return new ShortUrlService(stringRedisTemplate, simpleSnowId, shortUrlConfig);
    }

    @Bean
    public ShortUrlConfig shortUrlConfig() {
        ShortUrlConfig shortUrlConfig = new ShortUrlConfig();
        if (!StringUtils.isEmpty(shortUrlProperties.getHost())) {
            shortUrlConfig.setHost(shortUrlProperties.getHost());
        }
        return shortUrlConfig;
    }

}
