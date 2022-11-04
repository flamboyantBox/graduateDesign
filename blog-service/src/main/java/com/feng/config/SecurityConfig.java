package com.feng.config;

import com.feng.common.util.RedisUtils;
import com.feng.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.annotation.Resource;

/**
 * @author Mr.Feng
 * @date 10/7/2022 9:03 PM
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailsService userDetailService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private DefaultPasswordEncoder defaultPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(new UnauthEntryPoint()) //没有权限访问
                .and().csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and().logout().logoutUrl("/admin/acl/index/logout") //退出路径
                .addLogoutHandler(new TokenLogoutHandler()).and()
                .addFilter(new TokenLoginHandler(authenticationManager(), redisUtils))
                .httpBasic();
    }

    /**
     * 调用userDetailsService和密码处理
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(defaultPasswordEncoder);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 不进行认证的路径，可以直接访问
     * @param web
     * @throws Exception
     */
}
