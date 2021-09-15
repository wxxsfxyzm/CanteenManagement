package com.dyf.service.impl;



import com.dyf.dto.OrderDTO;
import com.dyf.dto.StudentDTO;
import com.dyf.service.IOrderService;
import com.dyf.service.IStudentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class StudentServiceImplTest {

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private IOrderService iOrderService;

    @Test
    public void findByStudentId() {
        StudentDTO studentDTO = iStudentService.findByStudentId("1001");

        Assert.assertNotNull(studentDTO);
    }

    @Test
    public void pay() {
        OrderDTO orderDTO = iOrderService.findById("1630648691339533584");

        StudentDTO studentDTO = iStudentService.pay(orderDTO);

        Assert.assertNotNull(studentDTO);
    }

}