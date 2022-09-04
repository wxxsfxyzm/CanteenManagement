package com.dyf.dao;

import com.dyf.entity.FoodInfo;
import com.dyf.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrdersDAO extends JpaRepository<Orders, String> {

    Page<Orders> findByUserId(String oderId, Pageable pageable);

    List<FoodInfo> findByOrderId(String orderId);

    List<Orders> findByUserId(String orderId);


}
