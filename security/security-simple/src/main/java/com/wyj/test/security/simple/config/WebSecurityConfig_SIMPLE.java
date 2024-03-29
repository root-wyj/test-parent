package com.wyj.test.security.simple.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.wyj.test.security.simple.filter.MyFilter3;

/**
 * @author wuyingjie
 * Created on 2020-02-10
 */

@Configuration
@EnableWebSecurity
@Profile("simple")
public class WebSecurityConfig_SIMPLE extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests().antMatchers("/test/noAuth/**").permitAll()
//                .and().authorizeRequests().antMatchers("*/**").authenticated()
                .and().authorizeRequests().antMatchers("/test/admin/**").hasRole("admin")
                .and().authorizeRequests().anyRequest().authenticated()
                .and().formLogin()
//                .and().httpBasic()
                .and().addFilterBefore(new MyFilter3(), UsernamePasswordAuthenticationFilter.class);

        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser("user1").password("user1").roles("")
                .and().withUser("admin").password("admin").roles("admin");

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("").
        super.configure(web);
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }
}
