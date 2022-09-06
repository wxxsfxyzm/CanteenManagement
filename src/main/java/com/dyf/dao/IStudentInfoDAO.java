package com.dyf.dao;

import com.dyf.entity.StudentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStudentInfoDAO extends JpaRepository<StudentInfo, String> {
    StudentInfo queryById(String studentId);

    StudentInfo findByPhoneNumber(String phone);

    Page<StudentInfo> findByIdLike(String studentId, Pageable pageable);

    Page<StudentInfo> findByUserNameLike(String studentName, Pageable pageable);


}
