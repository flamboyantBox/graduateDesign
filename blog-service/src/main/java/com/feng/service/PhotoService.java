package com.feng.service;

import com.feng.pojo.dto.PhotoDTO;
import com.feng.pojo.entity.Photo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.DeleteVo;
import com.feng.pojo.vo.PhotoInfoVo;
import com.feng.pojo.vo.PhotoVo;

import java.util.List;

/**
 * <p>
 * 照片 服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface PhotoService extends IService<Photo> {

    List<PhotoDTO> listPhotos(ConditionVo condition);

    void savePhotos(PhotoVo photoVo);

    void deletePhotos(DeleteVo deleteVo);

    void editPhoto(PhotoInfoVo photoInfoVo);
}
