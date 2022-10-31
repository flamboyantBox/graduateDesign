package com.feng.controller;


import com.feng.common.result.R;
import com.feng.pojo.dto.PhotoAlbumDTO;
import com.feng.pojo.entity.Photo;
import com.feng.pojo.entity.PhotoAlbum;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.PhotoAlbumVo;
import com.feng.service.PhotoAlbumService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 相册 前端控制器
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@RestController
@RequestMapping("/photoAlbum/core")
public class PhotoAlbumController {

    @Autowired
    private PhotoAlbumService photoAlbumService;

    @ApiOperation(value = "相册列表")
    @PostMapping("albumList")
    public R albumList(@RequestBody ConditionVo condition){
        List<PhotoAlbumDTO> photoAlbumDTOList = photoAlbumService.albumList(condition);
        return R.ok().data("list", photoAlbumDTOList);
    }

    @ApiOperation(value = "保存或更新相册")
    @PostMapping("saveOrUpdatePhotoAlbum")
    public R saveOrUpdatePhotoAlbum(@Valid @RequestBody PhotoAlbumVo photoAlbumVo) {
        photoAlbumService.saveOrUpdatePhotoAlbum(photoAlbumVo);
        return R.ok();
    }

    @ApiOperation(value = "根据id删除相册")
    @ApiImplicitParam(name = "albumId", value = "相册id", required = true, dataType = "Integer")
    @DeleteMapping("/removePhotoAlbumById/{albumId}")
    public R removePhotoAlbumById(@PathVariable("albumId") Integer albumId) {
        photoAlbumService.removePhotoAlbumById(albumId);
        return R.ok();
    }

    @ApiOperation(value = "根据id获取后台相册信息")
    @ApiImplicitParam(name = "albumId", value = "相册id", required = true, dataType = "Integer")
    @GetMapping("getPhotoAlbumBackById/{albumId}/info")
    public R getPhotoAlbumBackById(@PathVariable("albumId") Integer albumId) {
        PhotoAlbumDTO  photoAlbumDTO = photoAlbumService.getPhotoAlbumBackById(albumId);
        return R.ok().data("albumInfo", photoAlbumDTO);
    }
}

