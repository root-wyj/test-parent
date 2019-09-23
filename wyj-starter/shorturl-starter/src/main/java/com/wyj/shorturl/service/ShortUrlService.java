package com.wyj.shorturl.service;

import com.wyj.shorturl.ShortUrlConfig;
import com.wyj.shorturl.SimpleSnowId;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/20
 */
public class ShortUrlService {

    public static final String SHORT_URL_PREFIX = "/s";

    private StringRedisTemplate stringRedisTemplate;
    private SimpleSnowId simpleSnowId;
    private ShortUrlConfig config;

    public ShortUrlService(StringRedisTemplate stringRedisTemplate, SimpleSnowId simpleSnowId, ShortUrlConfig config) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.simpleSnowId = simpleSnowId;
        this.config = config;
    }

    public String getShortUrl(String url) {
        String shortPath = simpleSnowId.nextUniqueStr();
        stringRedisTemplate.opsForValue().set(String.format(config.getRedisKey(), shortPath), url, config.getExpireSecond(), TimeUnit.SECONDS);
        return config.getHost()+SHORT_URL_PREFIX+"/"+shortPath;
    }

    public String getLongUrl(String shortPath) {
        return stringRedisTemplate.opsForValue().get(String.format(config.getRedisKey(), shortPath));
    }
}
