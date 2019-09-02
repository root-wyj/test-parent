package com.wyj.test.mybatis.plugin;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/1
 */

@Intercepts({
        @Signature(type = Executor.class, method="query",
                args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method="query",
                args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
@Component
@Slf4j
public class TestPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
//        log.info("plugin:{}", JSON.toJSONString(invocation));
        if (invocation.getTarget() instanceof  Executor) {
            MappedStatement ms = null;
            for (Object obj : invocation.getArgs()) {
                if (obj instanceof MappedStatement) {
                    ms = (MappedStatement) obj;
                    break;
                }
            }

            if (ms != null) {
                SqlSource sqlSource = ms.getSqlSource();

            }

        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
//        log.info("plugin:{}", JSON.toJSONString(target));
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
