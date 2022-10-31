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
 * 相册
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_photo_album")
@ApiModel(value="PhotoAlbum对象", description="相册")
@Builder
public class PhotoAlbum implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "相册名")
    private String albumName;

    @ApiModelProperty(value = "相册描述")
    private String albumDesc;

    @ApiModelProperty(value = "相册封面")
    private String albumCover;

    @ApiModelProperty(value = "是否删除")
    @TableField("is_delete")
    private Integer deleted;

    @ApiModelProperty(value = "状态值 1公开 2私密")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}
