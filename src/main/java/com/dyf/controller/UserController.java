package com.dyf.controller;

import com.dyf.entity.StudentInfo;
import com.dyf.service.IStudentService;
import com.dyf.utils.ResultVOUtil;
import com.dyf.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private IStudentService iStudentService;

    /**
     * 登录逻辑
     *
     * @param studentId 用户id(学号)
     * @param password  密码
     * @return JSON
     */

    @PostMapping(value = "/login")
    public ResultVO login(
            @RequestParam(value = "userid") String studentId,
            @RequestParam(value = "password") String password) {
        if (Objects.equals(studentId, "195030320")) // 这是一个彩蛋
            return ResultVOUtil.fail(-1, "牛马，你不配进入");
        String info = "登录逻辑";
        log.info(info +
                ": userid = " +
                studentId +
                ", password = " +
                password);

        StudentInfo user = iStudentService.findById(studentId);

        if (user != null) {
            log.info(user.toString());
            if (Objects.equals(password, user.getPassword())) {
                info = "登录成功！";
                return ResultVOUtil.success(info, user);
            } else {
                info = "密码错误！";
                return ResultVOUtil.fail(-1, info);
            }
        } else {
            info = "没查找到该用户！";
            log.info(info);
        }

        log.info(info);
        return ResultVOUtil.fail(-2, info);
    }

    /**
     * 注册逻辑
     *
     * @param userid   学号
     * @param username 用户名
     * @param password 密码
     * @return JSON
     */
    @PostMapping("/register")
    public ResultVO register(
            @RequestParam(value = "userid") String userid,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password) {
        String info = "注册逻辑";
        log.info(info +
                ": userid = " +
                userid +
                ", username = " +
                username +
                ", password = " +
                password);

        if (iStudentService.findById(userid) == null) {// 用户名未重复，放行
            StudentInfo user = new StudentInfo(userid, username, password);
            log.info(user.toString());
            iStudentService.save(user);
            log.info(iStudentService.findById(userid).toString());
            info = "注册成功";
            return ResultVOUtil.success(iStudentService.findById(userid));
        } else { // 用户名重复
            info = "学号重复";
            return ResultVOUtil.success(1, info);
        }
    }

    @PostMapping(value = "/deposit")
    public ResultVO stuDeposit(
            @RequestParam(value = "userid") String studentId,
            @RequestParam(value = "balance") BigDecimal amount) {
        StudentInfo student = iStudentService.findById(studentId);

        String info = "";
        if (student == null) {
            info = "没有查询到学生信息";
            log.info(info);
            return ResultVOUtil.fail(-1, info);
        }
        log.info(student.toString());

        StudentInfo studentInfoAfterDepositOperation = iStudentService.stuDeposit(student, amount);

        return ResultVOUtil.success("充值成功", studentInfoAfterDepositOperation);
    }
}
