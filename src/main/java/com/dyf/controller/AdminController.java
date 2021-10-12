package com.dyf.controller;

import com.dyf.entity.FoodInfo;
import com.dyf.entity.StudentInfo;
import com.dyf.enums.ResultEnum;
import com.dyf.exception.SellException;
import com.dyf.form.FoodForm;
import com.dyf.form.StudentForm;
import com.dyf.service.IFoodInfoService;
import com.dyf.service.IStudentService;
import com.dyf.utils.KeyUtil;
import com.dyf.utils.ResultVOUtil;
import com.dyf.vo.FoodInfoVO;
import com.dyf.vo.ResultVO;
import com.dyf.vo.StudentInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.dyf.constant.Constant.SUCCESS;
import static com.dyf.enums.ResultEnum.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private IFoodInfoService iFoodInfoService;

    // http://127.0.0.1:8080/canteen/admin/getFoodList
    @GetMapping("/getFoodList")
    public ResultVO getFoodList() {
        List<FoodInfo> foodInfoList = iFoodInfoService.findAll();
        List<FoodInfoVO> foodInfoVOList = new ArrayList<>();

        for (FoodInfo foodInfo : foodInfoList) {
            FoodInfoVO foodInfoVO = new FoodInfoVO();

            BeanUtils.copyProperties(foodInfo, foodInfoVO);

            foodInfoVOList.add(foodInfoVO);
        }

        return ResultVOUtil.success(QUERY_SUCCESS.getMessage(), foodInfoVOList);
    }

    // http://127.0.0.1:8080/canteen/admin/addFood
    @PostMapping("/addFood")
    public ResultVO addFood(@RequestBody FoodForm foodForm) {

        FoodInfo foodInfo = new FoodInfo();

//        foodInfo.setFoodId(KeyUtil.genUniqueKey());
//        foodInfo.setFoodPrice(foodForm.getFoodPrice());
//        foodInfo.setFoodName(foodForm.getFoodName());
        foodForm.setFoodId(KeyUtil.genUniqueFoodKey());

        BeanUtils.copyProperties(foodForm, foodInfo);

        iFoodInfoService.save(foodInfo);

        return ResultVOUtil.success(ADD_SUCCESS.getMessage(), foodForm);
    }

    @PostMapping("/editFood")
    public ResultVO editFood(@RequestBody FoodForm foodForm) {
        FoodInfo foodInfo = iFoodInfoService.findById(foodForm.getFoodId());

        if (foodInfo == null) {
            throw new SellException(ResultEnum.FOOD_NOT_EXIST);
            //return ResultVOUtil.editFail();
        }

        BeanUtils.copyProperties(foodForm, foodInfo);

        iFoodInfoService.save(foodInfo);

        return ResultVOUtil.success(EDIT_SUCCESS.getMessage(), foodForm);

    }

    @PostMapping(value = "/getFoodListByName", produces = "application/json")
    public ResultVO getFoodListByName(@RequestParam String foodName) {

        List<FoodInfo> foodInfoList = iFoodInfoService.findByFoodName(foodName);
        if (foodInfoList.isEmpty()) {
            return ResultVOUtil.fail(QUERY_FAIL.getCode(), QUERY_FAIL.getMessage());
        }
        return ResultVOUtil.success(QUERY_SUCCESS.getMessage(), foodInfoList);
    }

    @GetMapping("/getStudentList")
    public ResultVO getStudentList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);

        Page<StudentInfo> studentInfoPage = iStudentService.getStudentPage(pageRequest);

        List<StudentInfoVO> studentInfoVOList = new ArrayList<StudentInfoVO>();

