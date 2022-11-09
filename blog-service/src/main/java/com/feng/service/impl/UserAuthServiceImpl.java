package com.feng.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.feng.common.constant.RedisPrefixConst;
import com.feng.common.exception.Assert;
import com.feng.common.exception.BlogException;
import com.feng.common.result.LoginTypeEnum;
import com.feng.common.result.ResponseEnum;
import com.feng.common.result.RoleEnum;
import com.feng.common.result.UserAreaTypeEnum;
import com.feng.common.util.JwtUtils;
import com.feng.common.util.MD5;
import com.feng.common.util.RedisUtils;
import com.feng.common.vo.UserRegisterVo;
import com.feng.pojo.dto.UserAreaDTO;
import com.feng.pojo.dto.UserFrontInfoDTO;
import com.feng.pojo.entity.UserAuth;
import com.feng.mapper.UserAuthMapper;
import com.feng.pojo.entity.UserInfo;
import com.feng.pojo.entity.UserRole;
import com.feng.pojo.vo.*;
import com.feng.service.UserAuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.service.UserInfoService;
import com.feng.service.UserRoleService;
import com.feng.service.WebsiteConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-08
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements UserAuthService {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private WebsiteConfigService websiteConfigService;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public UserAuthVo login(LoginVo loginVo, String ip) {
        // 匹配数据库的字段，判断登录是否成功
        UserAuth userAuth = baseMapper.selectOne(new QueryWrapper<UserAuth>()
                .eq("username", loginVo.getUsername())
                .eq("login_type", loginVo.getLoginType()));
        Assert.notNull(userAuth, ResponseEnum.LOGIN_USER_ERROR);
        Assert.equals(MD5.encrypt(loginVo.getPassword()), userAuth.getPassword(), ResponseEnum.LOGIN_PASSWORD_ERROR);
        userAuth.setIpAddress(ip);
        userAuth.setLastLoginTime(LocalDateTime.now());
        baseMapper.updateById(userAuth);

        // 设置token令牌，创建userAuthVo类并赋值
        String token = JwtUtils.createToken(userAuth.getId().longValue(), userAuth.getUsername());
        UserAuthVo userAuthVo = new UserAuthVo();
        userAuthVo.setUsername(userAuth.getUsername());
        userAuthVo.setToken(token);
        userAuthVo.setIp(userAuth.getIpAddress());
        userAuthVo.setLoginType(userAuth.getLoginType());
        return userAuthVo;
    }

    @Override
    public void updatePassword(Long userId, UpdatePasswordVo updatePasswordVo) {
        UserAuth userAuth = baseMapper.selectById(userId);
        // 查询旧密码是否正确
        String password = userAuth.getPassword();
        Assert.equals(password, MD5.encrypt(updatePasswordVo.getOldPassword()), ResponseEnum.OLD_PASSWORD_ERROR);

        // 修改密码
        userAuth.setPassword(MD5.encrypt(updatePasswordVo.getNewPassword()));
        baseMapper.updateById(userAuth);
    }

    @Override
    public void register(RegisterVo registerVo) {
        // 判断数据库是否已经存在用户名
        Integer count = baseMapper.selectCount(new QueryWrapper<UserAuth>().eq("username", registerVo.getUsername()));

        if (count > 0){
            throw new BlogException(ResponseEnum.USERNAME_EXIST);
        }else {
            UserAuth userAuth = new UserAuth();
            userAuth.setUsername(registerVo.getUsername());
            userAuth.setPassword(MD5.encrypt(registerVo.getPassword()));
            userAuth.setLoginType(registerVo.getLoginType());

            UserInfo userInfo = new UserInfo();
            userInfo.setEmail(userAuth.getUsername());
            userInfoService.save(userInfo);

            userAuth.setUserInfoId(userInfo.getId());
            baseMapper.insert(userAuth);
        }

    }

    @Override
    public UserFrontInfoDTO loginFront(LoginVo loginVo) {
        return null;
    }

    @Override
    public void logout(Integer userId) {
        redisUtils.del(userId.toString());
    }

    @Override
    public void userRegister(UserRegisterVo userRegisterVo) {
        //查询用户名是否存在
        UserAuth userAuthCheck = baseMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getUsername)
                .eq(UserAuth::getUsername, userRegisterVo.getUsername()));

        Assert.isNull(userAuthCheck, ResponseEnum.USERNAME_EXIST);

        // 新增用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(userRegisterVo.getUsername());
        userInfo.setNickname("用户" + IdWorker.getId());
        userInfo.setFaceInfo(userRegisterVo.getFaceInfo());
        userInfo.setAvatar(websiteConfigService.getWebsiteConfig().getUserAvatar());
        userInfoService.save(userInfo);
        System.out.println(userInfo.getId());

        // 绑定用户角色
        UserRole userRole = new UserRole();
        userRole.setUserId(userInfo.getId());
        userRole.setRoleId(RoleEnum.USER.getRoleId());
        userRoleService.save(userRole);

        // 新增用户账号
        UserAuth userAuth = new UserAuth();
        userAuth.setUserInfoId(userInfo.getId());
        userAuth.setUsername(userRegisterVo.getUsername());
        userAuth.setPassword(MD5.encrypt("123456"));
        userAuth.setLoginType(LoginTypeEnum.FACE.getType());
        userAuthService.save(userAuth);
    }

    @Override
    public List<UserAreaDTO> listUserAreas(ConditionVo conditionVo) {
        List<UserAreaDTO> userAreaDTOList = new ArrayList<>();

        switch (Objects.requireNonNull(UserAreaTypeEnum.getUserAreaType(conditionVo.getType()))) {
            case USER:
                // 查询注册用户区域分布
                Object userArea = redisUtils.get(RedisPrefixConst.USER_AREA);
                if (Objects.nonNull(userArea)) {
                    userAreaDTOList = JSON.parseObject(userArea.toString(), List.class);
                }
                return userAreaDTOList;
            case VISITOR:
                // 查询游客区域分布
                Map<String, Object> visitorArea = redisUtils.hGetAll(RedisPrefixConst.VISITOR_AREA);
                if (Objects.nonNull(visitorArea)) {
                    userAreaDTOList = visitorArea.entrySet().stream()
                            .map(item -> UserAreaDTO.builder()
                                    .name(item.getKey())
                                    .value(Long.valueOf(item.getValue().toString()))
                                    .build())
                            .collect(Collectors.toList());
                }
                return userAreaDTOList;
            default:
                break;
        }
        return userAreaDTOList;

    }

    /**
     * 统计用户地区
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void statisticalUserArea() {
        // 统计用户地域分布
        Map<String, Long> userAreaMap = baseMapper.selectList(new LambdaQueryWrapper<UserAuth>())
                .stream()
                .map(item -> {
                    if (StringUtils.isNotBlank(item.getIpSource())) {
                        return item.getIpSource().substring(0, 2)
                                .replaceAll("省", "")
                                .replaceAll("市区", "");
                    }
                    return "未知";
                })
                .collect(Collectors.groupingBy(item -> item, Collectors.counting()));
        // 转换格式
        List<UserAreaDTO> userAreaList = userAreaMap.entrySet().stream()
                .map(item -> UserAreaDTO.builder()
                        .name(item.getKey())
                        .value(item.getValue())
                        .build())
                .collect(Collectors.toList());
        redisUtils.set(RedisPrefixConst.USER_AREA, JSON.toJSONString(userAreaList));
    }
}
