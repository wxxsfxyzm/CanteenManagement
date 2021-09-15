package com.dyf.form;

import com.dyf.enums.FoodStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FoodForm implements Serializable
{
        private static final long serialVersionUID = 1L;

        private String foodId;

        private String foodName;

        private BigDecimal foodPrice;

        private String foodIcon;

        private Integer foodStatus = FoodStatusEnum.UP.getCode();

}
