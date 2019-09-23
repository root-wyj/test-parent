package com.wyj.shorturl;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/20
 */
@ConfigurationProperties(prefix = ShortUrlProperties.PREFIX)
public class ShortUrlProperties {

    static final String PREFIX = "short-url";

    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
