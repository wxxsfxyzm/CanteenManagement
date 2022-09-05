package com.dyf.controller;

import com.dyf.entity.FoodInfo;
import com.dyf.service.IFoodInfoService;
import com.dyf.utils.ResultVOUtil;
import com.dyf.vo.FoodInfoMenuVO;
import com.dyf.vo.FoodInfoVO;
import com.dyf.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/goodsinfo")
public class GoodsController {

    @Autowired
    private IFoodInfoService iFoodInfoService;

    @PostMapping("/getGoodsInfo")
    public ResultVO getFoodList() {
        List<FoodInfo> foodInfoList = iFoodInfoService.findAll();
        List<FoodInfoVO> foodInfoVOList = new ArrayList<>();

        for (FoodInfo foodInfo : foodInfoList) {
            FoodInfoVO foodInfoVO = new FoodInfoVO();

            BeanUtils.copyProperties(foodInfo, foodInfoVO);

            foodInfoVOList.add(foodInfoVO);
        }

        return ResultVOUtil.success(200, "成功", new FoodInfoMenuVO(foodInfoVOList));
    }

}
