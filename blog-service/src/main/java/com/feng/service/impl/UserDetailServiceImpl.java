package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.feng.common.constant.RedisPrefixConst;
import com.feng.common.exception.BlogException;
import com.feng.common.result.ResponseEnum;
import com.feng.common.result.ZoneEnum;
import com.feng.common.util.IpUtils;
import com.feng.common.util.RedisUtils;
import com.feng.mapper.RoleMapper;
import com.feng.mapper.UserAuthMapper;
import com.feng.mapper.UserInfoMapper;
import com.feng.pojo.dto.UserDetailDTO;
import com.feng.pojo.entity.UserAuth;
import com.feng.pojo.entity.UserInfo;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Mr.Feng
 * @date 10/8/2022 7:45 PM
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    private UserAuthMapper userAuthMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (Strings.isBlank(username)){
            throw new BlogException(ResponseEnum.USERNAME_NULL_ERROR.getMessage());
        }

        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getId, UserAuth::getUserInfoId, UserAuth::getUsername, UserAuth::getPassword, UserAuth::getLoginType)
                .eq(UserAuth::getUsername, username));

        if (Objects.isNull(userAuth)) {
            throw new BlogException(ResponseEnum.LOGIN_USER_ERROR.getMessage());
        }
        // 封装登录信息
        return convertUserDetail(userAuth, request);
    }

    /**
     * 封装用户登录信息
     *
     * @param user    用户账号
     * @param request 请求
     * @return 用户登录信息
     */
    public UserDetailDTO convertUserDetail(UserAuth user, HttpServletRequest request) {
        // 查询账号信息
        UserInfo userInfo = userInfoMapper.selectById(user.getUserInfoId());
        // 查询账号角色
        List<String> roleList = roleMapper.listRolesByUserInfoId(userInfo.getId());
        // 查询账号点赞信息
        redisUtils.set("key", "value");
        Set<Object> articleLikeSet = redisUtils.sMembers(RedisPrefixConst.ARTICLE_USER_LIKE + userInfo.getId());
        Set<Object> commentLikeSet = redisUtils.sMembers(RedisPrefixConst.COMMENT_USER_LIKE + userInfo.getId());
        // 获取设备信息
        String ipAddress = IpUtils.getIpAddress(request);
        String ipSource = IpUtils.getIpSource(ipAddress);
        UserAgent userAgent = IpUtils.getUserAgent(request);
        // 封装权限集合
        return UserDetailDTO.builder()
                .id(user.getId())
                .loginType(user.getLoginType())
                .userInfoId(userInfo.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(userInfo.getEmail())
                .roleList(roleList)
                .nickname(userInfo.getNickname())
                .avatar(userInfo.getAvatar())
                .intro(userInfo.getIntro())
                .webSite(userInfo.getWebSite())
                .articleLikeSet(articleLikeSet)
                .commentLikeSet(commentLikeSet)
                .ipAddress(ipAddress)
                .ipSource(ipSource)
                .isDisable(userInfo.getDisable())
                .browser(userAgent.getBrowser().getName())
                .os(userAgent.getOperatingSystem().getName())
                .lastLoginTime(LocalDateTime.now(ZoneId.of(ZoneEnum.SHANGHAI.getZone())))
                .build();
    }
}
