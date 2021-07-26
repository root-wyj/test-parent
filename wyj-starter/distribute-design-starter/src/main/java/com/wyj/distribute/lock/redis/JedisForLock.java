package com.wyj.distribute.lock.redis;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author wuyingjie
 * Created on 2020-01-20
 */
public class JedisForLock {

    private static final Long RELEASE_SUCCESS = 1L;
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private static JedisPool myJedisPool = null;

    @Autowired
    private JedisPool jedisPool;

    @PostConstruct
    public void init() {
        myJedisPool = this.jedisPool;
    }

    /**
     * 不设置过期时间则默认 5min
     * @param lockKey
     * @param clientId
     * @return
     */
    public static Boolean tryLock(String lockKey, String clientId) {
        return tryLock(lockKey, clientId,  5 * 60 * 1000);
    }

    /**
     * 尝试获取分布式锁
     * @param lockKey 锁
     * @param clientId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static Boolean tryLock(String lockKey, String clientId, long expireTime) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedisInstance();
            result = jedis.set(lockKey, clientId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) jedis.close();
        }

        if (LOCK_SUCCESS.equals(result)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 循环尝试获取分布式锁（默认循环5min）
     * @param lockKey
     * @param clientId
     * @return
     */
    public static Boolean loopTryLock(String lockKey, String clientId) throws InterruptedException {
        long time = 0;
        while (time < TimeUnit.MINUTES.toMillis(5)) {
            boolean hasLock = tryLock(lockKey, clientId);
            if (hasLock) {
                return true;
            }
            int sleep = new Random().nextInt(1000);
            time += TimeUnit.MILLISECONDS.toMillis(sleep);
            TimeUnit.MILLISECONDS.sleep(sleep);
        }
        return false;
    }

    /**
     * 释放分布式锁
     * @param lockKey 锁
     * @param clientId 请求标识
     * @return 是否释放成功
     */
    public static Boolean tryRelease(String lockKey, String clientId) {
        Jedis jedis = null;
        Object result = null;
        try {
            jedis = getJedisInstance();
            result = jedis.eval(RELEASE_LOCK_SCRIPT, Collections.singletonList(lockKey), Collections.singletonList(clientId));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) jedis.close();
        }

        if (RELEASE_SUCCESS.equals(result)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private static Jedis getJedisInstance() {
        return myJedisPool.getResource();
    }

    /**
     * 循环尝试获取分布式锁
     * @param lockKey
     * @param clientId
     * @return
     */
    public static Boolean loopTryLock(String lockKey, String clientId, long timeLoop) throws InterruptedException {
        long time = 0;
        while (time < timeLoop) {
            boolean hasLock = tryLock(lockKey, clientId);
            if (hasLock) {
                return true;
            }
            final int randomNumber = 500;
            int sleep = new Random().nextInt(randomNumber);
            time += TimeUnit.MILLISECONDS.toMillis(sleep);
            TimeUnit.MILLISECONDS.sleep(sleep);
        }
        return false;
    }
}
