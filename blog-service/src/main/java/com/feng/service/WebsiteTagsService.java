package com.feng.service;

import com.feng.pojo.entity.WebsiteTags;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 网站标签 服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface WebsiteTagsService extends IService<WebsiteTags> {

    List<WebsiteTags> getWebsiteTagList();

    String saveOrUpdateSiteNavTags(WebsiteTags websiteTags);
}
