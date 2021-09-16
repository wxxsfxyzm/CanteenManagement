package com.dyf.enums;

import lombok.Getter;

@Getter
public enum FoodStatusEnum implements CodeEnum<Integer> {
    UP(0, "上架"),
    DOWN(1, "下架");

    private final Integer code;

    private final String message;

    FoodStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
