package com.feng.service;

import com.feng.pojo.dto.*;
import com.feng.pojo.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.pojo.vo.*;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface ArticleService extends IService<Article> {

    List<ArticleDTO> articleList(ConditionVo conditionVo);

    void recoverOrDeleteArticle(DeleteVo logicDeleteVo);

    void updateArticleTop(ArticleTopVo articleTopVo);

    ArticleByIdDTO getArticleById(Integer articleId);

    void saveOrUpdateArticle(ArticleVo articleVo, Long userId);

    List<ArticleHomeDTO> listArticles();

    List<ArchiveDTO> listArchives();

    List<ArticlePreviewDTO> listArticlesByCondition(ConditionVo condition);

    ArticleFrontByIdDTO getArticleFrontByIdDTO(Integer articleId);

    void saveArticleLike(Integer articleId, String userId);
}
