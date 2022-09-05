package com.dyf.service;

import com.dyf.dto.Order2DTO;
import com.dyf.dto.OrderDTO;
import com.dyf.entity.FoodInfo;
import com.dyf.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService
{
    /**  创建订单  */
    OrderDTO create(OrderDTO orderDTO);

    OrderDTO create(Order2DTO order2DTO);

    /**  查询单个订单  */
    OrderDTO  findById(String orderId);

    List<Orders> findListByUserId(String userId);


    /**  查询某个用户的订单列表 */
    List<OrderDTO> findList(String studentId);
    Page<OrderDTO> findList(String studentId, Pageable pageable);

    /**  带分页查询所有的订单列表  */
    Page<OrderDTO> findList(Pageable pageable);

}
