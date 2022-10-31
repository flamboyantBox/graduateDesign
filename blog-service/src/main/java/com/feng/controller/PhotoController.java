package com.feng.controller;


import com.feng.common.result.R;
import com.feng.pojo.dto.PhotoDTO;
import com.feng.pojo.entity.Photo;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.DeleteVo;
import com.feng.pojo.vo.PhotoInfoVo;
import com.feng.pojo.vo.PhotoVo;
import com.feng.service.PhotoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 照片 前端控制器
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@RestController
@RequestMapping("/photo/core")
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @ApiOperation(value = "根据相册id获取照片列表")
    @GetMapping("photoList")
    public R photoList(ConditionVo condition) {
        List<PhotoDTO> photoList = photoService.listPhotos(condition);
        return R.ok().data("list", photoList);
    }

    @ApiOperation(value = "保存照片")
    @PostMapping("savePhotos")
    public R savePhotos(@Valid @RequestBody PhotoVo photoVo) {
        photoService.savePhotos(photoVo);
        return R.ok();
    }

    @ApiOperation(value = "删除照片")
    @DeleteMapping("deletePhotos")
    public R deletePhotos(@Valid @RequestBody DeleteVo deleteVo) {
        photoService.deletePhotos(deleteVo);
        return R.ok();
    }

    @ApiOperation(value = "编辑照片")
    @PutMapping("editPhoto")
    public R editPhoto(@Valid @RequestBody PhotoInfoVo photoInfoVo) {
        photoService.editPhoto(photoInfoVo);
        return R.ok();
    }
}

