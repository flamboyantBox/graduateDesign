package com.feng.service;

import com.feng.pojo.dto.TagDTO;
import com.feng.pojo.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.pojo.vo.CategoryVo;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.TagVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface TagService extends IService<Tag> {

    List<TagDTO> listTags();

    List<TagDTO> listTagBySearch(ConditionVo conditionVo);

    void saveOrUpdateCategory(TagVo tagVo);

    void deleteCategory(List<Integer> tagIdList);

}
