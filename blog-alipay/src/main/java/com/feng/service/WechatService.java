package com.feng.service;

import com.feng.vo.WechatPaymentVo;

import java.util.Map;

public interface WechatService{
    Map scanQR(WechatPaymentVo wechatPaymentVo);

    Map<String, String> orderStatus(String orderNo);
}
