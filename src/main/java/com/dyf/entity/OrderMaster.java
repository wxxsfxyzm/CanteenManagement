package com.dyf.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicInsert
public class OrderMaster implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    private String orderId;

    private String studentId;

    private BigDecimal orderAmount;

    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @CreationTimestamp
    private Date createTime;
}
