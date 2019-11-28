package com.wyj.distribute.lock.redis;

import com.wyj.distribute.lock.DistributeLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Created
 * Author: wyj
 * Date: 2019/11/28
 */
@Slf4j
public class LuaRedisDistributeLock implements DistributeLock {

    private String unlockLua = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private StringRedisTemplate stringRedisTemplate;

    public LuaRedisDistributeLock(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    class ExpirationSub extends Expiration {
        public ExpirationSub(long expirationTime, TimeUnit timeUnit){
            super(expirationTime,timeUnit);
        }
    }


    @Override
    public void lock(String key, String uniqueId, Long expireTime) {
        throw new RuntimeException("lua redis not support for now");
    }


    @Override
    public boolean tryLock(final String key, final String uniqueId, final Long expireSeconds) {
        Assert.notNull(key,"redis key 不能为空");
        Assert.notNull(uniqueId,"uniqueId 不能为空");
        Assert.notNull(expireSeconds,"expireTime 不能为空");
        Assert.isTrue(expireSeconds > 0 && expireSeconds <= 10 ,"锁的过期时间范围介于(0,10]秒");

        Boolean lockedSuccess = stringRedisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.set(key.getBytes(), uniqueId.getBytes(), new ExpirationSub(expireSeconds,TimeUnit.SECONDS), RedisStringCommands.SetOption.SET_IF_ABSENT);//老版本;//返回第一个result 别返回了第二个 --add by myz
            }
        });

        if(lockedSuccess){
            return true;
        }
        return false;
    }


    @Override
    public void unlock(String key, String uniqueId) {

        //使用Lua脚本删除Redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
        //spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
        RedisScript<Long> redisScript =
                new DefaultRedisScript<>(unlockLua, Long.class);

        stringRedisTemplate.execute(
                redisScript, Collections.singletonList(key), uniqueId);


    }
}
