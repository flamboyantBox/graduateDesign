package com.feng.pojo.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
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
 * @since 2022-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_order_log")
@ApiModel(value="OrderLog对象", description="")
public class OrderLog implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "支付完成时间")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "支付金额（分）")
    private BigDecimal totalFee;

    @ApiModelProperty(value = "交易流水号")
    private String transactionId;

    @ApiModelProperty(value = "交易状态")
    private String tradeState;

    @ApiModelProperty(value = "支付类型（1：微信 2：支付宝）")
    private Integer payType;

    @ApiModelProperty(value = "其他属性")
    private String attr;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableField("is_deleted")
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime gmtModified;


}
