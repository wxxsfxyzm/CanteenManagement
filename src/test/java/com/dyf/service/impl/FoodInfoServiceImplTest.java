package com.dyf.service.impl;

import com.dyf.entity.FoodInfo;
import com.dyf.enums.FoodStatusEnum;
import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FoodInfoServiceImplTest {

    @Autowired
    private FoodInfoServiceImpl foodInfoService;

    @Test
    public void findById()
    {
        FoodInfo foodInfo = foodInfoService.findById("1001");
        Assert.assertNotEquals(0,foodInfo.getFoodId());
    }

    @Test
    public void findUpAll() {
    }

    @Test
    public void save()
    {
        FoodInfo foodInfo = new FoodInfo();
        foodInfo.setFoodId("1002");
        foodInfo.setFoodName("宫保鸡丁");
        foodInfo.setFoodPrice(new BigDecimal(30));
        foodInfo.setFoodStatus(0);


        FoodInfo result = foodInfoService.save(foodInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void onSale() {
        FoodInfo result = foodInfoService.onSale("1001");
        Assert.assertEquals(FoodStatusEnum.UP, result.getFoodStatus());

    }

    @Test
    public void offSale() {
        FoodInfo result = foodInfoService.offSale("1001");
        Assert.assertEquals(FoodStatusEnum.DOWN, result.getFoodStatus());
    }
}