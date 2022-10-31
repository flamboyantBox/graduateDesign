package com.feng.handler;

import com.alibaba.fastjson.JSON;
import com.feng.common.result.R;
import com.feng.common.util.JwtUtils;
import com.feng.common.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出实现
 */
public class TokenLogoutHandler implements LogoutHandler {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 1.从header里面获取token
        // 2.token不为空，移除tokrn，congredis删除token
        String token = request.getHeader("token");
        if (token != null) {
            //移除
            JwtUtils.removeToken(token);
            //从token获取用户名
            String username = JwtUtils.getUserName(token);
            redisUtils.del(username);
        }

        try {
            response.getWriter().write(JSON.toJSONString(R.error()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
