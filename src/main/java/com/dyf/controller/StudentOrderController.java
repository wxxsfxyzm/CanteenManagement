package com.dyf.controller;

import com.dyf.dto.ChosenFoodDTO;
import com.dyf.dto.OrderDTO;
import com.dyf.entity.OrderDetail;
import com.dyf.entity.StudentInfo;
import com.dyf.form.OrderForm;
import com.dyf.service.IOrderService;
import com.dyf.service.IStudentService;
import com.dyf.utils.ResultVOUtil;
import com.dyf.vo.OrderMasterVO;
import com.dyf.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public OrderMasterVO orderMaster(String studentId) {
        StudentInfo studentInfo = iStudentService.findByStudentIdUsedByAdmin(studentId);
        List<OrderDTO> orderMaster = iOrderService.findList(studentId);

        if (orderMaster == null) {
            return ResultVOUtil.queryOrderMasterFail();
        }

        return ResultVOUtil.queryOrderMasterSuccess(orderMaster, studentInfo);
    }
    
    @PostMapping(value = "/create", produces = "application/json")
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
