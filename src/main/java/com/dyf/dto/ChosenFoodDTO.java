package com.dyf.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ChosenFoodDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String foodId;

    private Integer foodQuantity;

}
