package com.feng.handler;

import com.feng.common.util.JwtUtils;
import com.feng.common.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 授权Filter
 */
public class TokenAuthHandler extends BasicAuthenticationFilter {
    private RedisUtils redisUtils;

    public TokenAuthHandler(AuthenticationManager authenticationManager, RedisUtils redisUtils) {
        super(authenticationManager);
        this.redisUtils = redisUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        获取当前认证用户权限信息
        UsernamePasswordAuthenticationToken authRequest = getAuthentication(request);
//        判断如果有权限信息，放到权限上下文中
        if (authRequest != null) {
            SecurityContextHolder.getContext().setAuthentication(authRequest);
        }
        chain.doFilter(request, response);
    }

    /**
     * 获取当前认证用户权限信息
     * @param request
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
//        从header获取token
        String token = request.getHeader("token");
        if (token != null) {
//            从token获取用户名
            String username = JwtUtils.getUserName(token);

//            从redis获取对应权限列表
            List<String> permissionValueList = (List<String>) redisUtils.get(username);

            Collection<GrantedAuthority> authority = new ArrayList<>();
            permissionValueList.forEach(permisson->{
                SimpleGrantedAuthority auth = new SimpleGrantedAuthority(permisson);
                authority.add(auth);
            });
            return new UsernamePasswordAuthenticationToken(username, token, authority);
        }
        return null;
    }
}
