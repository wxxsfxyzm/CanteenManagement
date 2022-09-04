package com.dyf.dao;

import com.dyf.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderDetailDAO extends JpaRepository<OrderDetail, String>
{
    /** 根据订单编号 查询相关订单信息 */
    List<OrderDetail> findByOrderId(String orderId);
}
