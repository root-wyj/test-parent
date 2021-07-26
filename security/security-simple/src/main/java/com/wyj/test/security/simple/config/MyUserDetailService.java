package com.wyj.test.security.simple.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

/**
 * @author wuyingjie
 * Created on 2020-02-10
 */
public class MyUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException(username + "not found");
        }

        if ("user1".equals(username)) {
            return User.withUsername("user1")
                    .password(new BCryptPasswordEncoder().encode("user1"))
                    .roles("").build();
        }

        if ("admin".equals(username)) {
            return User.withUsername("admin")
                    .password(new BCryptPasswordEncoder().encode("admin"))
                    .roles("admin").build();
        }


        throw new UsernameNotFoundException(username + "not found");
    }
}
