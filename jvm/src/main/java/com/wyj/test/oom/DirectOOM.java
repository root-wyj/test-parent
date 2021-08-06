package com.wyj.test.oom;

import java.nio.ByteBuffer;

/**
 * -XX:MaxDirectMemorySize=100m
 * @author wuyingjie <13693653307@163.com>
 * Created on 2021-07-28
 */
public class DirectOOM {

    public static void main(String[] args) {
        //直接分配128M的直接内存
        ByteBuffer bb = ByteBuffer.allocateDirect(128*1024*1204);
    }
}
