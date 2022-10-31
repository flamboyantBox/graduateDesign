package com.feng.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 照片
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("tb_photo")
@ApiModel(value="Photo对象", description="照片")
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "相册id")
    private Integer albumId;

    @ApiModelProperty(value = "照片名")
    private String photoName;

    @ApiModelProperty(value = "照片描述")
    private String photoDesc;

    @ApiModelProperty(value = "照片地址")
    private String photoSrc;

    @ApiModelProperty(value = "是否删除")
    @TableField("is_delete")
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
