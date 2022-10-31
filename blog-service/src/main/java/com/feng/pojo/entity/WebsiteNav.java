package com.feng.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 网站导航
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_website_nav")
@ApiModel(value="WebsiteNav对象", description="网站导航")
public class WebsiteNav implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "网站名称")
    private String websiteName;

    private String websiteUrl;

    @ApiModelProperty(value = "网站图标")
    private String websiteIcon;

    @ApiModelProperty(value = "标签id")
    private Integer tagsId;

    @ApiModelProperty(value = "网站描述")
    private String websiteDesc;

    @ApiModelProperty(value = "网站排序")
    private Integer orderNum;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
