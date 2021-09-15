package com.dyf.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    private T data;


}
