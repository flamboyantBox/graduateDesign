package com.feng.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("tb_resource")
@ApiModel(value="Resource对象", description="")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "资源名")
    private String resourceName;

    @ApiModelProperty(value = "权限路径")
    private String url;

    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "父权限id")
    private Integer parentId;

    @ApiModelProperty(value = "是否匿名访问 0否 1是")
    @TableField("is_anonymous")
    private Boolean anonymous;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;


}
