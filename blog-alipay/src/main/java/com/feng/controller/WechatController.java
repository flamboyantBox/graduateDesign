package com.feng.controller;

import com.feng.client.PaymentClient;
import com.feng.common.result.PaymentEnum;
import com.feng.common.result.R;
import com.feng.service.WechatService;
import com.feng.vo.OrderVo;
import com.feng.vo.WechatPaymentVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Feng
 * @date 10/22/2022 9:01 PM
 */
@RestController
@RequestMapping("/wechat/core")
@CrossOrigin
public class WechatController {
    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private WechatService wechatService;

    @ApiOperation("创建订单")
    @PostMapping("createOrder")
    public R createOrder(@RequestBody OrderVo orderVo){
        return paymentClient.saveOrder(orderVo);
    }

    @ApiOperation("传递订单信息转为QR")
    @PostMapping("scanQR")
    public R scanQR(@RequestBody() WechatPaymentVo wechatPaymentVo){
        Map map = wechatService.scanQR(wechatPaymentVo);
        return R.ok().data("map", map);
    }

    @ApiOperation("查询订单支付状态")
    @PostMapping("orderStatus")
    public R orderStatus(@RequestParam("orderNo") String orderNo,
                         @RequestParam("articleIds") String articleIds){
        Map<String, String> map = wechatService.orderStatus(orderNo);
        if (map.get("trade_state").equals("SUCCESS")){
            //添加记录到支付表,更新订单表的状态
            map.put("pay_type", PaymentEnum.WECHAT.getStatus().toString());
            paymentClient.updateStatus(map, articleIds);
            return R.ok().message("支付成功");
        }
        return R.error().message("正在支付中");
    }
}
