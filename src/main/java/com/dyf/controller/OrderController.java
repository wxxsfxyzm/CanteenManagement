package com.dyf.controller;


import com.dyf.dto.GoodsDTO;
import com.dyf.dto.Order2DTO;
import com.dyf.entity.FoodInfo;
import com.dyf.entity.Orders;
import com.dyf.entity.StudentInfo;
import com.dyf.form.Order2Form;
import com.dyf.form.OrderForm;
import com.dyf.service.IFoodInfoService;
import com.dyf.service.IOrderService;
import com.dyf.utils.ResultVOUtil;
import com.dyf.vo.ResultVO;
import com.dyf.vo.StudentInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.dyf.enums.ResultEnum.QUERY_SUCCESS;

@CrossOrigin
@RestController
@RequestMapping("/historyrecord")
@Slf4j
public class OrderController {

    @Autowired
    private IFoodInfoService iFoodInfoService;

    @Autowired
    private IOrderService iOrderService;

   @PostMapping(value = "/orderTea", produces = "application/json")
    public ResultVO create(@RequestBody Order2Form orderForm, HttpServletRequest request){
       if (orderForm == null){
           log.info("orderForm为空");
       }

        for (GoodsDTO goodsDTO : orderForm.getGoodsDTOList()){
            if (goodsDTO == null){
                log.info("goods为空");
            }
            Order2DTO order2DTO = new Order2DTO();

            FoodInfo foodInfo = new FoodInfo();
            foodInfo = iFoodInfoService.findById(order2DTO.getGoodsId());

            order2DTO.setUserId(request.getAttribute("userId").toString());
            order2DTO.setGoodsId(goodsDTO.getGoodsId());
            order2DTO.setGoodsNumber(goodsDTO.getNum());
            order2DTO.setGoodsDetail(goodsDTO.getDetails());
            order2DTO.setGoodsName(foodInfo.getFoodName());
            order2DTO.setImgUrl(foodInfo.getFoodIcon());
            order2DTO.setGoodsPrice(foodInfo.getFoodPrice());

            iOrderService.create(order2DTO);

            return ResultVOUtil.success(order2DTO);
        }
       return ResultVOUtil.success(100);
   }

   @PostMapping(value = "/getHistory", produces = "application/json")
   public ResultVO getHistory(
           @RequestParam(value = "page", defaultValue = "1") Integer page,
           @RequestParam(value = "size", defaultValue = "10") Integer size,
           HttpServletRequest request) {

       PageRequest pageRequest = PageRequest.of(page - 1, size);

//       Page<StudentInfo> studentInfoPage = iStudentService.getStudentPage(pageRequest);
       //先根据userId查出相应的订单
       //List<Order2DTO> ordersList = iOrderService.findListByUserId(request.getAttribute("userId").toString());
       List<Orders> ordersList = iOrderService.findListByUserId("1231");

       //使用jdk8的流式编程对list集合进行分组
       Map<String, List<Orders>> listMap = ordersList.stream().collect(Collectors.groupingBy(t -> t.getOrderId()));

       ResultVO resultVO = new ResultVO();

       resultVO.setCode(200);
       resultVO.setData(listMap);
       resultVO.setSuccess(true);
       resultVO.setMsg("查询历史订单成功");


       return resultVO;
   }



}

