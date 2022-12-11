package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.feng.common.constant.RedisPrefixConst;
import com.feng.common.exception.Assert;
import com.feng.common.exception.BlogException;
import com.feng.common.exception.GlobalException;
import com.feng.common.result.ArticleStatusEnum;
import com.feng.common.util.RedisUtils;
import com.feng.mapper.TagMapper;
import com.feng.pojo.dto.*;
import com.feng.pojo.entity.Article;
import com.feng.mapper.ArticleMapper;
import com.feng.pojo.entity.ArticleTag;
import com.feng.pojo.entity.Category;
import com.feng.pojo.entity.Tag;
import com.feng.pojo.vo.*;
import com.feng.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.service.ArticleTagService;
import com.feng.service.CategoryService;
import com.feng.service.TagService;
import com.feng.strategy.context.SearchStrategyContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.CompletableFuture;
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
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private TagService tagService;

    @Resource
    private TagMapper tagMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private HttpSession session;

    @Autowired
    private SearchStrategyContext searchStrategyContext;

    @Transactional
    @Override
    public List<ArticleDTO> articleList(ConditionVo conditionVo) {
        // 查询文章
        List<ArticleDTO> articleDTOList = baseMapper.listArticleBacks(conditionVo);

        // 遍历每一个文章的id
        articleDTOList.forEach(item -> {
            // 为ArticleDTO的tagListDTO赋值
            ArrayList<TagDTO> tagDTOList = new ArrayList<>();

            // 查询文章id所对应的标签
            List<ArticleTag> articleTagList = articleTagService.list(new QueryWrapper<ArticleTag>().eq("article_id", item.getId()));

            // 查找出每一个标签的名add到数组中，并赋值给TagDTO
            articleTagList.forEach(articleTag -> {
                Tag tag = tagService.getOne(new QueryWrapper<Tag>().eq("id", articleTag.getTagId()));
                TagDTO tagDTO = TagDTO.builder()
                        .id(tag.getId())
                        .tagName(tag.getTagName())
                        .build();
                tagDTOList.add(tagDTO);
            });
            item.setTagDTOList(tagDTOList);
        });

        return articleDTOList;
    }

    @Override
    public void recoverOrDeleteArticle(DeleteVo logicDeleteVo) {
        // 修改文章逻辑删除状态,若有恢复的状态建议不用@LogicField注解。
        List<Article> articleList = logicDeleteVo.getIdList().stream()
                .map(id -> Article.builder()
                        .id(id)
                        .top(0)
                        .deleted(logicDeleteVo.getIsDelete())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(articleList);
    }

    @Override
    public void updateArticleTop(ArticleTopVo articleTopVo) {
        Article article = Article.builder()
                .id(articleTopVo.getId())
                .top(articleTopVo.getIsTop())
                .build();
        this.updateById(article);
    }

    @Override
    public ArticleByIdDTO getArticleById(Integer articleId) {
        // 查询文章信息
        Article article = this.getById(articleId);

        // 查询文章分类
        Category category = categoryService.getById(article.getCategoryId());

        // 查询文章标签
        List<String> tagNameList = tagMapper.listTagNameByArticleId(articleId);

        // 封装数据
        ArticleByIdDTO articleByIdDTO = new ArticleByIdDTO();
        BeanUtils.copyProperties(article, articleByIdDTO);

        if (Objects.nonNull(category)){
            articleByIdDTO.setCategoryName(category.getCategoryName());
        }

        articleByIdDTO.setTagNameList(tagNameList);
        return articleByIdDTO;
    }

    @Transactional
    @Override
    public void saveOrUpdateArticle(ArticleVo articleVo, Long userId) {

        // 保存或修改文章
        Article article = Article.builder().build();
        BeanUtils.copyProperties(articleVo, article);

        // 保存文章分类id
        Category category = saveArticleCategory(articleVo);
        if (Objects.nonNull(category)){
            article.setCategoryId(category.getId());
        }

        article.setUserId(userId.intValue());
        this.saveOrUpdate(article);
        // 保存文章标签
        saveArticleTag(articleVo, article.getId());
    }

    @Override
    public List<ArticleHomeDTO> listArticles() {
        List<ArticleHomeDTO> articleHomeDTOS = baseMapper.listArticles();

        // 获取去重后的article_id根据id取获取文章对应的tag_id
        articleHomeDTOS.forEach(item -> {

            // 为ArticleDTO的tagListDTO赋值
            ArrayList<TagDTO> tagDTOList = new ArrayList<>();

            List<ArticleTag> articleTagList = articleTagService.list(new LambdaQueryWrapper<ArticleTag>()
                    .eq(ArticleTag::getArticleId, item.getId()));
            // 查找出每一个标签的名add到数组中，并赋值给TagDTO
            articleTagList.forEach(articleTag -> {
                Tag tag = tagService.getOne(new QueryWrapper<Tag>().eq("id", articleTag.getTagId()));
                TagDTO tagDTO = TagDTO.builder()
                        .id(tag.getId())
                        .tagName(tag.getTagName())
                        .build();
                tagDTOList.add(tagDTO);
            });
            item.setTagDTOList(tagDTOList);
        });

        return articleHomeDTOS;
    }

    @Override
    public List<ArchiveDTO> listArchives() {
        List<Article> articles = baseMapper.selectList(new LambdaQueryWrapper<Article>()
                .orderByDesc(Article::getCreateTime)
                .eq(Article::getDeleted, 0)
                .eq(Article::getStatus, ArticleStatusEnum.PUBLIC.getStatus()));

        return articles.stream().map(item -> ArchiveDTO.builder()
                .id(item.getId())
                .articleTitle(item.getArticleTitle())
                .createTime(item.getCreateTime())
                .build()).collect(Collectors.toList());
    }


    @Override
    public List<ArticlePreviewDTO> listArticlesByCondition(ConditionVo condition) {
        // 查询文章
        List<ArticlePreviewDTO> articlePreviewDTOList = baseMapper.listArticleByCondition(condition);



        // 获取去重后的article_id根据id取获取文章对应的tag_id
        articlePreviewDTOList.forEach(item -> {

            // 为ArticleDTO的tagListDTO赋值
            ArrayList<TagDTO> tagDTOList = new ArrayList<>();

            List<ArticleTag> articleTagList = articleTagService.list(new LambdaQueryWrapper<ArticleTag>()
                    .eq(ArticleTag::getArticleId, item.getId()));
            // 查找出每一个标签的名add到数组中，并赋值给TagDTO
            articleTagList.forEach(articleTag -> {
                Tag tag = tagService.getOne(new QueryWrapper<Tag>().eq("id", articleTag.getTagId()));
                TagDTO tagDTO = TagDTO.builder()
                        .id(tag.getId())
                        .tagName(tag.getTagName())
                        .build();
                tagDTOList.add(tagDTO);
            });
            item.setTagDTOList(tagDTOList);
        });

        return articlePreviewDTOList;
    }

    @Override
    public ArticleFrontByIdDTO getArticleFrontByIdDTO(Integer articleId) {
        // 获取推荐的文章(按照所看文章的标签去搜索数据中的数据)
        CompletableFuture<List<ArticleRecommendDTO>> recommendArticleList = CompletableFuture
                .supplyAsync(() -> baseMapper.recommendArticleList(articleId));

        // 查询最新文章
        CompletableFuture<List<ArticleRecommendDTO>> newsArticleList = CompletableFuture
                .supplyAsync(() -> {
                    List<Article> articleList = baseMapper.selectList(new LambdaQueryWrapper<Article>()
                            .eq(Article::getDeleted, 0)
                            .eq(Article::getStatus, ArticleStatusEnum.PUBLIC.getStatus())
                            .orderByDesc(Article::getId)
                            .last("limit 5"));

                    return articleList.stream().map(item -> {
                        ArticleRecommendDTO articleRecommendDTO = new ArticleRecommendDTO();
                        articleRecommendDTO.setArticleCover(item.getArticleCover());
                        articleRecommendDTO.setArticleTitle(item.getArticleTitle());
                        articleRecommendDTO.setCreateTime(item.getCreateTime());
                        articleRecommendDTO.setId(item.getId());
                        return articleRecommendDTO;
                    }).collect(Collectors.toList());
                });

        // 查询id对应文章
        ArticleFrontByIdDTO article = baseMapper.getArticleById(articleId);
        List<ArticleTag> articleTagList = articleTagService.list(new QueryWrapper<ArticleTag>().eq("article_id", article.getId()));
        List<TagDTO> tagDTOList = articleTagList.stream().map(item -> {
            Tag tag = tagService.getById(item.getTagId());
            return TagDTO.builder()
                    .id(tag.getId())
                    .tagName(tag.getTagName())
                    .build();
        }).collect(Collectors.toList());
        article.setTagDTOList(tagDTOList);

        if (Objects.isNull(article)) {
            throw new BlogException("文章不存在");
        }

        // 更新文章浏览量
        updateArticleViewsCount(articleId);

        // 查询上一篇下一篇文章
        Article lastArticle = baseMapper.selectOne(new LambdaQueryWrapper<Article>()
                .eq(Article::getDeleted, 0)
                .eq(Article::getStatus, ArticleStatusEnum.PUBLIC.getStatus())
                .lt(Article::getId, articleId)
                .orderByDesc(Article::getId)
                .last("limit 1"));
        Article nextArticle = baseMapper.selectOne(new LambdaQueryWrapper<Article>()
                .eq(Article::getDeleted, 0)
                .eq(Article::getStatus, ArticleStatusEnum.PUBLIC.getStatus())
                .gt(Article::getId, articleId)
                .orderByAsc(Article::getId)
                .last("limit 1"));
        ArticlePaginationDTO articleLastPaginationDTO = new ArticlePaginationDTO();
        if (Objects.isNull(lastArticle)){
            article.setLastArticle(null);
        }else {
            BeanUtils.copyProperties(lastArticle, articleLastPaginationDTO);
            article.setLastArticle(articleLastPaginationDTO);
        }

        ArticlePaginationDTO articleNextPaginationDTO = new ArticlePaginationDTO();
        if (Objects.isNull(nextArticle)){
            article.setNextArticle(null);
        }else {
            BeanUtils.copyProperties(nextArticle, articleNextPaginationDTO);
            article.setNextArticle(articleNextPaginationDTO);
        }

        // 封装点赞量和浏览量
        Double score = redisUtils.zScore(RedisPrefixConst.ARTICLE_VIEWS_COUNT, articleId);
        if (Objects.nonNull(score)) {
            article.setViewsCount(score.intValue());
        }
        article.setLikeCount((Integer) redisUtils.hGet(RedisPrefixConst.ARTICLE_LIKE_COUNT, articleId.toString()));
        // 封装文章信息
        try {
            article.setRecommendArticleList(recommendArticleList.get());
            article.setNewestArticleList(newsArticleList.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return article;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveArticleLike(Integer articleId, String userId) {
        // 判断是否点赞
        String articleLikeKey = RedisPrefixConst.ARTICLE_USER_LIKE + userId;
        if (redisUtils.sIsMember(articleLikeKey, articleId)) {
            // 点过赞则删除文章id
            redisUtils.sRemove(articleLikeKey, articleId);
            // 文章点赞量-1
            redisUtils.hDecr(RedisPrefixConst.ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        } else {
            // 未点赞则增加文章id
            redisUtils.sAdd(articleLikeKey, articleId);
            // 文章点赞量+1
            redisUtils.hIncr(RedisPrefixConst.ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        }
    }

    @Override
    public List<ArticleSearchDTO> listArticlesBySearch(ConditionVo condition) {
        return searchStrategyContext.executeSearchStrategy(condition.getKeywords());
    }

    @Async
    public void updateArticleViewsCount(Integer articleId) {
        // 判断是否第一次访问，增加浏览量
        String ARTICLE_SET = "articleSet";
        Set<Integer> articleSet = (Set<Integer>) Optional.ofNullable(session.getAttribute(ARTICLE_SET)).orElse(new HashSet<>());
        if (!articleSet.contains(articleId)) {
            articleSet.add(articleId);
            session.setAttribute(ARTICLE_SET, articleSet);
            // 浏览量+1
            redisUtils.zIncr(RedisPrefixConst.ARTICLE_VIEWS_COUNT, articleId, 1D);
        }
    }

    private void saveArticleTag(ArticleVo articleVo, Integer articleId) {
        // 编辑文章则删除文章所有标签
        if (Objects.nonNull(articleVo.getId())){
            articleTagService.remove(new LambdaQueryWrapper<ArticleTag>()
                    .eq(ArticleTag::getArticleId, articleVo.getId()));
        }

        // 添加文章标签
        List<String> tagNameList = articleVo.getTagNameList();
        if (CollectionUtils.isNotEmpty(tagNameList)){
            // 查询出所有标签的id
            List<Integer> existTagIdList = tagNameList.stream().map(item -> {
                Tag tag = tagService.getOne(new LambdaQueryWrapper<Tag>()
                        .eq(Tag::getTagName, item));

                // 如果tag为空说明是enter的属性，保存进tag数据库中
                if (Objects.isNull(tag)){
                    System.out.println(item);
                    Tag newTag = Tag.builder().tagName(item).build();
                    tagService.save(newTag);
                    return newTag.getId();
                }
                return tag.getId();
            }).collect(Collectors.toList());

            // 提取标签id绑定文章
            List<ArticleTag> articleTagList = existTagIdList.stream().map(tagId ->
                    ArticleTag.builder()
                            .articleId(articleId)
                            .tagId(tagId)
                            .build()
            ).collect(Collectors.toList());

            // 重新添加文章对应的标签
            articleTagService.saveBatch(articleTagList);
        }
    }

    private Category saveArticleCategory(ArticleVo articleVO) {
        // 判断分类是否存在
        Category category = categoryService.getOne(new LambdaQueryWrapper<Category>()
                .eq(Category::getCategoryName, articleVO.getCategoryName()));

        // 如果不存在则将enter的属性添加数据库
        if (Objects.isNull(category) && !articleVO.getStatus().equals(ArticleStatusEnum.DRAFT.getStatus())) {
            category = Category.builder()
                    .categoryName(articleVO.getCategoryName())
                    .build();
            categoryService.save(category);
        }
        return category;
    }
}
