package com.wyj.test.netty.time.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/16
 */
public class UnixTime {

    private long value;

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public UnixTime(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date((value - 2208988800L) * 1000L));
    }
}
