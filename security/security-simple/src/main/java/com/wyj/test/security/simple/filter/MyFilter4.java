package com.wyj.test.security.simple.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyingjie
 * Created on 2020-03-31
 */
@Slf4j
@Component
public class MyFilter4 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("myFiler4|init. filterConfig:{}", filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        log.info("myFilter4|request:{}", httpRequest.getRequestURI());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("myFilter4|destroy");
    }
}
