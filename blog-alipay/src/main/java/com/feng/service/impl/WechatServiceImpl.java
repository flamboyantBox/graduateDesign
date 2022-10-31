package com.feng.service.impl;

import com.feng.client.PaymentClient;
import com.feng.common.util.HttpClient;
import com.feng.service.WechatService;
import com.feng.util.WechatUtil;
import com.feng.vo.WechatPaymentVo;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Feng
 * @date 10/24/2022 10:57 PM
 */
@Service
public class WechatServiceImpl implements WechatService {
    @Autowired
    private PaymentClient paymentClient;

    @Override
    public Map scanQR(WechatPaymentVo wechatPaymentVo) {
        try {
            //2.使用map设置生成二维码需要的参数
            Map m = new HashMap<>();
            //设置支付参数
            m.put("appid", WechatUtil.APPID);
            m.put("mch_id", WechatUtil.PARTNER);
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", wechatPaymentVo.getArticleTitle());
            m.put("out_trade_no", wechatPaymentVo.getOrderNo());
            m.put("total_fee", wechatPaymentVo.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", WechatUtil.NOTIFY_URL);
            m.put("trade_type", "NATIVE");

            System.out.println("******map :" + m);
            //3.发送httpclient请求，传递参数
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");

            client.setXmlParam(WXPayUtil.generateSignedXml(m, WechatUtil.PARTNER_KEY));
            client.setHttps(true);
            //执行请求发送
            client.post();

            //4.得到发送请求返回结果
            //返回的内容是xml格式
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //封装返回结果集
            Map map = new HashMap<>();
            map.put("out_trade_no", wechatPaymentVo.getOrderNo());
            map.put("article_id", wechatPaymentVo.getArticleIdList().get(0));
            map.put("total_fee", wechatPaymentVo.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));//返回二维码状态码
            map.put("code_url", resultMap.get("code_url"));//二维码地址

            System.out.println("******map :" + map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, String> orderStatus(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", WechatUtil.APPID);
            m.put("mch_id", WechatUtil.PARTNER);
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2.设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, WechatUtil.PARTNER_KEY));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();

            return WXPayUtil.xmlToMap(xml);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}