package com.feng.mapper;

import com.feng.pojo.dto.OrderBackListDTO;
import com.feng.pojo.dto.OrderListDTO;
import com.feng.pojo.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feng.pojo.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mr.feng
 * @since 2022-10-22
 */
public interface OrderMapper extends BaseMapper<Order> {

    List<OrderListDTO> orderList(@Param("userId") Integer userId);

    List<OrderListDTO> receptionList(@Param("userId") Integer userId);

    List<OrderBackListDTO> orderBackList(@Param("condition") ConditionVo condition);
}
