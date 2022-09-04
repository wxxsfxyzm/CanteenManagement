package com.dyf.vo;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.io.Serializable;

@Data
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Nullable
    private Boolean success;

    private Integer code;

    private String msg;

    private T data;


}
