package com.wyj.test.aop.log.imp;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service("XFExtObjSize")
public class XFExtObjSize implements XFExt {

    @Override
    public int toLength(Object object) {

        String value = JSON.toJSONString(object);
        return StringUtils.isBlank(value) ? 0 : value.length();
    }

}
