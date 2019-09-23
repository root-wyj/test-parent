package com.wyj.shorturl.handler;

import com.wyj.shorturl.controller.ShortUrlController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/19
 */
public class ShortUrlHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected boolean isHandler(Class<?> beanType) {
        return beanType.getName().equals(ShortUrlController.class.getName());
    }
}
