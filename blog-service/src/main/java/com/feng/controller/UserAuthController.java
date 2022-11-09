package com.feng.controller;


import com.feng.common.exception.Assert;
import com.feng.common.result.R;
import com.feng.common.result.ResponseEnum;
import com.feng.common.util.JwtUtils;
import com.feng.common.vo.UserRegisterVo;
import com.feng.pojo.dto.UserAreaDTO;
import com.feng.pojo.dto.UserFrontInfoDTO;
import com.feng.pojo.vo.*;
import com.feng.service.UserAuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-08
 */
@RestController
@RequestMapping("/userAuth/core")
public class UserAuthController {
    @Autowired
    private UserAuthService userAuthService;

    @ApiOperation("管理员登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo, HttpServletRequest request){
        // 验证用户名和密码不为空
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();
        Assert.notEmpty(username, ResponseEnum.USERNAME_NULL_ERROR);
        Assert.notEmpty(password, ResponseEnum.PASSWORD_NULL_ERROR);

        // 获取用户的登录ip地址
        String ip = request.getRemoteAddr();
        UserAuthVo userInfoVo = userAuthService.login(loginVo, ip);
        return R.ok().data("userInfo", userInfoVo);
    }

    @ApiOperation(value = "前台用户登录")
    @PostMapping("loginFront")
    public R loginFront(@Valid @RequestBody LoginVo loginVo) {
        UserFrontInfoDTO userFrontInfoDTO = userAuthService.loginFront(loginVo);
        return R.ok().data("userFrontInfo", userFrontInfoDTO).message("登录成功!");
    }

    @ApiOperation("管理员注册")
    @PostMapping("register")
    public R register(@Valid @RequestBody RegisterVo registerVo){
        userAuthService.register(registerVo);
        return R.ok().message("管理员注册成功");
    }

    @ApiOperation("用户注册")
    @PostMapping("userRegister")
    public R userRegister(@Valid @RequestBody UserRegisterVo userRegisterVo){
        userAuthService.userRegister(userRegisterVo);
        return R.ok().message("用户注册成功");
    }

    @ApiOperation(value = "修改管理员密码")
    @PutMapping("updatePassword")
    public R updatePassword(@Valid @RequestBody UpdatePasswordVo updatePasswordVo, HttpServletRequest request){
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        userAuthService.updatePassword(userId, updatePasswordVo);
        return R.ok().message("修改成功");
    }

    @ApiOperation(value = "退出登录")
    @GetMapping("logout")
    public R logout(@RequestParam("userId") Integer userId) {
        userAuthService.logout(userId);
        return R.ok().message("退出成功");
    }

    @ApiOperation(value = "获取用户区域分布")
    @GetMapping("area")
    public R listUserAreas(ConditionVo conditionVo) {
        List<UserAreaDTO> userAreaDTOList = userAuthService.listUserAreas(conditionVo);
        return R.ok().data("area", userAreaDTOList);
    }
}

