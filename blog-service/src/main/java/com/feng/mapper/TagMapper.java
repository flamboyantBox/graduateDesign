package com.feng.mapper;

import com.feng.pojo.dto.TagDTO;
import com.feng.pojo.entity.Tag;
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
public interface TagMapper extends BaseMapper<Tag> {

    List<TagDTO> listTagDTO();

    List<TagDTO> listTagBySearch(@Param("condition") String condition);

    List<String> listTagNameByArticleId(Integer articleId);
}
