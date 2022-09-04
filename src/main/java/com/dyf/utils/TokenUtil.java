package com.dyf.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dyf.entity.StudentInfo;

import javax.servlet.http.HttpServletRequest;

public class TokenUtil {
    public static String getToken(StudentInfo user) {
        String token = "";
        token = JWT.create().withAudience(user.getId())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

    public static String getUserIdFromToken(HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        return JwtUtils.getAudience(token);
    }
}
