package com.wyj.test.security.simple.token;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyingjie
 * Created on 2020-05-24
 */
@Slf4j
public class TokenFilter implements Filter {
    private static final String TOKEN = "_m_token";

    private UserDetailsService userDetailsService;

    public TokenFilter(UserDetailsService userDetailsService) {
        this. userDetailsService = userDetailsService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("TokenFilter|init. filterConfig:{}", filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getParameter(TOKEN);
        if (!StringUtils.isEmpty(token)) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(token);
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword());
//            auth.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("TokenFilter|destroy.");
    }
}
