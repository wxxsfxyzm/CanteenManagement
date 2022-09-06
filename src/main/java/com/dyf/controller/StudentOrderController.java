package com.dyf.controller;

import com.dyf.annotation.JwtAnnotation;
import com.dyf.dto.ChosenFoodDTO;
import com.dyf.dto.Order2DTO;
import com.dyf.dto.OrderDTO;
import com.dyf.entity.OrderDetail;
import com.dyf.entity.Orders;
import com.dyf.entity.StudentInfo;
import com.dyf.exception.SellException;
import com.dyf.form.OrderForm;
import com.dyf.service.IOrderService;
import com.dyf.service.IStudentService;
import com.dyf.utils.ResultVOUtil;
import com.dyf.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dyf.enums.ResultEnum.ILLEGAL_REQUEST;
import static com.dyf.enums.ResultEnum.ORDER_CREATE_SUCCESS;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/student/order")
public class StudentOrderController {
    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IStudentService iStudentService;


    @PostMapping(value = "/orderMaster", produces = "application/json")
    @JwtAnnotation.PassToken
    public ResultVO orderMaster(@RequestParam(value = "studentId") String phoneNumber) {
        StudentInfo studentInfo = iStudentService.findByStudentPhoneUsedByAdmin(phoneNumber);
        List<Orders> ordersList = iOrderService.findListByUserId(studentInfo.getId());

        if (ordersList == null) {
            return ResultVOUtil.success(200, "订单为空", null);
        }

        Map<String, List<Orders>> listMap = ordersList.stream().collect(Collectors.groupingBy(t -> t.getOrderId()));


        return ResultVOUtil.success(200, "查询订单成功", listMap);
    }

    @PostMapping(value = "/finish", produces = "application/json")
    @JwtAnnotation.PassToken
    public ResultVO finish(@RequestParam(value = "orderId") String orderId) {
        List<Orders> ordersList = iOrderService.findListByOrderId(orderId);
        if (ordersList == null) {
            // 不能把 status 1 改成 1
            throw new SellException(ILLEGAL_REQUEST);
        }
        // 把查出来的订单 status 改为 1
        Order2DTO order2DTO = new Order2DTO();
        for (Orders singleList : ordersList) {
            singleList.setGoodsStatus(1);
            BeanUtils.copyProperties(singleList, order2DTO);
            // 提交更改
            iOrderService.create(order2DTO);
        }

        return ResultVOUtil.success(200, "已经使用", ordersList);
    }


    @PostMapping(value = "/create", produces = "application/json")
    @JwtAnnotation.PassToken
    public ResultVO create(@RequestBody OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();

        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();

        for (ChosenFoodDTO chosenFoodDTO : orderForm.getChosenFoodDTOList()) {
            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setFoodId(chosenFoodDTO.getFoodId());
            orderDetail.setQuantity(chosenFoodDTO.getFoodQuantity());
            orderDetailList.add(orderDetail);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        orderDTO.setStudentId(orderForm.getStudentId());

        return ResultVOUtil.success(ORDER_CREATE_SUCCESS.getMessage(), iOrderService.create(orderDTO));
    }
}
