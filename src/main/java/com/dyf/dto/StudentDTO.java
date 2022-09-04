package com.dyf.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class StudentDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String studentId;

    private String studentName;

    private BigDecimal balance;
}
