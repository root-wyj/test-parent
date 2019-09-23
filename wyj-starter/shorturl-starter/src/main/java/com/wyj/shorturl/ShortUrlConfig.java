package com.wyj.shorturl;

import lombok.Data;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/20
 */

@Data
public class ShortUrlConfig {

    private String redisKey = "shorturl:%s";
    private long expireSecond = 60L*60*24*30*12;  //1年

    private int deviceBit = SimpleSnowId.DEFAULT_DEVICE_BIT;
    private String host;


}
