package com.dyf.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TokenVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String msg;
    private Boolean state;
    private String token;

    public TokenVO(String msg, boolean state, String token) {
        this.msg = msg;
        this.state = state;
        this.token = token;
    }
}
