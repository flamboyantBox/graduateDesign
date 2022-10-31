package com.feng.pojo.dto;

import lombok.Data;

/**
 * @author Mr.Feng
 * @date 8/18/2022 1:41 PM
 */
@Data
public class PhotoDTO {
    /**
     * 照片id
     */
    private Integer id;

    /**
     * 照片名
     */
    private String photoName;

    /**
     * 照片描述
     */
    private String photoDesc;

    /**
     * 照片地址
     */
    private String photoSrc;

}
