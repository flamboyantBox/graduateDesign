package com.feng.controller;


import com.feng.common.result.R;
import com.feng.common.util.JwtUtils;
import com.feng.pojo.dto.*;
import com.feng.pojo.vo.*;
import com.feng.service.ArticleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@RestController
@RequestMapping("/article/core")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "查看后台文章")
    @PostMapping("articleList")
    public R articleList(@RequestBody ConditionVo conditionVo) {
        List<ArticleDTO> articleDTOList = articleService.articleList(conditionVo);
        return R.ok().data("list", articleDTOList);
    }

    @ApiOperation(value = "根据目录和标签查询文章")
    @GetMapping("/articles/condition")
    public R listArticlesByCondition(ConditionVo condition) {
        List<ArticlePreviewDTO> articlePreviewDTOList = articleService.listArticlesByCondition(condition);
        return R.ok().data("list", articlePreviewDTOList);
    }

    @ApiOperation(value = "查看文章归档")
    @GetMapping("articles/archives")
    public R listArchives() {
        List<ArchiveDTO> archiveDTOList = articleService.listArchives();
        return R.ok().data("list", archiveDTOList);
    }

    @ApiOperation(value = "查看首页文章")
    @GetMapping("articleList")
    public R listArticles() {
        List<ArticleHomeDTO> articleHomeDTOList = articleService.listArticles();
        return R.ok().data("list", articleHomeDTOList);
    }

    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @GetMapping("/articleList/{articleId}")
    public R getArticleById(@PathVariable("articleId") Integer articleId) {
        ArticleByIdDTO articleByIdDTO = articleService.getArticleById(articleId);
        return R.ok().data("article", articleByIdDTO);
    }

    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @GetMapping("/articleFrontList/{articleId}")
    public R getArticleFrontById(@PathVariable("articleId") Integer articleId) {
        ArticleFrontByIdDTO articleFrontByIdDTO = articleService.getArticleFrontByIdDTO(articleId);
        return R.ok().data("article", articleFrontByIdDTO);
    }

    @ApiOperation(value = "添加或修改文章")
    @PostMapping("/saveOrUpdateArticle")
    public R saveOrUpdateArticle(@Valid @RequestBody ArticleVo articleVo, HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        articleService.saveOrUpdateArticle(articleVo, userId);
        return R.ok();
    }

    @ApiOperation(value = "恢复或删除文章")
    @PostMapping("recoverOrDeleteArticle")
    public R recoverOrDeleteArticle(@RequestBody DeleteVo logicDeleteVo){
        articleService.recoverOrDeleteArticle(logicDeleteVo);
        return R.ok();
    }

    @ApiOperation(value = "修改文章置顶")
    @PutMapping("changeTop")
    public R updateArticleTop(@Valid @RequestBody ArticleTopVo articleTopVo) {
        articleService.updateArticleTop(articleTopVo);
        return R.ok();
    }

    @ApiOperation(value = "文章点赞")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @PostMapping("{userId}/{articleId}/like")
    public R saveArticleLike(@PathVariable("articleId") Integer articleId, @PathVariable("userId") String userId) {
        articleService.saveArticleLike(articleId, userId);
        return R.ok();
    }

    @ApiOperation(value = "搜索文章")
    @GetMapping("/asearch")
    public R listArticlesBySearch(ConditionVo condition) {
        List<ArticleSearchDTO> articleSearchDTOList =  articleService.listArticlesBySearch(condition);
        return R.ok().data("data", articleSearchDTOList);
    }
}

