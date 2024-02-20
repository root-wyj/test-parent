package com.wyj.test.aop.log;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlsLogBean {
    private Object param;
    private Object result;
    private int beanLength;
    private long logTime;
    private long elapse;
    private String traceId;
    private String rpcId;
    private String functionName;
    private boolean isSuccess;
    private String OP;
    private String code;
    /**
     * 入参个数
     */
    private int paramSize;
    /**
     * 平均每个入参的返回值长度
     */
    private int retLength;
    /**
     * 返回结果size
     */
    private int retSize;
    /**
     * 压测标
     */
    private String useLog;
    private String uri;
    private String cpName;
    /**
     * 门店入驻类型
     */
    private String shopType;
    /**
     * 一级行业
     */
    private String firstIndustry;
    /**
     * 二级行业
     */
    private String secondIndustry;
    private String moduleType;
    private String brandCode;
    /**
     * 1=有效流量，0=无效流量
     */
    private Integer flowType;


    @Override
    public String toString() {
        return "{" +
            " \"beanLength\":" + beanLength +
            ", \"elapse\":" + elapse +
            ", \"functionName\":\"" + functionName + "\"" +
            ", \"logTime\":" + logTime +
            ", \"oP\":\"" + OP + "\"" +
            ", \"param\":" + "{}" +
            ", \"result\":" + "{}" +
            ", \"rpcId\":\"" + rpcId + "\"" +
            ", \"success\":" + isSuccess +
            ", \"traceId\":\"" + traceId + "\"" +
            ", \"code\":\"" + code + "\"" +
            ", \"paramSize\":\"" + paramSize + "\"" +
            ", \"retLength\":\"" + retLength + "\"" +
            ", \"retSize\":\"" + retSize + "\"" +
            ", \"useLog\":\"" + useLog + "\"" +
            ", \"uri\":\"" + uri + "\"" +
            '}';
    }
}
