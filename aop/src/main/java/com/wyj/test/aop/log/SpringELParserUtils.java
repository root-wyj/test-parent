package com.wyj.test.aop.log;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuyingjie
 * Date: 2024/2/20
 */
public class SpringELParserUtils {

    public static final SpringELParserUtils DEFAULT_PARSER = new SpringELParserUtils();

    private final ExpressionParser parser = new SpelExpressionParser();

    private final ConcurrentHashMap<String, Expression> expCache = new ConcurrentHashMap<String, Expression>();

    private final ConcurrentHashMap<String, Method> funcs = new ConcurrentHashMap<String, Method>(8);

    public <T> T getElValue(String keySpEL, Object target, Object[] arguments, Object retVal, boolean hasRetVal,
                            Class<T> valueType) throws Exception {
        if (valueType.equals(String.class)) {
            // 如果不是表达式，直接返回字符串
            if (keySpEL.indexOf("#") == -1 && keySpEL.indexOf("'") == -1) {
                return (T) keySpEL;
            }
        }
        StandardEvaluationContext context = new StandardEvaluationContext();

        Iterator<Map.Entry<String, Method>> it = funcs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Method> entry = it.next();
            context.registerFunction(entry.getKey(), entry.getValue());
        }
        context.setVariable("target", target);
        context.setVariable("args", arguments);
        if (hasRetVal) {
            context.setVariable("retVal", retVal);
        }
        Expression expression = expCache.get(keySpEL);
        if (null == expression) {
            expression = parser.parseExpression(keySpEL);
            expCache.put(keySpEL, expression);
        }
        return expression.getValue(context, valueType);
    }


}
