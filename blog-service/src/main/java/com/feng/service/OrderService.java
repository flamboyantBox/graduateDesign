package com.feng.service;

import com.feng.pojo.dto.OrderListDTO;
import com.feng.pojo.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.pojo.vo.OrderVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-10-22
 */
public interface OrderService extends IService<Order> {

    Integer createOrder(OrderVo orderVo);

    List<OrderListDTO> orderList(Integer userId);

    Integer deleteOrder(Integer orderId, Integer articleId);

    void updateStatus(Map<String, String> map, String articleIds);

    List<OrderListDTO> receptionList(Integer userId);
}
