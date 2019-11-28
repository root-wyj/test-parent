package com.wyj.distribute.lock;

/**
 * Created
 * Author: wyj
 * Date: 2019/11/28
 */
public interface DistributeLock {


    void lock(String key, String uniqueId, Long expireTime);

    boolean tryLock(String key, String uniqueId, Long expireTime);

    void unlock(String key, String uniqueId);
}
