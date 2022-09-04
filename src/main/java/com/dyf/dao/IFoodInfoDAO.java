package com.dyf.dao;

import com.dyf.entity.FoodInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFoodInfoDAO extends JpaRepository<FoodInfo, String>
{
    List<FoodInfo> queryByFoodStatus(Integer foodStatus);

    FoodInfo findByFoodId(String foodId);

    Page<FoodInfo> findByFoodNameLike(String foodName, Pageable pageable);
    List<FoodInfo> findByFoodNameLike(String foodName);
}
