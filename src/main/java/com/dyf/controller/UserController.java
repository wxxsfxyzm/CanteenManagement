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

import static com.dyf.constant.Constant.*;
import static com.dyf.enums.ResultEnum.*;

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
            return ResultVOUtil.fail(-99, NM_NO_ENTER);
        String info = LOGIN_LOGIC;
        log.info(info +
                ": userid = " +
                studentId +
                ", password = " +
                password);

        StudentInfo user = iStudentService.findById(studentId);

        if (user != null) {
            log.info(user.toString());
            if (Objects.equals(password, user.getPassword())) {
                info = LOGIN_SUCCESS.getMessage();
                log.info(info);
                return ResultVOUtil.success(info, user);
            } else {
                info = PASSWORD_MISMATCH.getMessage();
                log.info(info);
                return ResultVOUtil.fail(PASSWORD_MISMATCH.getCode(), info);
            }
        } else {
            info = STUDENT_NOT_EXIST.getMessage();
            log.info(info);
        }

        return ResultVOUtil.fail(STUDENT_NOT_EXIST.getCode(), info);
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
        String info = REGISTER_LOGIC;
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
            info = REGISTER_SUCCESS.getMessage();
            return ResultVOUtil.success(iStudentService.findById(userid));
        } else { // 用户名重复
            info = DUPLICATE_STUDENT_ID.getMessage();
            return ResultVOUtil.fail(DUPLICATE_STUDENT_ID.getCode(), info);
        }
    }

    @PostMapping(value = "/deposit")
    public ResultVO stuDeposit(
            @RequestParam(value = "userid") String studentId,
            @RequestParam(value = "balance") BigDecimal amount) {
        StudentInfo student = iStudentService.findById(studentId);

        String info = DEPOSIT_LOGIC;
        log.info(info);

        if (student == null) {
            info = STUDENT_NOT_EXIST.getMessage();
            log.info(info);
            return ResultVOUtil.fail(STUDENT_NOT_EXIST.getCode(), info);
        }
        log.info(student.toString());

        StudentInfo studentInfoAfterDepositOperation = iStudentService.stuDeposit(student, amount);

        return ResultVOUtil.success(DEPOSIT_SUCCESS.getMessage(), studentInfoAfterDepositOperation);
    }
}
