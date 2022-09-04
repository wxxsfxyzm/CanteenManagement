package com.dyf.service.impl;

import com.dyf.dao.IFoodInfoDAO;
import com.dyf.entity.FoodInfo;
import com.dyf.enums.FoodStatusEnum;
import com.dyf.enums.ResultEnum;
import com.dyf.exception.SellException;
import com.dyf.service.IFoodInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FoodInfoServiceImpl implements IFoodInfoService {
    @Autowired
    private IFoodInfoDAO iFoodInfoDAO;

    @Override
    public FoodInfo findById(String foodId) {
        return iFoodInfoDAO.findById(foodId).orElse(null);
    }

    @Override
    public Page<FoodInfo> findByFoodName(String foodName, Pageable pageable) {
        return iFoodInfoDAO.findByFoodNameLike("%" + foodName + "%", pageable);
    }

    @Override
    public List<FoodInfo> findByFoodName(String foodName) {
        return iFoodInfoDAO.findByFoodNameLike("%" + foodName + "%");
    }

    @Override
    public List<FoodInfo> findUpAll() {
        return iFoodInfoDAO.queryByFoodStatus(FoodStatusEnum.UP.getCode());
    }

    @Override
    public List<FoodInfo> findAll() {
        return iFoodInfoDAO.findAll();
    }

    @Override
    public FoodInfo save(FoodInfo foodInfo) {
        return iFoodInfoDAO.save(foodInfo);
    }

    @Override
    public FoodInfo edit(FoodInfo foodInfo) {

        FoodInfo foodInfo1 = iFoodInfoDAO.findByFoodId(foodInfo.getFoodId());

        if (foodInfo.getFoodName() != null) {
            foodInfo1.setFoodName(foodInfo.getFoodName());
        }

        if (foodInfo.getFoodPrice().compareTo(BigDecimal.ZERO) >= -1) {
            foodInfo1.setFoodPrice(foodInfo.getFoodPrice());
        }

        if (foodInfo.getFoodIcon() != null) {
            foodInfo1.setFoodIcon(foodInfo.getFoodIcon());
        }

        iFoodInfoDAO.save(foodInfo1);
        return foodInfo1;
    }

    @Override
    public FoodInfo onSale(String foodId) {

        FoodInfo foodInfo = iFoodInfoDAO.findById(foodId).orElse(null);

        /*
          食物id不存在
         */
        if (foodInfo == null) {
            throw new SellException(ResultEnum.FOOD_NOT_EXIST);
        }

        /*
          食物上架状态异常
         */
        if (foodInfo.getFoodStatusEnum() == FoodStatusEnum.UP) {
            throw new SellException(ResultEnum.FOOD_STATUS_ERROR);
        }


        foodInfo.setFoodStatus(FoodStatusEnum.UP.getCode());
        return iFoodInfoDAO.save(foodInfo);

    }

    @Override
    public FoodInfo offSale(String foodId) {
        FoodInfo foodInfo = iFoodInfoDAO.findById(foodId).orElse(null);

        /*
          食物id不存在
         */
        if (foodInfo == null) {
            throw new SellException(ResultEnum.FOOD_NOT_EXIST);
        }

        /*
          食物下架状态异常
         */
        if (foodInfo.getFoodStatusEnum() == FoodStatusEnum.DOWN) {
            throw new SellException(ResultEnum.FOOD_STATUS_ERROR);
        }


        foodInfo.setFoodStatus(FoodStatusEnum.DOWN.getCode());
        return iFoodInfoDAO.save(foodInfo);
    }
}
