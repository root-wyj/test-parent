package com.wyj.distribute.lock.zookeeper;

import com.wyj.distribute.lock.DistributeLock;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yzMa
 * @description
 * @date 2019/4/15 9:41 AM
 */
@Configuration
public class ZookeeperDistributeLockConfiguration {

    /**
     * @description
     * @author yzMa
     * @date 2019/4/13 5:02 PM
     * @param
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "distribute.lock.default.type",havingValue = "zookeeper")
    @ConditionalOnBean(ZooKeeper.class)
    @ConditionalOnMissingBean
    public DistributeLock zookeeperStarterDistributeLock(){
        return new ZookeeperDistributeLock();
    }

}
