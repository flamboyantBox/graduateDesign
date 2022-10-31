package com.feng.service.impl;

import com.feng.pojo.entity.UserAuth;
import com.feng.pojo.entity.UserInfo;
import com.feng.mapper.UserInfoMapper;
import com.feng.pojo.vo.UserInfoVo;
import com.feng.service.UserAuthService;
import com.feng.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Autowired
    private UserAuthService userAuthService;

    @Override
    public void updateUserInfoVoByUserId(UserInfoVo userInfoVo, Long userId) {
        UserAuth userAuth = userAuthService.getById(userId);
        Integer userInfoId = userAuth.getUserInfoId();
        UserInfo userInfo = baseMapper.selectById(userInfoId);
        BeanUtils.copyProperties(userInfoVo, userInfo);
        baseMapper.updateById(userInfo);
    }

    @Override
    public UserInfo getUserInfo(Long userId) {
        UserAuth userAuth = userAuthService.getById(userId);
        Integer userInfoId = userAuth.getUserInfoId();
        return baseMapper.selectById(userInfoId);
    }

    @Override
    public String getIndexAvatar(Long userId) {
        UserInfo userInfo = baseMapper.selectById(userId);
        return userInfo.getAvatar();
    }
}
