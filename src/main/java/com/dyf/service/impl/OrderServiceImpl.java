package com.dyf.service.impl;

import com.dyf.convert.OrderMaster2OrderDTOConverter;
import com.dyf.dao.IOrderDetailDAO;
import com.dyf.dao.IOrderMasterDAO;
import com.dyf.dao.IOrdersDAO;
import com.dyf.dao.IStudentInfoDAO;
import com.dyf.dto.Order2DTO;
import com.dyf.dto.OrderDTO;
import com.dyf.entity.FoodInfo;
import com.dyf.entity.OrderDetail;
import com.dyf.entity.OrderMaster;
import com.dyf.entity.Orders;
import com.dyf.enums.ResultEnum;
import com.dyf.exception.SellException;
import com.dyf.service.IFoodInfoService;
import com.dyf.service.IOrderService;
import com.dyf.service.IStudentService;
import com.dyf.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.dyf.enums.ResultEnum.QUERY_SUCCESS;

@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IFoodInfoService iFoodInfoService;

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private IStudentInfoDAO iStudentInfoDAO;

    @Autowired
    private IOrderMasterDAO iOrderMasterDAO;

    @Autowired
    private IOrderDetailDAO iOrderDetailDAO;

    @Autowired
    private IOrdersDAO iOrdersDAO;


    @Override
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();        //生成orderId

        BigDecimal orderTotalPrice = new BigDecimal(BigInteger.ZERO);

        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            FoodInfo foodInfo = iFoodInfoService.findById(orderDetail.getFoodId());

            if (foodInfo == null) {
                throw new SellException(ResultEnum.FOOD_NOT_EXIST);
            }

            //订单总价
            orderTotalPrice = foodInfo.getFoodPrice().multiply(new BigDecimal(orderDetail.getQuantity())).add(orderTotalPrice);

            //订单明细入库
            orderDetail.setDetailId((KeyUtil.genUniqueKey()));
            orderDetail.setOrderId((orderId));
            orderDetail.setFoodName(foodInfo.getFoodName());

            BeanUtils.copyProperties(foodInfo, orderDetail);
            iOrderDetailDAO.save(orderDetail);
        }

        /** 创建订单 */
        OrderMaster orderMaster = new OrderMaster();

        orderDTO.setOrderId(orderId);

        BeanUtils.copyProperties(orderDTO, orderMaster);

        orderMaster.setOrderAmount(orderTotalPrice);
        iOrderMasterDAO.save(orderMaster);

        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    @Override
    public void create(Order2DTO order2DTO) {

        Orders orders = new Orders();

//        orders.setOrderId(order2DTO.getOrderId());
//
//        orders.setGoodsId(order2DTO.getGoodsId());
//
//        orders.setGoodsName(order2DTO.getGoodsName());
//
//        orders.setCreateTime(order2DTO.getCreateTime());

        BeanUtils.copyProperties(order2DTO, orders);

        iOrdersDAO.save(orders);


    }

    @Override
    public OrderDTO findById(String orderId) {

        OrderMaster orderMaster = iOrderMasterDAO.findById(orderId).orElse(null);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.FOOD_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = iOrderDetailDAO.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public List<Orders> findListByUserId(String userId) {
        List<Orders> ordersList;
        ordersList = iOrdersDAO.findByUserId(userId);


        return ordersList;
    }

    @Override
    public List<Orders> findListByUserId(String userId, Integer status) {
        List<Orders> ordersList = new ArrayList<>();
        for (Orders singleOrder : iOrdersDAO.findByUserId(userId)) {
            if (Objects.equals(singleOrder.getGoodsStatus(), status)) {
                log.info(QUERY_SUCCESS.getMessage());
                ordersList.add(singleOrder);
            }
        }

        return ordersList;
    }

    @Override
    public List<Orders> findListByOrderId(String orderId) {
        List<Orders> ordersList = new ArrayList<>();
        for (Orders singleOrder : iOrdersDAO.findByOrderId(orderId)) {
            if (Objects.equals(singleOrder.getGoodsStatus(), 0)) {
                log.info(QUERY_SUCCESS.getMessage());
                ordersList.add(singleOrder);
            }
        }
        return ordersList;
    }

    /**
     * 返回值为page
     */
    @Override
    public Page<OrderDTO> findList(String studentId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = iOrderMasterDAO.queryByStudentId(studentId, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        // 带分页 查询所有的 订单列表
        Page<OrderMaster> orderMasterPageList = iOrderMasterDAO.findAll(pageable);

        // 将OrderMaster 转换成 OrderDTO
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPageList.getContent());
        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPageList.getTotalElements());
    }

    /**
     * 返回值为list
     */
    @Override
    public List<OrderDTO> findList(String studentId) {
        List<OrderMaster> orderMasterList = iOrderMasterDAO.queryByStudentId(studentId);

        // 用转换器 将OrderMaster转换成OrderDTO
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterList);

        for (OrderDTO orderDTO : orderDTOList) {

            List<OrderDetail> orderDetailList = iOrderDetailDAO.findByOrderId(orderDTO.getOrderId());

            orderDTO.setOrderDetailList(orderDetailList);
        }
        return orderDTOList;

    }


}
