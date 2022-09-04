package com.dyf.dao;

import com.dyf.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderMasterDAO extends JpaRepository<OrderMaster,String>
{
    List<OrderMaster> queryByStudentId(String studentId);
    Page<OrderMaster> queryByStudentId(String studentId, Pageable pageable);
}
