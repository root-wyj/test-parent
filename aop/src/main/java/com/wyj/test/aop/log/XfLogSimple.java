package com.wyj.test.aop.log;

import org.springframework.stereotype.Component;

/**
 * @author wuyingjie
 * Date: 2024/2/20
 */

@Component
public class XfLogSimple {

    @XfStat(printSlsLog = true, logsOp = LogsOPEnum.UP_HTTP, xfRetLength = "XFExtObjSize")
    public void simple() {

    }
}
