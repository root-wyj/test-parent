package com.wyj.test.netty.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 消息model
 * Created
 * Author: wyj
 * Date: 2019/10/21
 */
@Data
@AllArgsConstructor
public class CustomMsg {

    /**
     * 类型 系统编号， 0xAB 表示A系统，0xBC 表示B系统等
     */
    private byte type;

    /**
     * 信息标志 0xAB 心跳包, 0xBC 超时包, 0xCD 业务信息包
     */
    private byte flag;

    /**
     * body 主体信息长度
     */
    private int lenght;

    /**
     * 主体内容
     */
    private String body;
}
