package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.feng.common.exception.Assert;
import com.feng.common.exception.BlogException;
import com.feng.common.result.ResponseEnum;
import com.feng.pojo.dto.CategoryDTO;
import com.feng.pojo.entity.Article;
import com.feng.pojo.entity.Category;
import com.feng.mapper.CategoryMapper;
import com.feng.pojo.vo.CategoryVo;
import com.feng.pojo.vo.ConditionVo;
import com.feng.service.ArticleService;
import com.feng.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private ArticleService articleService;

    @Override
    public List<CategoryDTO> listCategories() {
        return baseMapper.listCategoryDTO();
    }

    @Override
    public void saveOrUpdateCategory(CategoryVo categoryVo) {
        // 判断分类名是否已经存在
        Category existCategory = baseMapper.selectOne(new QueryWrapper<Category>()
                .eq("category_name", categoryVo.getCategoryName()));
        if (Objects.nonNull(existCategory) && !existCategory.getId().equals(categoryVo.getId())){
            throw new BlogException(112, "分类已存在");
        }
        // 修改或增加业务
        Category category = Category.builder()
                .id(categoryVo.getId())
                .categoryName(categoryVo.getCategoryName())
                .build();
        this.saveOrUpdate(category);
    }

    @Override
    public void deleteCategory(List<Integer> categoryIdList) {
        // 判断分类id是否有文章，有则无法删除
        List<Article> count = articleService.list(new QueryWrapper<Article>()
                .in("category_id", categoryIdList));
        Assert.gtValue(count.size(), 0, ResponseEnum.CATEGORY_EXIST_ARTICLE);

        baseMapper.deleteBatchIds(categoryIdList);
    }

    @Override
    public List<CategoryDTO> listCategoriesBySearch(ConditionVo conditionVo) {
        return baseMapper.listCategoriesBySearch(conditionVo.getKeywords());
    }
}
