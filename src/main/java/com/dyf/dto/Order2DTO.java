package com.dyf.dto;

import com.dyf.entity.FoodInfo;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Order2DTO {

    private int recordId;

    private String orderId;

    private String userId;

    private String goodsId;

    private String goodsName;

    private String goodsDetail;

    private String imgUrl;

    private BigDecimal goodsPrice;

    private Integer goodsNumber;

    private Integer goodsStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @CreationTimestamp
    private Date createTime;

    private List<FoodInfo> goodsInfoList;

}
