package com.dyf.utils;

import com.dyf.dto.OrderDTO;
import com.dyf.entity.StudentInfo;
import com.dyf.vo.OrderMasterVO;
import com.dyf.vo.ResultVO;
import org.springframework.lang.Nullable;

import java.util.List;

import static com.dyf.constant.Constant.*;

public class ResultVOUtil {
    /**
     * 查询成功
     *
     * @param response    返回列表
     * @param studentInfo 单个学生信息
     * @return JSON
     */
    public static OrderMasterVO queryOrderMasterSuccess(
            List<OrderDTO> response,
            StudentInfo studentInfo) {
        OrderMasterVO orderMasterVO = new OrderMasterVO();

        orderMasterVO.setCode(SUCCESS);
        orderMasterVO.setMsg(QUERY_SUCCESS);
        orderMasterVO.setStudentId(studentInfo.getStudentId());
        orderMasterVO.setStudentBalance(studentInfo.getBalance());
        orderMasterVO.setStudentName(studentInfo.getStudentName());

        orderMasterVO.setOrderDTOList(response);

        return orderMasterVO;
    }

    /**
     * 成功
     *
     * @param object 传入一个实例
     * @return JSON
     */
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(SUCCESS);
        resultVO.setMsg(SUCCEED);
        resultVO.setData(object);
        return resultVO;
    }

    /**
     * 成功
     *
     * @param msg    信息
     * @param object 传入一个对象
     * @return JSON
     */
    public static ResultVO success(
            @Nullable String msg,
            @Nullable Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(SUCCESS);
        resultVO.setMsg(msg);
        resultVO.setData(object);
        return resultVO;
    }

    /**
     * 成功
     *
     * @param code code
     * @param msg  信息
     * @return JSON
     */
    public static ResultVO success(
            @Nullable Integer code,
            String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    /**
     * 失败
     *
     * @param code code
     * @param msg  信息
     * @return JSON
     */
    public static ResultVO fail(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    /**
     * 失败
     *
     * @param code   错误代码
     * @param msg    信息
     * @param object 一个实例
     * @return JSON
     */
    public static ResultVO fail(
            Integer code,
            String msg,
            Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        resultVO.setData(object);
        return resultVO;
    }

    /**
     * 查询订单失败
     *
     * @return orderMasterVO
     */
    public static OrderMasterVO queryOrderMasterFail() {
        OrderMasterVO orderMasterVO = new OrderMasterVO();
        orderMasterVO.setCode(1);
        orderMasterVO.setMsg(QUERY_FAIL);
        return orderMasterVO;
    }

}
