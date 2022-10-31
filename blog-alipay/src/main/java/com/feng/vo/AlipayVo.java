package com.feng.vo;

import lombok.Data;

/**
 * @author Mr.Feng
 * @date 10/20/2022 2:09 PM
 */
@Data
public class AlipayVo {
    /**
     * 商品ids
     */
    private String subject;

    /**
     * 商品编号
     */
    private String traceNo;

    /**
     * 商品金额
     */
    private String totalAmount;
}
