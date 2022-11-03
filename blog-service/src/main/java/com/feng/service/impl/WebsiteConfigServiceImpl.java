package com.feng.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.feng.common.constant.RedisPrefixConst;
import com.feng.common.result.AddressEnum;
import com.feng.common.result.ArticleStatusEnum;
import com.feng.common.util.BeanCopyUtils;
import com.feng.common.util.IpUtils;
import com.feng.common.util.RedisUtils;
import com.feng.mapper.*;
import com.feng.pojo.dto.*;
import com.feng.pojo.entity.Article;
import com.feng.pojo.entity.WebsiteConfig;
import com.feng.pojo.vo.PageVo;
import com.feng.pojo.vo.WebsiteConfigVo;
import com.feng.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private HttpServletRequest request;

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Autowired
    private UniqueViewService uniqueViewService;

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

    @Override
    public BlogBackInfoDTO getBlogBackInfo() {
        // 查询访问量
        Object count = redisUtils.get(RedisPrefixConst.BLOG_VIEWS_COUNT);
        Integer viewsCount = Integer.parseInt(Optional.ofNullable(count).orElse(0).toString());
        // 查询留言量
        Integer messageCount = messageMapper.selectCount(null);
        // 查询用户量
        Integer userCount = userInfoMapper.selectCount(null);
        // 查询文章量
        Integer articleCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getDeleted, 0));
        // 查询一周用户量
        List<UniqueViewDTO> uniqueViewList = uniqueViewService.listUniqueViews();
        // 查询文章统计
        List<ArticleStatisticsDTO> articleStatisticsList = articleMapper.listArticleStatistics();
        // 查询分类数据
        List<CategoryDTO> categoryDTOList = categoryMapper.listCategoryDTO();
        // 查询标签数据
        List<TagDTO> tagDTOList = BeanCopyUtils.copyList(tagMapper.selectList(null), TagDTO.class);
        // 查询redis访问量前五的文章
        Map<Object, Double> articleMap = redisUtils.zReverseRangeWithScore(RedisPrefixConst.ARTICLE_VIEWS_COUNT, 0, 4);
        BlogBackInfoDTO blogBackInfoDTO = BlogBackInfoDTO.builder()
                .articleStatisticsList(articleStatisticsList)
                .tagDTOList(tagDTOList)
                .viewsCount(viewsCount)
                .messageCount(messageCount)
                .userCount(userCount)
                .articleCount(articleCount)
                .categoryDTOList(categoryDTOList)
                .uniqueViewDTOList(uniqueViewList)
                .build();
        if (CollectionUtils.isNotEmpty(articleMap)) {
            // 查询文章排行
            List<ArticleRankDTO> articleRankDTOList = listArticleRank(articleMap);
            blogBackInfoDTO.setArticleRankDTOList(articleRankDTOList);
        }
        return blogBackInfoDTO;
    }

    /**
     * 查询文章排行
     *
     * @param articleMap 文章信息
     * @return {@link List<ArticleRankDTO>} 文章排行
     */
    private List<ArticleRankDTO> listArticleRank(Map<Object, Double> articleMap) {
        // 提取文章id
        List<Integer> articleIdList = new ArrayList<>();
        articleMap.forEach((key, value) -> articleIdList.add((Integer) key));
        // 查询文章信息
        return articleMapper.selectList(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle)
                .in(Article::getId, articleIdList))
                .stream().map(article -> ArticleRankDTO.builder()
                        .articleTitle(article.getArticleTitle())
                        .viewsCount(articleMap.get(article.getId()).intValue())
                        .build())
                .sorted(Comparator.comparingInt(ArticleRankDTO::getViewsCount).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void report() {
        // 获取ip
        String ipAddress = IpUtils.getIpAddress(request);

        // 获取访问设备
        UserAgent userAgent = IpUtils.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();

        // 生成唯一用户标识
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());

        /**
         * sIsMember
         *      target是否存在source缓存当中
         * hIncr
         *      自增+=
         * sAdd
         *      加入新值与sIsMember连用
         */
        // 判断是否访问
        if (!redisUtils.sIsMember(RedisPrefixConst.UNIQUE_VISITOR, md5)) {
            // 统计游客地域分布
            String ipSource = IpUtils.getIpSource(ipAddress);
            if (StringUtils.isNotBlank(ipSource)) {
                ipSource = ipSource.substring(0, 2)
                        .replaceAll(AddressEnum.PROVINCE.getAddress(), "")
                        .replaceAll(AddressEnum.CITY.getAddress(), "");
                redisUtils.hIncr(RedisPrefixConst.VISITOR_AREA, ipSource, 1L);
            } else {
                redisUtils.hIncr(RedisPrefixConst.VISITOR_AREA, AddressEnum.UNKNOWN.getAddress(), 1L);
            }
            // 访问量+1
            redisUtils.incr(RedisPrefixConst.BLOG_VIEWS_COUNT, 1);
            // 保存唯一标识
            redisUtils.sAdd(RedisPrefixConst.UNIQUE_VISITOR, md5);
        }
    }
}
