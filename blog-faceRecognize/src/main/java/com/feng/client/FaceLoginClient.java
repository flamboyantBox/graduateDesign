package com.feng.client;

import com.feng.common.result.R;
import com.feng.common.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "service-blog", path = "/userAuth/core")
public interface FaceLoginClient {
    @PostMapping("userRegister")
    R userRegister(@Valid @RequestBody UserRegisterVo userRegisterVo);
}
