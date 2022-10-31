package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.feng.pojo.dto.PhotoDTO;
import com.feng.pojo.entity.Photo;
import com.feng.mapper.PhotoMapper;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.DeleteVo;
import com.feng.pojo.vo.PhotoInfoVo;
import com.feng.pojo.vo.PhotoVo;
import com.feng.service.PhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 照片 服务实现类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements PhotoService {

    @Override
    public List<PhotoDTO> listPhotos(ConditionVo condition) {
        // 查询照片列表
        List<Photo> photoList = this.list(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, condition.getAlbumId())
                .eq(Photo::getDeleted, condition.getIsDelete())
                .orderByDesc(Photo::getId)
                .orderByDesc(Photo::getUpdateTime));

        // 赋值给PhotoDTO
        return photoList.stream().map(item -> {
            PhotoDTO photoDTO = new PhotoDTO();
            BeanUtils.copyProperties(item, photoDTO);
            return photoDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public void savePhotos(PhotoVo photoVo) {
        // 遍历每一个照片的url地址，实例化photo。
        List<Photo> photoList = photoVo.getPhotoUrlList().stream().map(item ->
                Photo.builder()
                        .albumId(photoVo.getAlbumId())
                        .photoSrc(item)
                        .photoName(IdWorker.getIdStr())
                        .build()
        ).collect(Collectors.toList());
        this.saveBatch(photoList);
    }

    @Override
    public void deletePhotos(DeleteVo deleteVo) {
        // 更新照片状态
        List<Photo> photoList = deleteVo.getIdList().stream().map(item -> Photo.builder()
                .id(item)
                .deleted(deleteVo.getIsDelete())
                .build()
        ).collect(Collectors.toList());

        this.updateBatchById(photoList);
    }

    @Override
    public void editPhoto(PhotoInfoVo photoInfoVo) {
        this.update(new LambdaUpdateWrapper<Photo>()
                .set(Photo::getPhotoDesc, photoInfoVo.getPhotoDesc())
                .set(Photo::getPhotoName, photoInfoVo.getPhotoName())
                .eq(Photo::getId, photoInfoVo.getId()));
    }
}
