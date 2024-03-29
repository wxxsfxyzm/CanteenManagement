package com.dyf.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String goodsId;

    private Integer num;

    private String details;
}
