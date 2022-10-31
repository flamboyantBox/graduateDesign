package com.feng.controller;


import com.alibaba.fastjson.JSON;
import com.feng.common.exception.Assert;
import com.feng.common.result.R;
import com.feng.common.result.ResponseEnum;
import com.feng.pojo.dto.BlogHomeInfoDTO;
import com.feng.pojo.entity.UserInfo;
import com.feng.pojo.entity.WebsiteConfig;
import com.feng.pojo.vo.WebsiteConfigVo;
import com.feng.service.WebsiteConfigService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@RestController
@RequestMapping("/websiteConfig/core")
public class WebsiteConfigController {
    @Autowired
    private WebsiteConfigService websiteConfigService;

    @ApiOperation(value = "查看博客信息")
    @GetMapping("/BlogHomeInfo")
    public R getBlogHomeInfo() {
        BlogHomeInfoDTO blogHomeInfoDTO = websiteConfigService.getBlogHomeInfo();
        return R.ok().data("blogHomeInfo", blogHomeInfoDTO);
    }

    @ApiOperation(value = "获取登录用户的信息")
    @PostMapping("/loginInfo")
    public R report() {
//        websiteConfigService.loginInfo();
        return R.ok();
    }

    @ApiOperation("获取网站配置")
    @GetMapping("/getWebsiteConfig")
    public R getWebsiteConfig(){
        WebsiteConfigVo websiteConfigVo = websiteConfigService.getWebsiteConfig();
        return R.ok().data("data", websiteConfigVo);
    }

    @ApiOperation("更新网站配置")
    @PutMapping("updateWebsiteConfig")
    public R updateWebsiteConfig(@Valid @RequestBody WebsiteConfigVo websiteConfigVo){
        websiteConfigService.updateWebsiteConfig(websiteConfigVo);
        return R.ok().message("更新成功");
    }

    @ApiOperation("远程调用OSS实现删除WebImg")
    @GetMapping("getWebImg")
    public String getWebImg(){
        String webConfig = websiteConfigService.getById(1).getConfig();
        WebsiteConfigVo websiteConfigVo = JSON.parseObject(webConfig, WebsiteConfigVo.class);
        return websiteConfigVo.getWebsiteAvatar();
    }

    @ApiOperation("远程调用OSS实现删除UserImg")
    @GetMapping("getUserImg")
    public String getUserImg(){
        String webConfig = websiteConfigService.getById(1).getConfig();
        WebsiteConfigVo websiteConfigVo = JSON.parseObject(webConfig, WebsiteConfigVo.class);
        return websiteConfigVo.getUserAvatar();
    }

    @ApiOperation("远程调用OSS实现删除GuestImg")
    @GetMapping("getGuestImg")
    public String getGuestImg(){
        String webConfig = websiteConfigService.getById(1).getConfig();
        WebsiteConfigVo websiteConfigVo = JSON.parseObject(webConfig, WebsiteConfigVo.class);
        return websiteConfigVo.getTouristAvatar();
    }

    @ApiOperation("远程调用OSS实现删除alipayImg")
    @GetMapping("getAlipayImg")
    public String getAlipayImg(){
        String webConfig = websiteConfigService.getById(1).getConfig();
        WebsiteConfigVo websiteConfigVo = JSON.parseObject(webConfig, WebsiteConfigVo.class);
        return websiteConfigVo.getAlipayQRCode();
    }

    @ApiOperation("远程调用OSS实现删除WeChatImg")
    @GetMapping("getWeChatImg")
    public String getWeChatImg(){
        String webConfig = websiteConfigService.getById(1).getConfig();
        WebsiteConfigVo websiteConfigVo = JSON.parseObject(webConfig, WebsiteConfigVo.class);
        return websiteConfigVo.getWeiXinQRCode();
    }

}

