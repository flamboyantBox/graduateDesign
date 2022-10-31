package com.feng.client;

import com.feng.common.result.R;
import com.feng.vo.OrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value = "service-blog", path = "/order/core")
public interface PaymentClient {

    @PostMapping("createOrder")
    R saveOrder(@RequestBody OrderVo orderVo);

    @PostMapping("updateStatus")
    void updateStatus(@RequestParam Map<String, String> map, @RequestParam String articleIds);
}
