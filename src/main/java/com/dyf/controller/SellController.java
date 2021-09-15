package com.dyf.controller;

import com.dyf.dto.ChosenFoodDTO;
import com.dyf.dto.OrderDTO;
import com.dyf.dto.StudentDTO;
import com.dyf.entity.FoodInfo;
import com.dyf.entity.OrderDetail;
import com.dyf.entity.StudentInfo;
import com.dyf.enums.ResultEnum;
import com.dyf.exception.SellException;
import com.dyf.form.OrderForm;
import com.dyf.service.IFoodInfoService;
import com.dyf.service.IOrderService;
import com.dyf.service.IStudentService;
import com.dyf.utils.ResultVOUtil;
import com.dyf.vo.FoodInfoVO;
import com.dyf.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sell")
public class SellController
{
    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IFoodInfoService iFoodInfoService;

    @Autowired
    private IStudentService iStudentService;

    // http://127.0.0.1:8080/canteen/sell/foodList
    @CrossOrigin
    @GetMapping("/foodList")
    public ResultVO list()
    {

        List<FoodInfo> foodInfoList = iFoodInfoService.findUpAll();

        List<FoodInfoVO> foodInfoVOList = new ArrayList<FoodInfoVO>();

        for(FoodInfo foodInfo : foodInfoList)
        {
            FoodInfoVO foodInfoVO = new FoodInfoVO();

            BeanUtils.copyProperties(foodInfo, foodInfoVO);
            foodInfoVOList.add(foodInfoVO);
        }

        return ResultVOUtil.querySuccess(foodInfoVOList);
    }

    // http://127.0.0.1:8080/canteen/sell/pay
    @CrossOrigin
    @PostMapping(value = "/pay", produces = "application/json")
    public ResultVO pay(@RequestBody OrderForm orderForm )
    {
        /* 根据传入的学生id查询相关的学生信息 */
        StudentDTO studentDTO = iStudentService.findByStudentId(orderForm.getStudentId());
        StudentInfo studentInfo = iStudentService.findByStudentIdUsedByAdmin(orderForm.getStudentId());

        BigDecimal foodPrice;
        BigDecimal totalPrice = BigDecimal.ZERO;

        /* 计算订单总价 */
        for (ChosenFoodDTO chosenFoodDTO : orderForm.getChosenFoodDTOList())
        {
            /* 检查食物是否存在 */
            if (chosenFoodDTO.getFoodId() == null)
            {
                throw  new SellException(ResultEnum.FOOD_NOT_EXIST);
            }

            /* 根据订单id查询食物单价 */
            foodPrice = iFoodInfoService.findById(chosenFoodDTO.getFoodId()).getFoodPrice();

            totalPrice = foodPrice.multiply(new BigDecimal(chosenFoodDTO.getFoodQuantity())).add(totalPrice);
        }

        /* 判断密码是否匹配 */
        String password = orderForm.getPassword();

        if ( !password.equals(studentInfo.getPassword())){
            return ResultVOUtil.wrongPassword();
        }

        /* 判断余额是否支付 */
        if(studentDTO.getBalance().compareTo(totalPrice) == -1)     //余额不足
        {
            return ResultVOUtil.BalanceInsufficient(studentDTO);
        }

        /* 创建订单 */
        OrderDTO orderDTO = new OrderDTO();

        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();

        for (ChosenFoodDTO chosenFoodDTO : orderForm.getChosenFoodDTOList())
        {
            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setFoodId(chosenFoodDTO.getFoodId());
            orderDetail.setQuantity(chosenFoodDTO.getFoodQuantity());
            orderDetailList.add(orderDetail);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        orderDTO.setStudentId(orderForm.getStudentId());

        iOrderService.create(orderDTO);
        return ResultVOUtil.paySuccess(iStudentService.pay(totalPrice, orderForm.getStudentId()));        //余额足够，扣除余额，返回的对象包含学生DTO对象（学生id，姓名，余额）

    }

    @CrossOrigin
    @PostMapping(value = "/editFood",produces = "application/json")
    public ResultVO editFood(FoodInfo foodInfo)
    {
        return ResultVOUtil.success("编辑成功",iFoodInfoService.edit(foodInfo));
    }


}
