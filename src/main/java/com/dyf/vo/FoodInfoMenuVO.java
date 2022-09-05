package com.dyf.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FoodInfoMenuVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<FoodInfoVO> menu;

    public FoodInfoMenuVO(List<FoodInfoVO> foodInfoVOList) {
        this.menu = foodInfoVOList;
    }
}
