package com.feng.service;

import com.feng.pojo.dto.BlogHomeInfoDTO;
import com.feng.pojo.entity.WebsiteConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.pojo.vo.WebsiteConfigVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface WebsiteConfigService extends IService<WebsiteConfig> {

    WebsiteConfigVo getWebsiteConfig();

    void updateWebsiteConfig(WebsiteConfigVo websiteConfigVo);

    BlogHomeInfoDTO getBlogHomeInfo();
}
