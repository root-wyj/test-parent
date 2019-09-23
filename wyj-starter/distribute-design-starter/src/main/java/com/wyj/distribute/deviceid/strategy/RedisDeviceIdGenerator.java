package com.wyj.distribute.deviceid.strategy;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/19
 */
@Slf4j
public class RedisDeviceIdGenerator extends AbstractDeviceIdGenerator{

    private StringRedisTemplate redisTemplate;

    public RedisDeviceIdGenerator(StringRedisTemplate redisTemplate, String host, int deviceIdLength) {
        super(host, deviceIdLength);
        this.redisTemplate = redisTemplate;
    }


    private String getRedisHashKey() {
        return "deviceId:"+host;
    }

    //WYJ  应该做 分布式锁
    protected int generateDeviceId() {
        HashOperations<String, String, String> stringHashOperations = redisTemplate.opsForHash();
        List<String> values = stringHashOperations.values(getRedisHashKey());
        String hostIp;
        try {
            hostIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("获取本机ip失败，初始化 失败。e:{}", e);
            throw new RuntimeException("获取本机IP失败。"+e.getMessage());
        }

        String deviceIdStr = stringHashOperations.get(getRedisHashKey(), hostIp);
        final int deviceCount = 1 << deviceLength;
        if (StringUtils.isEmpty(deviceIdStr)) {
            log.info("本机第一次注册deviceId，ip:{}, 总共支持 {} 台机器，已注册{} 台机器", hostIp, deviceCount, values.size());

            if (values.size() >= deviceCount) {
                throw new RuntimeException("以达到最大可注册机器数");
            }

            boolean alreadyUse;
            // 因为 台数较少，就从1开始分配，因为如果 random的话 不知道 要多少次呢
            for (int i=0; i<(1<<deviceLength); i++) {
                alreadyUse = false;
                for (String item : values){
                    if (i == Integer.valueOf(item)) {
                        // 已经被注册过
                        alreadyUse = true;
                        break;
                    }
                }
                if (!alreadyUse) {
                    deviceIdStr = String.valueOf(i);
                    break;
                }
            }

            if (StringUtils.isEmpty(deviceIdStr)) {
                // 不可能运行到这儿
                throw new RuntimeException("分配deviceId失败");
            }
            stringHashOperations.put(getRedisHashKey(), hostIp, deviceIdStr);
            log.info("成功注册本机，ip:{}, deviceId:{}", hostIp, deviceIdStr);
        } else {
            log.info("本机之前注册过，ip:{}, deviceIdStr:{}", hostIp, deviceIdStr);
        }

        return Integer.valueOf(deviceIdStr);
    }
}
