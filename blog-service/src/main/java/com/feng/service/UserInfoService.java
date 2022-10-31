package com.feng.service;

import com.feng.pojo.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.pojo.vo.UserInfoVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface UserInfoService extends IService<UserInfo> {

    void updateUserInfoVoByUserId(UserInfoVo userInfoVo, Long userId);

    UserInfo getUserInfo(Long userId);

    String getIndexAvatar(Long userId);
}
