package com.wyj.test.aop.log;

import lombok.Getter;

@Getter
public enum LogsOPEnum {
    /**
     * 日志操作枚举
     */
    UNKNOWN("unknown", "未知"),
    DB("op_db", "db操作"),
    SERVICE("request_service", "服务请求"),
    UP_HTTP("up_http", "给上游请求的http"),
    DOWN_HTTP("down_http", "请求下游的http"),
    HSF("hsf", "hsf"),
    REDIS("redis", "tair rdb"),

    ;

    private String value;
    private String desc;

    LogsOPEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getValue() {
        return value;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValidName(String name) {
        for (LogsOPEnum enumName : LogsOPEnum.values()) {
            if (enumName.getValue().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
