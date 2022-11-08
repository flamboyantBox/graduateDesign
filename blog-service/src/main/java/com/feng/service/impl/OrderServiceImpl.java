package com.feng.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.feng.common.util.BeanCopyUtils;
import com.feng.common.util.OrderNoUtil;
import com.feng.pojo.dto.OrderBackListDTO;
import com.feng.pojo.dto.OrderListDTO;
import com.feng.pojo.entity.Article;
import com.feng.pojo.entity.Order;
import com.feng.mapper.OrderMapper;
import com.feng.pojo.entity.OrderLog;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.DeleteVo;
import com.feng.pojo.vo.OrderVo;
import com.feng.service.OrderLogService;
import com.feng.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-10-22
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderLogService orderLogService;

    @Override
    public Integer createOrder(OrderVo orderVo) {
        Order order = Order.builder()
                .articleId(orderVo.getArticleId())
                .articleTitle(orderVo.getArticleTitle())
                .articleCover(orderVo.getArticleCover())
                .userId(orderVo.getUserId())
                .nickname(orderVo.getNickname())
                .email(orderVo.getEmail())
                .totalFee(orderVo.getTotalFee())
                .payType(null)
                .build();

        order.setStatus(0);
        order.setOrderNo(OrderNoUtil.getOrderNo());
        this.save(order);

        // 查询共有articleId的数量

        return this.count(new LambdaQueryWrapper<Order>().eq(Order::getArticleId, orderVo.getArticleId()));
    }

    @Override
    public List<OrderListDTO> orderList(Integer userId) {
        return baseMapper.orderList(userId);
    }

    @Override
    public Integer deleteOrder(Integer orderId, Integer articleId) {
        // 删除一个订单
        this.removeById(orderId);

        // 查询剩余articleId的数量
        return this.count(new LambdaQueryWrapper<Order>().eq(Order::getArticleId, articleId));
    }

    @Override
    public void updateStatus(Map<String, String> map, String articleIds) {
        // 更改订单信息表
        String[] ids = articleIds.split(",");
        for (String id : ids) {
            List<Order> orderList = this.list(new LambdaQueryWrapper<Order>()
                    .eq(Order::getDeleted, 0)
                    .eq(Order::getStatus, 0)
                    .eq(Order::getArticleId, Integer.valueOf(id)));
            for (Order order : orderList) {
                if (order.getStatus() == 1) continue;
                order.setPayType(Integer.valueOf(map.get("pay_type")));
                order.setStatus(1);
                this.updateById(order);

                // 创建订单日志表记录支付日志并判断是微信支付还是支付宝支付
                OrderLog payLog = new OrderLog();
                payLog.setOrderNo(order.getOrderNo());
                payLog.setPayTime(LocalDateTime.now());
                payLog.setPayType(Integer.valueOf(map.get("pay_type")));//支付类型
                payLog.setTotalFee(order.getTotalFee());//总金额(分)
                payLog.setAttr(JSONObject.toJSONString(map));

                if (map.get("pay_type").equals("1")) {
                    payLog.setTradeState(map.get("trade_state"));//支付状态
                    payLog.setTransactionId(map.get("transaction_id"));
                } else {
                    payLog.setTradeState(map.get("trade_status"));//支付状态
                    payLog.setTransactionId(map.get("seller_id"));
                }
                orderLogService.save(payLog);
            }
        }
    }

    @Override
    public List<OrderListDTO> receptionList(Integer userId) {
        return baseMapper.receptionList(userId);
    }

    @Override
    public List<OrderBackListDTO> orderBackList(ConditionVo conditionVo) {
        return baseMapper.orderBackList(conditionVo);
    }

    @Override
    public void deleteBackOrder(DeleteVo logicDeleteVo) {
        // 修改订单逻辑删除状态
        List<Order> orderList = logicDeleteVo.getIdList().stream()
                .map(id -> Order.builder()
                        .id(id)
                        .deleted(logicDeleteVo.getIsDelete())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(orderList);
    }
}
