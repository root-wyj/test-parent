package com.wyj.distribute.lock.zookeeper;

import com.wyj.distribute.lock.DistributeLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yzMa
 * @description
 * @date 2019/4/13 4:59 PM
 */
@Slf4j
public class ZookeeperDistributeLock implements DistributeLock {

    @Autowired
    private ZooKeeper zooKeeper;

    @Override
    public void lock(String key, String uniqueId, Long expireTime) {

    }

    @Override
    public boolean tryLock(String key, String uniqueId, Long expireTime) {
        return false;
    }

    @Override
    public void unlock(String key, String uniqueId) {

    }
}
