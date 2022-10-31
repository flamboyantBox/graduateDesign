package com.feng.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Mr.Feng
 * @date 10/22/2022 9:03 PM
 */
@Data
public class OrderVo {
    /**
     * 订单id
     */
    private Integer id;

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
     * 用户id
     */
    private Integer userId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 订单金额
     */
    private BigDecimal totalFee;

    /**
     * 支付方式
     */
    private Integer payType;
}
