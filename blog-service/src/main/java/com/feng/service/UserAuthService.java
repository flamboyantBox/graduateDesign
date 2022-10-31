package com.feng.service;

import com.feng.common.vo.UserRegisterVo;
import com.feng.pojo.dto.UserFrontInfoDTO;
import com.feng.pojo.entity.UserAuth;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.pojo.vo.*;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-08
 */
public interface UserAuthService extends IService<UserAuth> {

    UserAuthVo login(LoginVo loginVo, String ip);

    void updatePassword(Long userId, UpdatePasswordVo updatePasswordVo);

    void register(RegisterVo registerVo);

    UserFrontInfoDTO loginFront(LoginVo loginVo);

    void logout(Integer userId);

    void userRegister(UserRegisterVo userRegisterVo);
}