//        for(StudentInfo studentInfo : studentInfoPage){
//            StudentInfoVO studentInfoVO = new StudentInfoVO();
//
//            BeanUtils.copyProperties(studentInfo, studentInfoVO);
//
//            studentInfoVOList.add(studentInfoVO);
//        }

        return ResultVOUtil.success(QUERY_SUCCESS.getMessage(), studentInfoPage);
    }

    @PostMapping(value = "/addStudent", produces = "application/json")
    public ResultVO addStudent(@RequestBody StudentForm studentForm) {
        if (studentForm == null) {
            return ResultVOUtil.fail(ADD_FAIL.getCode(), ADD_FAIL.getMessage());
        }
        StudentInfo studentInfo = new StudentInfo();

        BeanUtils.copyProperties(studentForm, studentInfo);

        return ResultVOUtil.success(ADD_SUCCESS.getMessage(), iStudentService.addStudent(studentInfo));

    }


    @PostMapping(value = "/editStudent", produces = "application/json")
    public ResultVO editStudent(@RequestBody StudentForm studentForm) {
        if (studentForm == null) {
            return ResultVOUtil.fail(EDIT_FAIL.getCode(), EDIT_FAIL.getMessage());
        }


        StudentInfo studentInfo = iStudentService.findByStudentIdUsedByAdmin(studentForm.getStudentId());

        if (studentInfo == null) {
            throw new SellException(STUDENT_NOT_EXIST);
        }

        if (studentForm.getBalance() != null) studentInfo.setBalance(studentForm.getBalance());
        if (studentForm.getStudentName() != null) studentInfo.setStudentName(studentForm.getStudentName());
        if (studentForm.getPassword() != null) studentInfo.setPassword(studentForm.getPassword());

        return ResultVOUtil.success(EDIT_SUCCESS.getMessage(), iStudentService.editStudent(studentInfo));
    }

    @PostMapping(value = "/stuDeposit", produces = "application/json")
    public ResultVO stuDeposit(String studentId, BigDecimal amount) {
        StudentInfo studentInfo = iStudentService.findByStudentIdUsedByAdmin(studentId);

        if (studentInfo == null) {
            throw new SellException(STUDENT_NOT_EXIST);
        }

        if (amount.compareTo(BigDecimal.ZERO) < 1) {
            return ResultVOUtil.fail(DEPOSIT_FAIL.getCode(), DEPOSIT_FAIL.getMessage());
        }

        iStudentService.stuDeposit(studentInfo, amount);

        return ResultVOUtil.success(0, DEPOSIT_SUCCESS.getMessage());
    }

    @PostMapping(value = "/deleteStudent", produces = "application/json")
    public ResultVO deleteStudent(String studentId) {
        try {
            StudentInfo studentInfo = iStudentService.findByStudentIdUsedByAdmin(studentId);
            iStudentService.deleteStudent(studentInfo);
        } catch (SellException sellException) {
            log.error(STUDENT_NOT_EXIST.getMessage());
            return ResultVOUtil.fail(STUDENT_NOT_EXIST.getCode(), STUDENT_NOT_EXIST.getMessage());
        }

        return ResultVOUtil.success(SUCCESS, DELETE_SUCCESS.getMessage());
    }

    @PostMapping(value = "/findStudentByName", produces = "application/json")
    public ResultVO findStudentByName(
            @RequestParam String studentName,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<StudentInfo> studentInfoList = iStudentService.findByStudentName(studentName, pageable);

        if (studentInfoList.isEmpty()) {
            return ResultVOUtil.fail(QUERY_FAIL.getCode(), QUERY_FAIL.getMessage());
        }

        return ResultVOUtil.success(QUERY_SUCCESS.getMessage(), studentInfoList);
    }

    @PostMapping(value = "/findStudentById", produces = "application/json")
    public ResultVO findStudentById(
            @RequestParam String studentId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<StudentInfo> studentInfoPage = iStudentService.findByStudentId(studentId, pageRequest);

        if (studentInfoPage.isEmpty()) {
            return ResultVOUtil.fail(QUERY_FAIL.getCode(), QUERY_FAIL.getMessage());
        }

        return ResultVOUtil.success(QUERY_SUCCESS.getMessage(), studentInfoPage);
    }

}
