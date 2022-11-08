package com.feng.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Mr.Feng
 * @date 11/8/2022 1:03 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderBackListDTO {

    /**
     * 订单编号
     */
    private Integer id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 商品名称
     */
    private String articleTitle;

    /**
     * 商品单价
     */
    private BigDecimal totalFee;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 支付类型
     */
    private Integer payType;

    /**
     * 支付状态
     */
    private Integer status;

    /**
     * 交易流水号
     */
    private String transactionId;

    /**
     * 交易时间
     */
    private LocalDateTime createTime;
}
