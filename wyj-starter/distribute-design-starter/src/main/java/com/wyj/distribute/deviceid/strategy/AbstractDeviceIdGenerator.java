package com.wyj.distribute.deviceid.strategy;

import com.wyj.distribute.deviceid.storage.IDeviceIdStorage;
import com.wyj.distribute.deviceid.strategy.IDeviceIdGenerator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextAware;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/19
 */
public abstract class AbstractDeviceIdGenerator implements IDeviceIdGenerator, InitializingBean {

    public static final int DEFAULT_DEVICE_LENGTH = 3;

    protected String host;
    protected int deviceLength;
    private int deviceId;

    public AbstractDeviceIdGenerator(String host, int deviceLength) {
        this.host = host;
        this.deviceLength = deviceLength;
    }

    @Override
    public void afterPropertiesSet() {
        deviceId = generateDeviceId();
    }

    /**
     * 产生deviceId
     */
    protected abstract int generateDeviceId();

    @Override
    public int getDeviceId() {
        return deviceId;
    }
}
