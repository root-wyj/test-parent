package com.wyj.test.sentinel.app.aspectUtil;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class SentinelAspect {

    @Pointcut("@annotation(com.wyj.test.sentinel.app.aspectUtil.Sentinel)")
    public void sentinel() {}

    @Around("sentinel()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        final MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        final Method method = signature.getMethod();
        final Sentinel sentinel = method.getAnnotation(Sentinel.class);
        String resourceName = sentinel.resourceName();
        String suffixExpression = sentinel.suffixExpression();
        if (StringUtils.isNotBlank(suffixExpression)) {
            resourceName = evalExpressionValue(resourceName, joinPoint.getArgs(), suffixExpression);
        }
        if (StringUtils.isNotBlank(resourceName)) {
            Entry entry = null;
            try {
                entry = SphU.entry(resourceName);
            } catch (BlockException e) {
                log.error("触发限流 resourceName={}", resourceName);
                //TODO 返回统一限流 CODE and MSG or throw BizException
//                return MallResponseBuilder.overLimit(null);
                return null;
            } finally {
                if(entry!=null){
                    entry.exit();
                }
            }
        }
        return joinPoint.proceed();
    }

    //这个是 普通的解析
    private static String evalExpressionValue2(String prefix, Object[] args, String expression) {
        if (ArrayUtils.isEmpty(args)) {
            return null;
        }

        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("args", args);
        ExpressionParser parser = new SpelExpressionParser();
        String[] expressions = expression.split("\\+");
        StringBuilder sb = new StringBuilder(20);
        if (StringUtils.isNotBlank(prefix)) {
            sb.append(prefix);
        }
        for(String expressionName : expressions){
            Expression exp = parser.parseExpression(expressionName);
            Object value = exp.getValue(context);
            if (value != null) {
                sb.append("-").append(value);
            }
        }

        return sb.toString();
    }

    // 这个是增加了 缓存的 解析
    private String evalExpressionValue (String prefix, Object[] args, String expression) throws Exception {
        if (ArrayUtils.isEmpty(args)) {
            return null;
        }

        String[] expressions = expression.split("\\+");
        StringBuilder sb = new StringBuilder(20);
        if (StringUtils.isNotBlank(prefix)) {
            sb.append(prefix);
        }
        for(String expressionName : expressions){
            Object value = SpringELParserUtils.DEFAULT_PARSER.getElValue(expressionName, null, args, null, false, Object.class);
            sb.append("-").append(value == null ? "" : value);
        }

        return sb.toString();
    }

}
