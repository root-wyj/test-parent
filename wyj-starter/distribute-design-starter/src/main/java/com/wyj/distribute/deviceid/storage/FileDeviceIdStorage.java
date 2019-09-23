package com.wyj.distribute.deviceid.storage;

import java.util.Map;
import java.util.Properties;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/19
 */
public class FileDeviceIdStorage implements IDeviceIdStorage{

    private String deviceIdFilePath;
    private IDeviceIdStorage cachedDeviceIdStorage;

    public FileDeviceIdStorage(IDeviceIdStorage deviceIdStorage) {
        this.cachedDeviceIdStorage = deviceIdStorage;
    }

    @Override
    public void storeDeviceId() throws RuntimeException {
        System.getProperties().getProperty("user.home");
    }

    @Override
    public int readDeviceId() throws RuntimeException {
        return 0;
    }

    public static void main(String[] args) {
        Properties properties = System.getProperties();
        Map<String, String> env = System.getenv();
        System.out.println(properties);
    }
}
