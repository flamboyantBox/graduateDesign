package com.feng.oss.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mr.Feng
 * @date 7/14/2022 4:34 PM
 */
@FeignClient(value = "service-blog")
public interface UserInfoClient {
    @GetMapping("/userInfo/core/getAvatar")
    String getAvatar(@RequestParam("tokenId") Long tokenId);

    @GetMapping("/websiteConfig/core/getWebImg")
    String getWebImg();

    @GetMapping("/websiteConfig/core/getUserImg")
    String getUserImg();

    @GetMapping("/websiteConfig/core/getGuestImg")
    String getGuestImg();

    @GetMapping("/websiteConfig/core/getAlipayImg")
    String getAlipayImg();

    @GetMapping("/websiteConfig/core/getWeChatImg")
    String getWeChatImg();
}
