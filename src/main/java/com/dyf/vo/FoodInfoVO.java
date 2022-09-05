package com.dyf.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class FoodInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private String foodId;

    @JsonProperty("classInfo")
    private String foodClass;

    @JsonProperty("goodsname")
    private String foodName;

    @JsonProperty("goodsprice")
    private BigDecimal foodPrice;

    @JsonProperty("status")
    private Integer foodStatus;

    @JsonProperty("describe")
    private String foodDescribe;

    @JsonProperty("url")
    private String foodIcon;

    @JsonProperty("createTime")
    private Date createTime;

    @JsonProperty("details")
    private String detail;
}
