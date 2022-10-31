package com.feng.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.feng.common.dto.FaceInfo;
import com.feng.common.exception.Assert;
import com.feng.common.result.R;
import com.feng.common.result.ResponseEnum;
import com.feng.common.util.JwtUtils;
import com.feng.pojo.entity.UserAuth;
import com.feng.pojo.entity.UserInfo;
import com.feng.pojo.vo.UserInfoVo;
import com.feng.service.UserAuthService;
import com.feng.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@RestController
@RequestMapping("/userInfo/core")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserAuthService userAuthService;

    @ApiOperation("根据Id获取用户信息")
    @GetMapping("user")
    public R user(HttpServletRequest request){
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        UserInfo userInfo = userInfoService.getUserInfo(userId);
        Assert.notNull(userInfo, ResponseEnum.FIND_ERROR);
        return R.ok().data("userInfo", userInfo);
    }

    @ApiOperation("更改用户信息")
    @PostMapping("updateInfo")
    public R updateInfo(@RequestBody UserInfoVo userInfoVo, HttpServletRequest request){
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        userInfoService.updateUserInfoVoByUserId(userInfoVo, userId);
        return R.ok().message("信息保存成功");
    }

    @ApiOperation("远程调用OSS实现删除")
    @GetMapping("getAvatar")
    public String getAvatar(@RequestParam("tokenId") Long tokenId){
        UserInfo userInfo = userInfoService.getUserInfo(tokenId);
        Assert.notNull(userInfo, ResponseEnum.FIND_ERROR);
        return userInfo.getAvatar();
    }

    @ApiOperation("首页获取用户头像")
    @GetMapping("getIndexAvatar")
    public R getIndexAvatar(HttpServletRequest request){
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        String avatar = userInfoService.getIndexAvatar(userId);

        return R.ok().data("avatar", avatar);
    }

    @ApiOperation("人脸信息")
    @GetMapping("getFaceInfo")
    public List<FaceInfo> getFaceInfo(){
        List<UserInfo> userInfoList = userInfoService.list();

        return userInfoList.stream().map(item -> {
            UserAuth userAuth = userAuthService.getById(item.getId());
            return FaceInfo.builder()
                    .username(userAuth.getUsername())
                    .password(userAuth.getPassword())
                    .faceInfo(item.getFaceInfo())
                    .build();
        }).collect(Collectors.toList());
    }
}

