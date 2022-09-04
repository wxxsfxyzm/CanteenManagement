package com.dyf.form;

import com.dyf.dto.ChosenFoodDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderForm implements Serializable
{
    private static final long serialVersionUID = 1l;

    private String studentId;

    private String password;

    private List<ChosenFoodDTO> chosenFoodDTOList;
}
