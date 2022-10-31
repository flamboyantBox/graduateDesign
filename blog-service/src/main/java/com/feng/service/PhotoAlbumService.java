package com.feng.service;

import com.feng.pojo.dto.PhotoAlbumDTO;
import com.feng.pojo.entity.Photo;
import com.feng.pojo.entity.PhotoAlbum;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.PhotoAlbumVo;

import java.util.List;

/**
 * <p>
 * 相册 服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface PhotoAlbumService extends IService<PhotoAlbum> {

    List<PhotoAlbumDTO> albumList(ConditionVo conditionVo);

    void saveOrUpdatePhotoAlbum(PhotoAlbumVo photoAlbumVo);

    void removePhotoAlbumById(Integer albumId);

    PhotoAlbumDTO getPhotoAlbumBackById(Integer albumId);
}
