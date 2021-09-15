package com.dyf.service;


import com.dyf.dto.OrderDTO;
import com.dyf.dto.StudentDTO;
import com.dyf.entity.StudentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface IStudentService {
    StudentDTO findByStudentId(String studentId);

    StudentInfo findByStudentIdUsedByAdmin(String studentId);

    Page<StudentInfo> findByStudentName(String studentName, Pageable pageable);

    Page<StudentInfo> findByStudentId(String studentId, Pageable pageable);

    StudentDTO pay(OrderDTO orderDTO);

    StudentDTO pay(BigDecimal totalPrice, String studentId);

    List<StudentInfo> getStudentList();

    Page<StudentInfo> getStudentPage(Pageable pageable);

    StudentInfo addStudent(StudentInfo studentInfo);

    StudentInfo editStudent(StudentInfo studentInfo);

    StudentInfo editStudentPassword(StudentInfo studentInfo, String password);

    StudentInfo stuDeposit(StudentInfo studentInfo, BigDecimal amount);

    StudentInfo deleteStudent(StudentInfo studentInfo);

    /**
     * 根据用户名查找用户
     */
    StudentInfo findByName(String name);

    /**
     * 根据用户id（学号）查找用户
     *
     * @return StudentInfo
     */
    StudentInfo findById(String userId);

    /**
     * 用户注册入库
     */
    void save(StudentInfo user);

}
