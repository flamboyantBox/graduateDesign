package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.feng.common.exception.Assert;
import com.feng.common.exception.BlogException;
import com.feng.common.result.ResponseEnum;
import com.feng.pojo.dto.PhotoAlbumDTO;
import com.feng.pojo.entity.Photo;
import com.feng.pojo.entity.PhotoAlbum;
import com.feng.mapper.PhotoAlbumMapper;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.PhotoAlbumVo;
import com.feng.service.PhotoAlbumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.service.PhotoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 相册 服务实现类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@Service
public class PhotoAlbumServiceImpl extends ServiceImpl<PhotoAlbumMapper, PhotoAlbum> implements PhotoAlbumService {
    @Autowired
    private PhotoService photoService;

    @Override
    public List<PhotoAlbumDTO> albumList(ConditionVo conditionVo) {
        return baseMapper.photoAlbumList(conditionVo);
    }

    @Override
    public void saveOrUpdatePhotoAlbum(PhotoAlbumVo photoAlbumVo) {
        // 查询相册名是否存在
        PhotoAlbum album = this.getOne(new LambdaQueryWrapper<PhotoAlbum>()
                .eq(PhotoAlbum::getAlbumName, photoAlbumVo.getAlbumName()));

        if (Objects.nonNull(album) && !album.getId().equals(photoAlbumVo.getId())){
            throw new BlogException(ResponseEnum.EXIST_ALBUM);
        }

        PhotoAlbum photoAlbum = PhotoAlbum.builder().build();
        BeanUtils.copyProperties(photoAlbumVo, photoAlbum);
        this.saveOrUpdate(photoAlbum);
    }

    @Override
    public void removePhotoAlbumById(Integer albumId) {
        // 查询该相册下是否存在照片
        int count = photoService.count(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId));

        if (count > 0){
            // 若相册下存在照片则逻辑删除相册和照片
            this.updateById(PhotoAlbum.builder()
                    .id(albumId)
                    .deleted(1)
                    .build());
            photoService.update(Photo.builder().build(), new LambdaUpdateWrapper<Photo>()
                    .set(Photo::getDeleted, 1)
                    .eq(Photo::getAlbumId, albumId));
        }else {
            // 若没有则直接删除该相册
            this.removeById(albumId);
        }
    }

    @Override
    public PhotoAlbumDTO getPhotoAlbumBackById(Integer albumId) {
        // 查询相册信息
        PhotoAlbum album = this.getById(albumId);

        // 查询照片数量
        int count = photoService.count(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId)
                .eq(Photo::getDeleted, 0));

        PhotoAlbumDTO photoAlbumDTO = new PhotoAlbumDTO();
        BeanUtils.copyProperties(album, photoAlbumDTO);
        photoAlbumDTO.setPhotoCount(count);
        return photoAlbumDTO;
    }
}
