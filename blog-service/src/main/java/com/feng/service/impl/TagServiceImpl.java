package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.feng.common.exception.Assert;
import com.feng.common.exception.BlogException;
import com.feng.common.result.ResponseEnum;
import com.feng.pojo.dto.TagDTO;
import com.feng.pojo.entity.Article;
import com.feng.pojo.entity.ArticleTag;
import com.feng.pojo.entity.Category;
import com.feng.pojo.entity.Tag;
import com.feng.mapper.TagMapper;
import com.feng.pojo.vo.CategoryVo;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.TagVo;
import com.feng.service.ArticleService;
import com.feng.service.ArticleTagService;
import com.feng.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public List<TagDTO> listTags() {
        return baseMapper.listTagDTO();
    }

    @Override
    public List<TagDTO> listTagBySearch(ConditionVo conditionVo) {
        return baseMapper.listTagBySearch(conditionVo.getKeywords());
    }

    @Override
    public void saveOrUpdateCategory(TagVo tagVo) {
        // 查询标签名是否存在
        Tag existTag = baseMapper.selectOne(new QueryWrapper<Tag>()
                .eq("tag_name", tagVo.getTagName()));

        if (Objects.nonNull(existTag) && !existTag.getId().equals(tagVo.getId())) {
            throw new BlogException(114, "标签名已存在");
        }

        // 业务逻辑实现
        Tag tag = Tag.builder()
                .id(tagVo.getId())
                .tagName(tagVo.getTagName())
                .build();
        this.saveOrUpdate(tag);
    }

    @Override
    public void deleteCategory(List<Integer> tagIdList) {
        // 判断标签分类id是否有文章，有则无法删除
        List<ArticleTag> count = articleTagService.list(new QueryWrapper<ArticleTag>()
                .in("tag_id", tagIdList));
        Assert.gtValue(count.size(), 0, ResponseEnum.TAG_EXIST_ARTICLE);

        baseMapper.deleteBatchIds(tagIdList);
    }
}
