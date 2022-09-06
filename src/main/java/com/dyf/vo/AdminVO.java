package com.dyf.vo;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.io.Serializable;

@Data
public class AdminVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;

    @Nullable
    private String token;

    public AdminVO(Integer code, String token) {
        this.code = code;
        this.token = token;
    }
}
