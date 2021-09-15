package com.dyf.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FoodInfoVO implements Serializable
{
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private String foodId;

    @JsonProperty("name")
    private String foodName;

    @JsonProperty("price")
    private BigDecimal foodPrice;

    @JsonProperty("status")
    private  Integer foodStatus;

    @JsonProperty("icon")
    private String foodIcon;
}
