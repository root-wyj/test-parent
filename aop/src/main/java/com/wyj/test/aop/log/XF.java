package com.wyj.test.aop.log;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 *
 * @author yukun.gyk
 * @date 2017/10/13
 */
public class XF {
    private static final Joiner LOG_JOIN = Joiner.on("|").useForNull(StringUtils.EMPTY);
    protected static final Logger log = LoggerFactory.getLogger("xfLogger");
    protected static final Logger logSize = LoggerFactory.getLogger("xfSizeLogger");
    private static final String SUCCESS = "Y";
    private static final String FAIL = "N";

    public static void stat(String logType, long elapse, boolean success, String code, String useLog, String op, int paramSize,
        int retSize, int retLength, String uri, SlsLogBean logBean) {
        info(LOG_JOIN.join(Lists.newArrayList(logType, elapse, success ? SUCCESS : FAIL, code, useLog, op, paramSize,
            retSize, retLength, uri, logBean.getCpName(), logBean.getShopType(), logBean.getFirstIndustry(),
            logBean.getSecondIndustry(), logBean.getModuleType(), logBean.getBrandCode(), logBean.getFlowType())));
    }

    public static void info(String msg, Object... params) {
        log.info(msg, params);
    }

    public static void infoSize(String msg, Object... params) {
        logSize.info(msg, params);
    }

    public static void resultSize(String logType, int size) {
        String uri = MDC.get("http_uri");
        infoSize("{}|{}|{}", logType, size, uri);
    }
    public static void stat(String logType, long elapse, boolean success) {
        info("{}|{}|{}", logType, elapse, success ? SUCCESS : FAIL);
    }

    public static void infoBiz(String logType, String bizType, long startMs, String result) {
        info("{}|{}|{}|{}", logType, System.currentTimeMillis() - startMs, bizType, result);
    }

    public static void infoBiz(String logType, String bizType, long startMs, boolean success) {
        info("{}|{}|{}|{}", logType, bizType, System.currentTimeMillis() - startMs, success ? SUCCESS : FAIL);
    }

    public static void infoBizError(String logType, String bizType, long startMs, String reason) {
        String r = StringUtils.left(reason, 100);
        info("{}|{}|{}|{}|{}", logType, System.currentTimeMillis() - startMs, FAIL, bizType, r);
    }

    public static void infoBizId(String logType, String bizType, String bizId, long startMs, String result) {
        info("{}|{}|{}|{}|{}", logType, System.currentTimeMillis() - startMs, result, bizType, bizId);
    }

    public static void infoBizId(String logType, String bizType, String bizId, long startMs, boolean success) {
        infoBizId(logType, bizType, bizId, startMs, success ? SUCCESS : FAIL);
    }

    public static void infoBizId(String logType, String bizType, String bizId, long startMs) {
        infoBizId(logType, bizType, bizId, startMs, true);
    }

}
