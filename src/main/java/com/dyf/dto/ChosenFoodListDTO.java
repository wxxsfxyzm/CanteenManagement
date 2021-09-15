package com.dyf.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ChosenFoodListDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    private List<ChosenFoodDTO> chosenFoodDTOList;

}
