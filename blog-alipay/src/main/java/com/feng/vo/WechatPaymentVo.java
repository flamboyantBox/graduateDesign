package com.feng.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Mr.Feng
 * @date 10/24/2022 10:17 PM
 */
@Data
@Builder
public class WechatPaymentVo {
    /**
     * 商品标题
     */
    private String articleTitle;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 商品标题
     */
    private List<Integer> articleIdList;

    /**
     * 总金额
     */
    private BigDecimal totalFee;
}
