package com.dyf.enums;

import lombok.Getter;

@Getter
public enum ResultEnum
{
    FOOD_NOT_EXIST(100,"该食物不存在"),

    FOOD_STATUS_ERROR(101,"食物上架状态错误"),

    ORDERDETAIL_NOT_EXIST(102,"订单详情不存在"),

    STUDENT_NOT_EXIST(103,"学生不存在");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
