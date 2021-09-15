package com.dyf.entity;

import com.dyf.enums.FoodStatusEnum;
import com.dyf.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicInsert
public class FoodInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    private String foodId;

    private String foodName;

    private BigDecimal foodPrice = BigDecimal.ZERO;

    private String foodIcon;

    private Integer foodStatus = FoodStatusEnum.UP.getCode();

    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @CreationTimestamp
    private Date createTime;

    /** 获取产品的枚举类: 商品的各个状态都在里面 */
    @JsonIgnore
    public FoodStatusEnum getFoodStatusEnum()
    {
        return EnumUtil.getByCode(foodStatus,FoodStatusEnum.class);
    }
}
