package com.wyj.test.sentinel.app.sentinel;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyingjie <13693653307@163.com>
 * Created on 2020-11-23
 */
@Slf4j
public class SentinelBlockHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException ex)
            throws Exception {
        String msg = "";
        if (ex instanceof FlowException) {
            msg = "限流了";
        } else if (ex instanceof DegradeException) {
            msg = "降级了";
        } else if (ex instanceof ParamFlowException) {
            msg = "热点限流";
        } else if (ex instanceof SystemBlockException) {
            msg = "系统限流(负载、CPU、内存)";
        } else if (ex instanceof AuthorityException){
            msg = "Sentinel授权不通过";
        }

        log.error("SentinelBlockHandler|msg:{}, path:{}", msg, request.getRequestURI());
        response.setHeader("content-type", "text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println(msg);
        out.flush();
        out.close();
    }
}
