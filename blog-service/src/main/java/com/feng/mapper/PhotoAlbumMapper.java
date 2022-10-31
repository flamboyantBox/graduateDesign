package com.feng.mapper;

import com.feng.pojo.dto.PhotoAlbumDTO;
import com.feng.pojo.entity.PhotoAlbum;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feng.pojo.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 相册 Mapper 接口
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface PhotoAlbumMapper extends BaseMapper<PhotoAlbum> {

    List<PhotoAlbumDTO> photoAlbumList(@Param("condition") ConditionVo condition);
}
