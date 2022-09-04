package com.dyf.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
public class OrderDetail implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    private String detailId;

    private String orderId;

    private String foodId;

    private String foodName;

    private BigDecimal foodPrice;

    private Integer quantity;
}
