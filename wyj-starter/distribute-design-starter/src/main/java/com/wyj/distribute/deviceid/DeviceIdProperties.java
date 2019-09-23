package com.wyj.distribute.deviceid;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/19
 */

@ConfigurationProperties(prefix = DeviceIdProperties.DEVICEID_PROPERTY_PREFIX)
public class DeviceIdProperties {

    public static final String DEVICEID_PROPERTY_PREFIX = "distribute.deviceid";

    private int bitLength;

    private String host;

    public int getBitLength() {
        return bitLength;
    }

    public void setBitLength(int bitLength) {
        this.bitLength = bitLength;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
