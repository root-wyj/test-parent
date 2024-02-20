package com.wyj.test.aop.log;

import com.alibaba.fastjson.JSON;
import com.wyj.test.aop.log.imp.IXFParam;
import com.wyj.test.aop.log.imp.IXFReturn;
import com.wyj.test.aop.log.imp.XFExt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class XflushAspect {

    private static Logger SLS_LOG = LoggerFactory.getLogger("slsLogger");

    @Autowired
    private ApplicationContext applicationContext;

    @Pointcut("@annotation(com.wyj.test.aop.log.XfStat)")
    public void xfStat() {}

    /**
     * 这种方式的log 是在运行的最后打印的
     * 有一些代码 可能会在方法中 修改入参
     */
    @Around("xfStat()")
    public Object stat(ProceedingJoinPoint joinPoint) throws Throwable {
        /**
         * 记录日志
         */
        final long start = System.currentTimeMillis();
        //获取当前执行号,用于代码跟踪
        //int line = joinPoint.getSourceLocation().getLine();
        final MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        final Method method = signature.getMethod();
        final String clzName = signature.getDeclaringType().getCanonicalName();
        final XfStat xfStat = method.getAnnotation(XfStat.class);
        String xfRetLength = xfStat == null ? null : xfStat.xfRetLength();
        String name = xfStat == null ? null : xfStat.name();
        String paramType = xfStat == null ? null : xfStat.paramType();
        String returnSizeType = xfStat == null ? null : xfStat.returnSizeType();
        if (StringUtils.isBlank(name)) {
            name = clzName + "." + method.getName();
        }else {
            name = SpringELParserUtils.DEFAULT_PARSER.getElValue(name, joinPoint.getTarget(), joinPoint.getArgs(), null, false, String.class);
        }

        boolean success = true;
        Object returnValue = null;
        try {
            returnValue = joinPoint.proceed();
            success = true;
            return returnValue;
        } catch (Throwable e) {
            returnValue = e.getMessage();
            success = false;
            throw e;
        } finally {
            final long elapse = System.currentTimeMillis() - start;

            // 在HandlerInterceptor中 put的
            // MDC.put("http_uri", StringUtils.replace(request.getRequestURI(), "/", "."));
            String uri = MDC.get("http_uri");

            String code = StringUtils.EMPTY;
            int retSize;
//            if (returnValue instanceof MallResponse) {
//                MallResponse response = (MallResponse)returnValue;
//                code = String.valueOf(response.getCode());
//                retSize = calculateSize(response.getData());
//            } else {
                retSize = calculateSize(returnValue);
//            }
            retSize = StringUtils.isNotBlank(returnSizeType) ? applicationContext.getBean(returnSizeType, IXFReturn.class).toReturnSize(returnValue) : retSize;
            int retTotalLength = StringUtils.isNotBlank(xfRetLength) ? applicationContext.getBean(xfRetLength, XFExt.class).toLength(returnValue) : 0;
            String op = xfStat != null ? xfStat.logsOp().getValue() : StringUtils.EMPTY;
            int paramSize = StringUtils.isNotBlank(paramType) ? applicationContext.getBean(paramType, IXFParam.class).toParamSize(joinPoint.getArgs()) : 1;
            int retLength = retSize == 0 ? 0 : retTotalLength / retSize;
            // 从ThreadLocal 中获取 业务请求参数
            // 同样的也是在 HandlerInterceptor 初始化 ThreadLocal 再after 再删除，具体的各个字段的值 有可能散落在业务处理过程中
//            SlsLogBean globalLogBean = globalLogService.calculateLog();
            SlsLogBean globalLogBean = new SlsLogBean();
            if (xfStat != null && xfStat.printSlsLog()) {
                SlsLogBean bean = new SlsLogBean();
                // 设置 traceId
//                bean.setTraceId(EagleEye.getTraceId());
//                bean.setRpcId(EagleEye.getRpcId());
                bean.setLogTime(System.currentTimeMillis());
                bean.setFunctionName(name);
                bean.setParam(getParam(joinPoint));
                bean.setResult(returnValue);
                bean.setSuccess(success);
                bean.setElapse(elapse);
                bean.setCode(code);
                bean.setParamSize(paramSize);
                bean.setRetLength(retLength);
                bean.setRetSize(retSize);
                bean.setUri(uri);
                bean.setOP(xfStat.logsOp().getValue());
                bean.setCpName(globalLogBean.getCpName());
                bean.setShopType(globalLogBean.getShopType());
                bean.setFirstIndustry(globalLogBean.getFirstIndustry());
                bean.setSecondIndustry(globalLogBean.getSecondIndustry());
                bean.setModuleType(globalLogBean.getModuleType());
                bean.setBrandCode(globalLogBean.getBrandCode());
                bean.setFlowType(globalLogBean.getFlowType());
                String log = JSON.toJSONString(bean);
                bean.setBeanLength(log != null ? log.length() : 0);
                // 打印slslog 用来看 每个方法调用的 入参，返回等信息
                SLS_LOG.info(log);
            }
            // 打印xf 日志，xf 日志用来做监控
            XF.stat(name, elapse, success, code, "", op, paramSize, retSize, retLength, uri, globalLogBean);
        }
    }

    private int calculateSize(Object data) {
        if (data instanceof Collection || data instanceof Object[] || data instanceof Map) {
            return 1;
            // 实际应该返回 size
//            return org.apache.commons.collections4.CollectionUtils.size(data);
        } else {
            return data == null ? 0 : 1;
        }
    }

    /**
     * 获取参数名和参数值
     * @param proceedingJoinPoint
     * @return
     */
    private Object getParam(ProceedingJoinPoint proceedingJoinPoint) {
        if(proceedingJoinPoint == null){
            return null;
        }
        Object[] values = proceedingJoinPoint.getArgs();
        Map<String, Object> map = new HashMap<>(values != null ? values.length : 0);
        String[] names = ((CodeSignature) proceedingJoinPoint.getSignature()).getParameterNames();
        for (int i = 0; names != null && i <  names.length; i++) {
            map.put(names[i], values[i]);
        }
        return map;
    }

}
