package com.feng.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.feng.common.result.R;
import com.feng.pojo.dto.OrderListDTO;
import com.feng.pojo.entity.Order;
import com.feng.pojo.vo.OrderVo;
import com.feng.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mr.feng
 * @since 2022-10-22
 */
@RestController
@RequestMapping("/order/core")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation("创建订单")
    @PostMapping("createOrder")
    public R saveOrder(@RequestBody OrderVo orderVo){
        Integer articleNum = orderService.createOrder(orderVo);
        return R.ok().data("articleNum", articleNum);
    }

    @ApiOperation("用户订单列表")
    @GetMapping("orderList")
    public R orderList(@RequestParam Integer userId){
        List<OrderListDTO> orderList = orderService.orderList(userId);
        return R.ok().data("orderList", orderList);
    }

    @ApiOperation("用户收货列表")
    @GetMapping("receptionList")
    public R receptionList(@RequestParam Integer userId){
        List<OrderListDTO> receptionList = orderService.receptionList(userId);
        return R.ok().data("receptionList", receptionList);
    }

    @ApiOperation("删除订单列表")
    @DeleteMapping("deleteOrder")
    public R deleteOrder(@RequestParam("orderId") Integer orderId, @RequestParam("articleId") Integer articleId){
        Integer articleNum = orderService.deleteOrder(orderId, articleId);

        //获取最新articleId商品的id
        List<Order> orderList = orderService.list(new LambdaQueryWrapper<Order>()
                .eq(Order::getArticleId, articleId));

        Integer id;
        if (orderList.size() > 0){
            id = orderList.get(0).getId();
        }else {
            id = 0;
        }

        return R.ok().data("articleNum", articleNum).data("id", id);
    }

    @ApiOperation("更新订单状态")
    @PostMapping("updateStatus")
    public void updateStatus(@RequestParam Map<String, String> map, @RequestParam String articleIds){
        orderService.updateStatus(map, articleIds);
    }
}

