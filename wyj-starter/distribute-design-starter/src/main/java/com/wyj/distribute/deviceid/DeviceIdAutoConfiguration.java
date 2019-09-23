package com.wyj.distribute.deviceid;

import com.wyj.distribute.deviceid.strategy.AbstractDeviceIdGenerator;
import com.wyj.distribute.deviceid.strategy.IDeviceIdGenerator;
import com.wyj.distribute.deviceid.strategy.RedisDeviceIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/19
 */
@Configuration
@EnableConfigurationProperties(DeviceIdProperties.class)
@AutoConfigureAfter(value = {RedisAutoConfiguration.class, DataSourceAutoConfiguration.class})
@Slf4j
public class DeviceIdAutoConfiguration {

    @Autowired
    DeviceIdProperties properties;

    @Autowired
    Environment applicationEnvironment;

    @Bean
    @ConditionalOnBean(StringRedisTemplate.class)
    @ConditionalOnMissingBean(IDeviceIdGenerator.class)
    public IDeviceIdGenerator redisDeviceIdGenerator(StringRedisTemplate stringRedisTemplate) {
        int bitLength = properties.getBitLength();
        String host = properties.getHost();

        if (bitLength <= 0) {
            bitLength = AbstractDeviceIdGenerator.DEFAULT_DEVICE_LENGTH;
        }

        if (StringUtils.isEmpty(host)) {
            host = "localhost";
            String port = applicationEnvironment.getProperty("server.port");
            if (!StringUtils.isEmpty(port)) {
                host = host + ":" + port;
            }
        }

        return new RedisDeviceIdGenerator(stringRedisTemplate, host, bitLength);
    }


}
