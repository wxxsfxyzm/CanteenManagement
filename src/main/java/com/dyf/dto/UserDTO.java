package com.dyf.dto;

import com.dyf.vo.TokenVO;
import com.dyf.vo.UserInfoVO;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private UserInfoVO info;

    @Nullable
    private TokenVO token;

    public UserDTO(UserInfoVO user, TokenVO tokenVO) {
        this.info = user;
        this.token = tokenVO;
    }
}
