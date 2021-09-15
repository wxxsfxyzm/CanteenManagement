package com.dyf.service.impl;

import com.dyf.convert.OrderMaster2OrderDTOConverter;
import com.dyf.dto.OrderDTO;
import com.dyf.dto.StudentDTO;
import com.dyf.entity.OrderDetail;
import com.dyf.entity.OrderMaster;
import com.dyf.service.IOrderService;
import com.dyf.service.IStudentService;
import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest
{
    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IStudentService iStudentService;

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("1");
        orderDTO.setStudentId("195080703");
        orderDTO.setOrderAmount(BigDecimal.ONE);

        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        OrderDetail o1 = new OrderDetail();
        OrderDetail o2 = new OrderDetail();
        o1.setFoodId("16309072839586634");
        o1.setQuantity(1);

        o2.setFoodId("16309137214122841");
        o2.setQuantity(2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);


        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = iOrderService.create(orderDTO);

        Assert.assertNotNull(result);
    }

    @Test
    public void findById() {
        OrderDTO orderDTO = iOrderService.findById("1630648691339533584");
        log.info("[查询单个订单] orderDTO={}", orderDTO);
        Assert.assertEquals("1630648691339533584", orderDTO.getOrderId());

    }

    @Test
    public void findList() {
        //PageRequest request = PageRequest.of(0,2);
        List<OrderDTO> orderDTOList = iOrderService.findList("1001");

        Assert.assertNotEquals(0,orderDTOList.size());
    }
    @Test
    public void findList02() {
        // 查询第0页的数据 每页显示2条数据
        PageRequest request = PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage = iOrderService.findList("1001", request);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
    }

    @Test
    public  void finish() {
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = iOrderService.findById("1630648691339533584");

        //iOrderService.paid(orderDTO);

        StudentDTO studentDTO = iStudentService.findByStudentId("1001");

        Assert.assertNotNull(studentDTO);
    }

    @Test
    public void testFindList() {
    }
}