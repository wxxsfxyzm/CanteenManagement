package com.dyf.form;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class StudentForm implements Serializable
{
    private static final long serialVersionUID = 1l;

    private String studentId;

    private String password;

    private String studentName;

    /**
     * 余额
     */
    private BigDecimal balance = BigDecimal.ZERO;
}
