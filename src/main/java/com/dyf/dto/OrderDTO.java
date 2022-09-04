package com.dyf.dto;

import com.dyf.entity.OrderDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 订单id */
    private String orderId;

    /** 学生id */
    private String studentId;

    /** 订单总金额 */
    private BigDecimal orderAmount;

    /** 创建时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 订单明细列表 */
    // 这个字段在表中不存在
    @Transient
    private List<OrderDetail> orderDetailList;

}
