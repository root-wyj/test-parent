package com.wyj.distribute.lock.redis;

import com.wyj.distribute.lock.DistributeLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created
 * Author: wyj
 * Date: 2019/11/28
 */
@Configuration
public class RedisDistributeLockConfiguration {

    /**
     * @description
     * @author yzMa
     * @date 2019/4/13 5:02 PM
     * @param
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "distribute.lock.default.type",havingValue = "redis",matchIfMissing = true)
    @ConditionalOnMissingBean
    public DistributeLock luaStarterRedisDistributeLock(StringRedisTemplate stringRedisTemplate){

        return new LuaRedisDistributeLock(stringRedisTemplate);
    }
}
