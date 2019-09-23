package com.wyj.distribute.deviceid.storage;

/**
 * deviceId 的存储方式
 * Author: wyj
 * Date: 2019/9/19
 */
public interface IDeviceIdStorage{

    void storeDeviceId() throws RuntimeException;

    int readDeviceId() throws RuntimeException;

}
