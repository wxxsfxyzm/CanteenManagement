package com.dyf.vo;

import com.dyf.entity.StudentInfo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class StudentInfoVO implements Serializable
{
    private static final long serialVersionUID = 1l;

    private String studentId;

    private String StudentName;

    private BigDecimal balance;

}
