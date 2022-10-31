package com.feng.handler;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.feng.common.constant.RedisPrefixConst;
import com.feng.common.exception.BlogException;
import com.feng.common.result.R;
import com.feng.common.util.BeanCopyUtils;
import com.feng.common.util.JwtUtils;
import com.feng.common.util.RedisUtils;
import com.feng.pojo.dto.UserDetailDTO;
import com.feng.pojo.dto.UserInfoDTO;
import com.feng.pojo.vo.LoginVo;
import com.feng.pojo.vo.UserAuthVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author Mr.Feng
 * @date 10/8/2022 3:15 PM
 */
public class TokenLoginHandler extends UsernamePasswordAuthenticationFilter {

    private RedisUtils redisUtils;

    private AuthenticationManager authenticationManager;

    public TokenLoginHandler(AuthenticationManager authenticationManager, RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
        this.authenticationManager = authenticationManager;
        this.setPostOnly(true);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/userAuth/core/login","POST"));
    }

    // 获取表单提交的信息
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginVo loginVo = new ObjectMapper().readValue(request.getInputStream(), LoginVo.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // 认证成功调用的方法
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 认证成功的用户信息
        UserDetailDTO userDetailDTO = (UserDetailDTO) authResult.getPrincipal();

        UserInfoDTO userInfoDTO = BeanCopyUtils.copyObject(userDetailDTO, UserInfoDTO.class);
        String token = JwtUtils.createToken(userDetailDTO.getId().longValue(), userDetailDTO.getUsername());
        userInfoDTO.setToken(token);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(R.ok().data("userInfo", userInfoDTO).message("started")));

        redisUtils.set(userDetailDTO.getUserInfoId().toString(), userInfoDTO);
    }

    // 认证失败调用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write(JSON.toJSONString(R.error()));
    }
}
