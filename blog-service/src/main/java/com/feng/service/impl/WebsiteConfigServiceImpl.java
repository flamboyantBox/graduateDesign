package com.feng.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.feng.common.constant.RedisPrefixConst;
import com.feng.common.result.ArticleStatusEnum;
import com.feng.common.util.RedisUtils;
import com.feng.pojo.dto.BlogHomeInfoDTO;
import com.feng.pojo.entity.Article;
import com.feng.pojo.entity.WebsiteConfig;
import com.feng.mapper.WebsiteConfigMapper;
import com.feng.pojo.vo.PageVo;
import com.feng.pojo.vo.WebsiteConfigVo;
import com.feng.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@Service
public class WebsiteConfigServiceImpl extends ServiceImpl<WebsiteConfigMapper, WebsiteConfig> implements WebsiteConfigService {

    @Resource
    private RedisUtils redisUtils;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PageService pageService;

    @Override
    public WebsiteConfigVo getWebsiteConfig() {
        WebsiteConfigVo websiteConfigVo;

        // 获取缓存中的数据
        Object RedisWebsiteConfig = redisUtils.get(RedisPrefixConst.WEBSITE_CONFIG);
        if (Objects.nonNull(RedisWebsiteConfig)) {
            System.out.println("redis缓存中的数据");
            websiteConfigVo = JSON.parseObject(RedisWebsiteConfig.toString(), WebsiteConfigVo.class);
        }else {
            // 从数据库中加载
            System.out.println("数据库中的数据");
            String config  = baseMapper.selectById(1).getConfig();
            websiteConfigVo = JSON.parseObject(config, WebsiteConfigVo.class);
            redisUtils.set(RedisPrefixConst.WEBSITE_CONFIG, config);
            System.out.println("保存进redis缓存中");
        }

        return websiteConfigVo;
    }

    @Override
    public void updateWebsiteConfig(WebsiteConfigVo websiteConfigVo) {
        // 修改网站配置
        WebsiteConfig websiteConfig = baseMapper.selectById(1);
        websiteConfig.setConfig(JSON.toJSONString(websiteConfigVo));
        baseMapper.updateById(websiteConfig);
        // 删除缓存
        redisUtils.del(RedisPrefixConst.WEBSITE_CONFIG);
    }

    @Override
    public BlogHomeInfoDTO getBlogHomeInfo() {
        // 查询文章数量
        int articleCount = articleService.count(new LambdaQueryWrapper<Article>()
                .eq(Article::getStatus, ArticleStatusEnum.PUBLIC.getStatus())
                .eq(Article::getDeleted, 0));

        // 查询分类数量
        int categoryCount = categoryService.count(null);

        // 查询标签数量
        int tagCount = tagService.count(null);

        // 查询访问量
        // TODO
        String viewCount = "0";

        // 查询网站配置
        WebsiteConfigVo websiteConfig = this.getWebsiteConfig();

        // 查询页面图片
        List<PageVo> pageVoList = pageService.listPages();

        // 数据封装
        return BlogHomeInfoDTO.builder()
                .articleCount(articleCount)
                .categoryCount(categoryCount)
                .tagCount(tagCount)
                .viewsCount(viewCount)
                .pageList(pageVoList)
                .websiteConfig(websiteConfig)
                .build();
    }
}
