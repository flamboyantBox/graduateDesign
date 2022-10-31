package com.feng.controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.feng.client.PaymentClient;
import com.feng.common.result.PaymentEnum;
import com.feng.util.AlipayUtil;
import com.feng.vo.AlipayVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Feng
 * @date 10/20/2022 2:03 PM
 */
@RestController
@RequestMapping("/alipay/core")
@CrossOrigin
public class AlipayController {
    @Autowired
    private PaymentClient paymentClient;

    private Boolean status = false;

    @ApiOperation("支付宝支付调用")
    @GetMapping("/pay")
    public String pay(AlipayVo aliPay) {
        Factory.setOptions(getOptions());
        AlipayTradePagePayResponse response;
        try {
            //  发起API调用（以创建当面付收款二维码为例）
            response = Factory.Payment.Page()
                    .pay(aliPay.getSubject(), aliPay.getTraceNo(), aliPay.getTotalAmount(), "");
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return response.getBody();
    }

    @ApiOperation("支付宝同步回调")
    @PostMapping("/notify")
    public String notify(HttpServletRequest request) {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")){
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> parameterMap = request.getParameterMap();
            for (String name :
                    parameterMap.keySet()) {
                params.put(name, request.getParameter(name));
            }

            params.put("pay_type", PaymentEnum.ALIPAY.getStatus().toString());
            String articleIds = params.get("subject");
            paymentClient.updateStatus(params, articleIds);
            this.status = true;
            return "支付成功";
        }
        return "支付失败";
    }

    @ApiOperation("查询支付状态")
    @GetMapping("/status")
    public Boolean status() {
        return status;
    }

    @ApiOperation("查询支付状态")
    @GetMapping("/changeFalse")
    public void changeFalse() {
        this.status = false;
    }

    private static Config getOptions() {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipaydev.com";
        config.signType = "RSA2";
        config.appId = AlipayUtil.APPID;
        config.merchantPrivateKey = AlipayUtil.PRIVATE_KEY;
        config.alipayPublicKey = AlipayUtil.PUBLIC_KEY;
        config.notifyUrl = AlipayUtil.NOTIFY_URL;
        return config;
    }
}
