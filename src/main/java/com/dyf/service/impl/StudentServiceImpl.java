package com.dyf.service.impl;

import com.dyf.dao.IStudentInfoDAO;
import com.dyf.dto.OrderDTO;
import com.dyf.dto.StudentDTO;
import com.dyf.entity.StudentInfo;
import com.dyf.enums.ResultEnum;
import com.dyf.exception.SellException;
import com.dyf.service.IStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.dyf.enums.ResultEnum.QUERY_SUCCESS;

@Service
@Slf4j
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private IStudentInfoDAO iStudentInfoDAO;

    @Override
    public StudentDTO findByStudentId(String studentId) {

        StudentInfo studentInfo = iStudentInfoDAO.findByStudentId(studentId);

        /** 查询结果为空的处理 */
        if (studentInfo == null) {
            throw new SellException(ResultEnum.STUDENT_NOT_EXIST);
        }

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentName(studentInfo.getStudentName());
        studentDTO.setStudentId(studentInfo.getStudentId());
        studentDTO.setBalance(studentInfo.getBalance());

        return studentDTO;
    }

    @Override
    public StudentInfo findByStudentIdUsedByAdmin(String studentId) {

        StudentInfo studentInfo = iStudentInfoDAO.findByStudentId(studentId);

        /* 查询结果为空的处理 */
        if (studentInfo == null) {
            throw new SellException(ResultEnum.STUDENT_NOT_EXIST);
        }

        return studentInfo;
    }

    @Override
    public Page<StudentInfo> findByStudentName(String studentName, Pageable pageable) {
        return iStudentInfoDAO.findByStudentNameLike("%" + studentName + "%", pageable);
    }

    @Override
    public Page<StudentInfo> findByStudentId(String studentId, Pageable pageable) {
        return iStudentInfoDAO.findByStudentIdLike("%" + studentId + "%", pageable);
    }

    @Override
    public StudentDTO pay(OrderDTO orderDTO) {
        //通过订单查询学生信息
        StudentInfo studentInfo = iStudentInfoDAO.findByStudentId(orderDTO.getStudentId());

        //扣除学生的余额
        studentInfo.setBalance(studentInfo.getBalance().subtract(orderDTO.getOrderAmount()));

        //返回studentDTO对象
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(studentInfo.getStudentId());
        studentDTO.setStudentName(studentInfo.getStudentName());
        studentDTO.setBalance(studentInfo.getBalance());

        //将修改后的数据保存到数据库
        iStudentInfoDAO.save(studentInfo);

        return studentDTO;
    }

    @Override
    public StudentDTO pay(BigDecimal totalPrice, String studentId) {
        //通过订单查询学生信息
        StudentInfo studentInfo = iStudentInfoDAO.findByStudentId(studentId);

        //扣除学生的余额
        studentInfo.setBalance(studentInfo.getBalance().subtract(totalPrice));

        //返回studentDTO对象
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(studentInfo.getStudentId());
        studentDTO.setStudentName(studentInfo.getStudentName());
        studentDTO.setBalance(studentInfo.getBalance());

        //将修改后的数据保存到数据库
        iStudentInfoDAO.save(studentInfo);

        return studentDTO;
    }

    @Override
    public List<StudentInfo> getStudentList() {
        return iStudentInfoDAO.findAll();
    }

    @Override
    public Page<StudentInfo> getStudentPage(Pageable pageable) {
        return iStudentInfoDAO.findAll(pageable);
    }


    @Override
    public StudentInfo addStudent(StudentInfo studentInfo) {
        return iStudentInfoDAO.save(studentInfo);
    }

    @Override
    public StudentInfo editStudent(StudentInfo studentInfo) {
        return iStudentInfoDAO.save(studentInfo);
    }


    @Override
    public StudentInfo editStudentPassword(StudentInfo studentInfo, String password) {

        studentInfo.setPassword(password);

        iStudentInfoDAO.save(studentInfo);

        return studentInfo;
    }

    @Override
    public StudentInfo stuDeposit(StudentInfo studentInfo, BigDecimal amount) {

        studentInfo.setBalance(studentInfo.getBalance().add(amount));

        iStudentInfoDAO.save(studentInfo);

        return studentInfo;
    }

    @Override
    public StudentInfo deleteStudent(StudentInfo studentInfo) {

        iStudentInfoDAO.delete(studentInfo);

        return studentInfo;
    }

    @Override
    public StudentInfo findByName(String username) {
        //List<User> user = userDAO.findAll();
        for (StudentInfo singleUser : iStudentInfoDAO.findAll()) {
            // log.info(singleUser.toString());
            if (Objects.equals(singleUser.getStudentName(), username)) {
                log.info(QUERY_SUCCESS.getMessage());
                return singleUser;
            }
        }
        return null;
    }

    @Override
    public StudentInfo findById(String userId) {
        //List<User> user = userDAO.findAll();
        for (StudentInfo singleUser : iStudentInfoDAO.findAll()) {
            // log.info(singleUser.toString());
            if (Objects.equals(singleUser.getStudentId(), userId)) {
                log.info(QUERY_SUCCESS.getMessage());
                return singleUser;
            }
        }
        return null;
    }

    @Override
    public void save(StudentInfo user) {
        iStudentInfoDAO.save(user);
    }
}
