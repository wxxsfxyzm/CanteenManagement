package com.dyf.controller;

import com.dyf.annotation.JwtAnnotation.PassToken;
import com.dyf.dto.UserDTO;
import com.dyf.entity.StudentInfo;
import com.dyf.service.IStudentService;
import com.dyf.utils.JwtUtils;
import com.dyf.utils.KeyUtil;
import com.dyf.utils.ResultVOUtil;
import com.dyf.vo.ResultVO;
import com.dyf.vo.TokenVO;
import com.dyf.vo.UserInfoVO;
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
     * @param phonenumber 用户id(学号)
     * @param password    密码
     * @return JSON
     */

    @PostMapping(value = "/login")
    @PassToken
    public ResultVO login(
            @RequestParam(value = "phone") String phonenumber,
            @RequestParam(value = "password") String password) {
        String info = LOGIN_LOGIC;
        log.info(info +
                ": phone = " +
                phonenumber +
                ", password = " +
                password);

        StudentInfo user = iStudentService.findByPhoneNumber(phonenumber);

        if (user != null) {
            log.info(user.toString());
            if (Objects.equals(password, user.getPassword())) {
                info = LOGIN_SUCCESS.getMessage();
                String token = JwtUtils.createToken(user);
                UserDTO userDTO = new UserDTO(new UserInfoVO(user, true), new TokenVO("认证成功", true, token));
                return ResultVOUtil.success(200, info, userDTO);
            } else {
                info = PASSWORD_MISMATCH.getMessage();
                log.info(info);
                return ResultVOUtil.fail(100, "账号或密码错误！");
            }
        } else {
            info = STUDENT_NOT_EXIST.getMessage();
            log.info(info);
        }

        return ResultVOUtil.fail(100, "账号或密码错误！");
    }

    /**
     * 注册逻辑
     *
     * @param phonenumber 学号
     * @param password    密码
     * @return JSON
     */
    @PostMapping("/register")
    @PassToken
    public ResultVO register(
            @RequestParam(value = "phone") String phonenumber,
            @RequestParam(value = "password") String password) {
        String info = REGISTER_LOGIC;
        String userid = KeyUtil.genUniqueKey();
        log.info(info +
                ": userid = " +
                userid +
                ": userid = " +
                phonenumber +
                ", password = " +
                password);
        if (iStudentService.findById(userid) == null) {// 用户名未重复，放行
            StudentInfo user = new StudentInfo(userid, phonenumber, null, password);
            log.info(user.toString());
            iStudentService.save(user);
            log.info(iStudentService.findById(userid).toString());
            info = REGISTER_SUCCESS.getMessage();
            return ResultVOUtil.success(200, "注册成功", iStudentService.findById(userid));
        } else { // 用户名重复
            info = DUPLICATE_STUDENT_ID.getMessage();
            return ResultVOUtil.fail(200, "注册失败：该账号已注册，请直接登录");
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
