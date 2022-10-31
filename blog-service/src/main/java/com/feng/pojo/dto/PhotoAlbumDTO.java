package com.feng.pojo.dto;

import lombok.Data;

/**
 * @author Mr.Feng
 * @date 8/16/2022 10:06 AM
 */
@Data
public class PhotoAlbumDTO {
    /**
     * 相册id
     */
    private Integer id;

    /**
     * 相册名
     */
    private String albumName;

    /**
     * 相册描述
     */
    private String albumDesc;

    /**
     * 相册封面
     */
    private String albumCover;

    /**
     * 照片数量
     */
    private Integer photoCount;

    /**
     * 状态值 1公开 2私密
     */
    private Integer status;
}
