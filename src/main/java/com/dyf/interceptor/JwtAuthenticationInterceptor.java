package com.dyf.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.dyf.annotation.JwtAnnotation.PassToken;
import com.dyf.dto.StudentDTO;
import com.dyf.service.IStudentService;
import com.dyf.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @author Lehr
 * @create: 2020-02-03
 */
public class JwtAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    IStudentService accountService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 从请求头中取出 token  这里需要和前端约定好把jwt放到请求头一个叫token的地方
        String token = httpServletRequest.getHeader("token");
        System.out.println("token: " + token);
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                System.out.println("Method has PassToken");
                return true;
            }
        }
        //默认全部检查
        else {
            System.out.println("Method No PassToken");
            // 执行认证
            if (token == null) {
                //  登录失效,没token了
                try {
                    ExceptionHandler(httpServletResponse, "请携带token");
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    httpServletResponse.sendError(500);
                }
                //throw new RuntimeException(method + "NeedToLogin");
            }

            // 获取 token 中的 user Name
            String userId = null;
            try {
                userId = JwtUtils.getAudience(token);
            } catch (RuntimeException e) {
                ExceptionHandler(httpServletResponse, e.getMessage());
                return false;
            }

            //找找看是否有这个user   因为我们需要检查用户是否存在，读者可以自行修改逻辑
            StudentDTO user = accountService.findByStudentId(userId);

            if (user == null) {
                //这个错误也是我自定义的
                try {
                    ExceptionHandler(httpServletResponse, "token无效");
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    httpServletResponse.sendError(500);
                }
            }

            // 验证 token
            try {
                JwtUtils.verifyToken(token, userId);
            } catch (RuntimeException e) {
                ExceptionHandler(httpServletResponse, e.getMessage());
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                httpServletResponse.sendError(500);
            }

            //获取载荷内容
            // String userName = JwtUtils.getClaimByName(token, "userName").asString();

            //放入attribute以便后面调用
            httpServletRequest.setAttribute("userId", userId);


            return true;

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

    private void ExceptionHandler(HttpServletResponse httpServletResponse, String msg) throws IOException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        JSONObject res = new JSONObject();
        res.put("state", false);
        res.put("msg", msg);
        out = httpServletResponse.getWriter();
        out.append(res.toString());
        out.close();
    }
}