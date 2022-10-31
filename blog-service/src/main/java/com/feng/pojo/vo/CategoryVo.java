package com.feng.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author Mr.Feng
 * @date 8/8/2022 1:54 PM
 */

@Data
@ApiModel(description = "分类")
public class CategoryVo {
    /**
     * id
     */
    @ApiModelProperty(name = "id", value = "分类id", dataType = "Integer")
    private Integer id;

    /**
     * 分类名
     */
    @NotBlank(message = "分类名不能为空")
    @ApiModelProperty(name = "categoryName", value = "分类名", required = true, dataType = "String")
    private String categoryName;
}
