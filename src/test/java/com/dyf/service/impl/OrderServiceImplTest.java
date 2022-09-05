package com.dyf.service.impl;

import com.dyf.convert.OrderMaster2OrderDTOConverter;
import com.dyf.dto.OrderDTO;
import com.dyf.dto.StudentDTO;
import com.dyf.entity.OrderDetail;
import com.dyf.entity.OrderMaster;
import com.dyf.service.IOrderService;
import com.dyf.service.IStudentService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest
{
    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IStudentService iStudentService;

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("1");
        orderDTO.setStudentId("195080703");
        orderDTO.setOrderAmount(BigDecimal.ONE);

        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        OrderDetail o1 = new OrderDetail();
        OrderDetail o2 = new OrderDetail();
        o1.setFoodId("16309072839586634");
        o1.setQuantity(1);

        o2.setFoodId("16309137214122841");
        o2.setQuantity(2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);


        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = iOrderService.create(orderDTO);

        Assert.assertNotNull(result);
    }

    @Test
    public void findById() {
        OrderDTO orderDTO = iOrderService.findById("1630648691339533584");
        log.info("[查询单个订单] orderDTO={}", orderDTO);
        Assert.assertEquals("1630648691339533584", orderDTO.getOrderId());

    }

    @Test
    public void findList() {
        //PageRequest request = PageRequest.of(0,2);
        List<OrderDTO> orderDTOList = iOrderService.findList("1001");

        Assert.assertNotEquals(0,orderDTOList.size());
    }
    @Test
    public void findList02() {
        // 查询第0页的数据 每页显示2条数据
        PageRequest request = PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage = iOrderService.findList("1001", request);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
    }

    @Test
    public  void finish() {
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = iOrderService.findById("1630648691339533584");

        //iOrderService.paid(orderDTO);

        StudentDTO studentDTO = iStudentService.findByStudentId("1001");

        Assert.assertNotNull(studentDTO);
    }

    @Test
    public void testFindList() {
    }

    @Data   //这个是lombok的注解，可省略get、set方法、toString方法这些
    public class Student {
        private String name;  //姓名
        private int age;	//年龄
        private String type; //分类依据
    }

    @Test
    public void GroupingArray() {

            //创建map集合，用于存储分组后的list集合对象
            Map<String, List<Student>> map1 = new HashMap<>();
            Map<String, List<Student>> map2 = new HashMap<>();

            //实例化对象
            Student s1 = new Student();
            s1.setName("张三");
            s1.setAge(19);
            s1.setType("A");
            Student s2 = new Student();
            s2.setName("李四");
            s2.setAge(12);
            s2.setType("A");
            Student s3 = new Student();
            s3.setName("王五");
            s3.setAge(22);
            s3.setType("A");
            Student s4 = new Student();
            s4.setName("小明");
            s4.setAge(12);
            s4.setType("B");
            Student s5 = new Student();
            s5.setName("小红");
            s5.setAge(12);
            s5.setType("B");

            //将实例化对象添加到list集合中
            List<Student> list = new ArrayList<>();
            list.add(s1);
            list.add(s2);
            list.add(s3);
            list.add(s4);
            list.add(s5);
            //打印未分组的list

            System.out.println("未分组前的集合");
            System.out.println(list);
            System.out.println("=============================");
            System.out.println();


            System.out.println("第一种分组方法");
            //使用第一种分组的办法
            map1 = groupingArrayListOne(list);
            //遍历分组后的map1
            traverseMap(map1);

            System.out.println("=============================");

            System.out.println("第二种分组方法");
            //使用第二种分组的办法
            map2 = groupingArrayListTwo(list);
            //遍历分组后的map2
            traverseMap(map2);
        }

        //第一种分组的方法
        public static Map<String, List<Student>> groupingArrayListOne(List<Student> list){
            Map<String, List<Student>> map = new HashMap<>();
            //分组
            for (Student l:list){
                if (map.containsKey(l.getType())) {  //判断是否存在该key
                    map.get(l.getType()).add(l);   //存在就获取该key的value然后add
                } else {
                    List<Student> lt = new ArrayList<>();
                    lt.add(l);
                    map.put(l.getType(), lt);  //不存在就put
                }
            }
            return map;
        }

        //第二种分组的方法
        public static Map<String, List<Student>> groupingArrayListTwo(List<Student> list){

            //使用jdk8的流式编程对list集合进行分组
            Map<String, List<Student>> listMap = list.stream().collect(Collectors.groupingBy(t -> t.getType()));
            return listMap;

        }

        //遍历Map集合
        public static void traverseMap(Map<String,List<Student>> map){
            for (String s:map.keySet()){
                System.out.println(map.get(s));
            }
        }

}