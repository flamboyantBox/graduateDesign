package com.feng.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Mr.Feng
 * @date 8/18/2022 3:01 PM
 */
@Data
@Builder
@ApiModel(description = "照片")
public class PhotoVo {
    /**
     * 相册id
     */
    @NotNull(message = "相册id不能为空")
    @ApiModelProperty(name = "id", value = "相册id", required = true, dataType = "Integer")
    private Integer albumId;

    /**
     * 照片url列表
     */
    @ApiModelProperty(name = "photoUrlList", value = "照片列表", required = true, dataType = "List<String>")
    private List<String> photoUrlList;

    /**
     * 照片id列表
     */
    @ApiModelProperty(name = "photoIdList", value = "照片id列表", required = true, dataType = "List<Integer>")
    private List<Integer> photoIdList;
}
