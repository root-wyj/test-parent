package com.wyj.test.security.simple.token;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author wuyingjie
 * Created on 2020-05-24
 */
@Component
public class SimpleUserService implements UserDetailsService {
    private static final Map<String, User> userMap = new HashMap<>();
    static {
        userMap.put("user1", new User("user1", "user1", AuthorityUtils.createAuthorityList("USER")));
        userMap.put("admin", new User("admin", "admin", AuthorityUtils.createAuthorityList("ADMIN")));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMap.get(username);
    }
}
