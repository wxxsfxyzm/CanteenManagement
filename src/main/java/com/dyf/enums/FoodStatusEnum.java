package com.dyf.enums;

import lombok.Getter;

@Getter
public enum FoodStatusEnum implements CodeEnum
{
    UP(0, "上架"),
    DOWN(1, "下架");

    private Integer code;

    private String message;

    FoodStatusEnum(Integer code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
