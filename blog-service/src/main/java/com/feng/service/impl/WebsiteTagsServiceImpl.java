package com.feng.service.impl;

import com.feng.pojo.entity.WebsiteTags;
import com.feng.mapper.WebsiteTagsMapper;
import com.feng.service.WebsiteTagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 网站标签 服务实现类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@Service
public class WebsiteTagsServiceImpl extends ServiceImpl<WebsiteTagsMapper, WebsiteTags> implements WebsiteTagsService {

    @Override
    public List<WebsiteTags> getWebsiteTagList() {
        return baseMapper.selectList(null);
    }

    @Override
    public String saveOrUpdateSiteNavTags(WebsiteTags websiteTags) {
        if (Objects.isNull(websiteTags.getId())){
            baseMapper.insert(websiteTags);
            return "添加成功";
        }else {
            baseMapper.updateById(websiteTags);
            return "更新成功";
        }
    }
}
