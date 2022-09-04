package com.dyf.entity;

import lombok.*;

import javax.persistence.Column;
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

    @Column(name = "student_id")
    @Id
    private String id;

    private String password;

    @Column(name = "student_name")
    private String userName;

    private String phoneNumber;

    private String sex;

    private String birthday;
    /**
     * 余额
     */
    private BigDecimal balance;

    public StudentInfo(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    public StudentInfo(String userid,
                       String phoneNumber,
                       String username,
                       String password) {
        this.id = userid;
        this.phoneNumber = phoneNumber;
        this.userName = username;
        this.password = password;
        this.balance = BigDecimal.valueOf(0.00);
    }


}
