package com.dyf.controller;


import com.alibaba.fastjson.JSONArray;
import com.dyf.dto.GoodsDTO;
import com.dyf.dto.Order2DTO;
import com.dyf.entity.FoodInfo;
import com.dyf.entity.Orders;
import com.dyf.service.IFoodInfoService;
import com.dyf.service.IOrderService;
import com.dyf.utils.ResultVOUtil;
import com.dyf.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dyf.utils.KeyUtil.genUniqueKey;

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
    public ResultVO create(@RequestBody JSONArray jsonParam, HttpServletRequest request) {
        if (jsonParam == null) {
            log.info("orderForm为空");
        }
        List<GoodsDTO> goodsDTOList = jsonParam.toJavaList(GoodsDTO.class);

        log.info(goodsDTOList.toString());

        String orderId = genUniqueKey();

        for (GoodsDTO goodsDTO : goodsDTOList) {
            if (goodsDTO == null) {
                log.info("goods为空");
                return ResultVOUtil.fail(100, "Goods为空");
            }
            Order2DTO order2DTO = new Order2DTO();

            FoodInfo foodInfo = new FoodInfo();
            foodInfo = iFoodInfoService.findById(goodsDTO.getGoodsId());


            order2DTO.setUserId(request.getAttribute("userId").toString());
            // order2DTO.setUserId("1231");
            order2DTO.setGoodsId(goodsDTO.getGoodsId());
            order2DTO.setGoodsNumber(goodsDTO.getNum());
            order2DTO.setGoodsDetail(goodsDTO.getDetails());
            order2DTO.setGoodsName(foodInfo.getFoodName());
            order2DTO.setImgUrl(foodInfo.getFoodIcon());
            order2DTO.setGoodsPrice(foodInfo.getFoodPrice());
            order2DTO.setGoodsStatus(0);

            order2DTO.setOrderId(orderId);

            iOrderService.create(order2DTO);

        }
        return ResultVOUtil.success(200, "下单成功", null);
    }

    @PostMapping(value = "/getHistory", produces = "application/json")
    public ResultVO getHistory(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "status") Integer status,
            HttpServletRequest request) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);

//       Page<StudentInfo> studentInfoPage = iStudentService.getStudentPage(pageRequest);
        //先根据userId查出相应的订单
        List<Orders> ordersList = iOrderService.findListByUserId(request.getAttribute("userId").toString(), status);
        // List<Orders> ordersList = iOrderService.findListByUserId("1231");

        //使用jdk8的流式编程对list集合进行分组
        Map<String, List<Orders>> listMap = ordersList.stream().collect(Collectors.groupingBy(t -> t.getOrderId()));


        return ResultVOUtil.success(200, "查询历史订单成功", listMap);
    }


}

