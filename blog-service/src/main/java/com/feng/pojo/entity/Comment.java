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
 * 
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@TableName("tb_comment")
@ApiModel(value="Comment对象", description="")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "评论用户Id")
    private Integer userId;

    @ApiModelProperty(value = "评论文章id")
    private Integer articleId;

    @ApiModelProperty(value = "评论内容")
    private String commentContent;

    @ApiModelProperty(value = "回复用户id")
    private Integer replyUserId;

    @ApiModelProperty(value = "父评论id")
    private Integer parentId;

    @ApiModelProperty(value = "是否删除  0否 1是")
    @TableField("is_delete")
    @TableLogic
    private Boolean delete;

    @ApiModelProperty(value = "是否审核")
    @TableField("is_review")
    private Boolean review;

    @ApiModelProperty(value = "评论时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
