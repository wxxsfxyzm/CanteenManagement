package com.dyf.vo;

import com.dyf.dto.OrderDTO;
import com.dyf.entity.OrderDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderMasterVO implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    /** 学生id */
    private String studentId;

    /** 学生姓名 */
    private String studentName;

    /** 学生余额 */
    private BigDecimal studentBalance;

    /** 订单明细列表 */
    // 这个字段在表中不存在
    @Transient
    private List<OrderDTO> orderDTOList;

}
