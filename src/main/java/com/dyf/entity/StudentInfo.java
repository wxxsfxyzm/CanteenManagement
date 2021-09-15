package com.dyf.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Web端的学生管理Bean
 */
@Entity
@Table(name = "student_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String studentId;

    private String password;

    private String studentName;

    /**
     * 余额
     */
    private BigDecimal balance;

    public StudentInfo(String username, String password) {
        this.studentName = username;
        this.password = password;
    }

    public StudentInfo(String userid,
                       String username,
                       String password) {
        this.studentId = userid;
        this.studentName = username;
        this.password = password;
        this.balance = BigDecimal.valueOf(0.00);
    }
}
