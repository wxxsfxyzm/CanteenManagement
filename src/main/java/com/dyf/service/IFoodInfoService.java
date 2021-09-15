package com.dyf.service;

import com.dyf.entity.FoodInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFoodInfoService
{
    /** 根据食物id查询食物信息
     * @return*/
    FoodInfo findById(String foodId);

    /** 根据食物名称查询食物信息
     * @return*/
    Page<FoodInfo> findByFoodName(String foodName, Pageable pageable);
    List<FoodInfo> findByFoodName(String foodName);

    /** 查询所有上架食物的信息 */
    List<FoodInfo> findUpAll();

    /** 查询所有的食物信息 */
    List<FoodInfo> findAll();

    /** 新增食物 */
    FoodInfo save(FoodInfo foodInfo);

    /** 编辑食物 */
    FoodInfo edit(FoodInfo foodInfo);

    /** 食物上架 */
    FoodInfo onSale(String foodId);

    /** 食物下架 */
    FoodInfo offSale(String foodId);
}
