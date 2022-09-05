package com.dyf.vo;

import com.dyf.dto.Order2DTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String success;

    private String code;

    private String message;

    private List<Order2DTO> records;
}
