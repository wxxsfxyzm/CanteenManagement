package com.dyf.service;

import com.dyf.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService
{
    /**  创建订单  */
    OrderDTO create(OrderDTO orderDTO);

    /**  查询单个订单  */
    OrderDTO  findById(String orderId);

    /**  查询某个用户的订单列表 */
    List<OrderDTO> findList(String studentId);
    Page<OrderDTO> findList(String studentId, Pageable pageable);

    /**  带分页查询所有的订单列表  */
    Page<OrderDTO> findList(Pageable pageable);

}
