package com.dyf.vo;

import com.dyf.entity.StudentInfo;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String phone;
    private String password;
    @Nullable
    private String sex;
    @Nullable
    private Date birthday;
    private Integer noun = 0;
    private BigDecimal balance;
    private Boolean isUser;
    @Nullable
    private Date createTime;
    @Nullable
    private Date updateTime;
    @Nullable
    private Date deleteTime;

    public UserInfoVO(StudentInfo studentInfo, Boolean isUser) {
        this.id = studentInfo.getId();
        this.phone = studentInfo.getPhoneNumber();
        this.password = studentInfo.getPassword();
        this.sex = studentInfo.getSex();
        this.birthday = studentInfo.getBirthday();
        this.noun = 0;
        this.balance = studentInfo.getBalance();
        this.isUser = isUser;
        this.createTime = null;
        this.updateTime = null;
        this.deleteTime = null;
    }

}
