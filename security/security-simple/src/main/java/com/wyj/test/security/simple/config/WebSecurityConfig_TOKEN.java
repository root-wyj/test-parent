package com.wyj.test.security.simple.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.wyj.test.security.simple.filter.MyFilter3;
import com.wyj.test.security.simple.token.SimpleUserService;
import com.wyj.test.security.simple.token.TokenFilter;

/**
 * @author wuyingjie
 * Created on 2020-02-10
 */

@Configuration
@EnableWebSecurity
@Profile("token")
public class WebSecurityConfig_TOKEN extends WebSecurityConfigurerAdapter {

    @Autowired
    private SimpleUserService simpleUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests().antMatchers("/test/noAuth/**").permitAll()
//                .and().authorizeRequests().antMatchers("*/**").authenticated()
                .and().authorizeRequests().antMatchers("/test/admin/**").hasRole("admin")
                .and().authorizeRequests().anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                .and().formLogin()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().httpBasic()
                .and().addFilterBefore(new TokenFilter(simpleUserService), UsernamePasswordAuthenticationFilter.class);

        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .withUser("user1").password("user1").roles("")
//                .and().withUser("admin").password("admin").roles("admin");
//        super.configure(auth);
        auth.userDetailsService(simpleUserService).passwordEncoder(NoOpPasswordEncoder.getInstance());

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
