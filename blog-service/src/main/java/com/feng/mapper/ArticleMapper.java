package com.feng.mapper;

import com.feng.pojo.dto.*;
import com.feng.pojo.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feng.pojo.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface ArticleMapper extends BaseMapper<Article> {
    List<ArticleDTO> listArticleBacks(@Param("condition") ConditionVo conditionVo);

    List<ArticleHomeDTO> listArticles();

    List<ArticlePreviewDTO> listArticleByCondition(@Param("condition") ConditionVo condition);

    List<ArticleRecommendDTO>  recommendArticleList(@Param("articleId") Integer articleId);

    ArticleFrontByIdDTO getArticleById(@Param("articleId") Integer articleId);
}
