package com.feng.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Mr.Feng
 * @date 10/24/2022 6:11 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderListDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 商品id
     */
    private Integer articleId;

    /**
     * 商品名称
     */
    private String articleTitle;

    /**
     * 商品封面
     */
    private String articleCover;

    /**
     * 商品数量
     */
    private Integer articleNum;

    /**
     * 商品单价
     */
    private BigDecimal totalFee;

    /**
     * 日期
     */
    private LocalDateTime updateTime;
}
