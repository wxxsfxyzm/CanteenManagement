package com.dyf.dao;

import com.dyf.entity.StudentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IStudentInfoDAO extends JpaRepository<StudentInfo, String>
{
    StudentInfo findByStudentId(String studentId);

    Page<StudentInfo> findByStudentIdLike(String studentId, Pageable pageable);

    Page<StudentInfo> findByStudentNameLike(String studentName, Pageable pageable);


}
