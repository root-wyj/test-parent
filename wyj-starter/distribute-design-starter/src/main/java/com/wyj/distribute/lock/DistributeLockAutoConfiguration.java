package com.wyj.distribute.lock;

import com.wyj.distribute.lock.redis.RedisDistributeLockConfiguration;
import com.wyj.distribute.lock.zookeeper.ZookeeperDistributeLockConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created
 * Author: wyj
 * Date: 2019/11/28
 */
@Configuration
@ConditionalOnProperty(value = "distribute.lock.enable",havingValue = "true",matchIfMissing = true)
@Import({ZookeeperDistributeLockConfiguration.class,
        RedisDistributeLockConfiguration.class})
@AutoConfigureAfter({RedisAutoConfiguration.class})//我们使用redisTemplate所以用的这个
public class DistributeLockAutoConfiguration {

    /**
     * @description
     * @author yzMa
     * @date 2019/4/13 5:54 PM
     * @param
     * @return
     */
    @Bean
    public LockTemplate<?> lockStarterTemplate(DistributeLock distributeLock){
        return new LockTemplate(distributeLock);
    }

}
