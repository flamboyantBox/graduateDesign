package com.feng.client;

import com.feng.common.dto.FaceInfo;
import com.feng.common.result.R;
import com.feng.common.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(value = "service-blog", path = "/userInfo/core")
public interface FaceInfoClient {
    @GetMapping("getFaceInfo")
    List<FaceInfo> getFaceInfo();
}
