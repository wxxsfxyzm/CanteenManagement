package com.dyf.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicInsert
public class Orders {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int recordId;

    private String orderId;

    private String userId;

    private String goodsId;

    private String goodsName;

    private String goodsDetail;

    private String imgUrl;

    private BigDecimal goodsPrice;

    private Integer goodsNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @CreationTimestamp
    private Date createTime;

}
