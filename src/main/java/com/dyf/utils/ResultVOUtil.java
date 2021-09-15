package com.dyf.utils;

import com.dyf.dto.OrderDTO;
import com.dyf.entity.StudentInfo;
import com.dyf.vo.OrderMasterVO;
import com.dyf.vo.ResultVO;
import org.springframework.lang.Nullable;

import java.util.List;

public class ResultVOUtil {
    /**
     * 支付 成功
     *
     * @param object 一个对象/集合
     * @return JSON
     */
    public static ResultVO paySuccess(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("支付成功");
        resultVO.setData(object);
        return resultVO;
    }

    /**
     * 查询 成功
     *
     * @param object 一个对象/集合
     * @return JSON
     */
    public static ResultVO querySuccess(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("查询成功");
        resultVO.setData(object);
        return resultVO;
    }


    public static OrderMasterVO queryOrderMasterSuccess(List<OrderDTO> response, StudentInfo studentInfo) {
        OrderMasterVO orderMasterVO = new OrderMasterVO();

        orderMasterVO.setCode(0);
        orderMasterVO.setMsg("查询成功");
        orderMasterVO.setStudentId(studentInfo.getStudentId());
        orderMasterVO.setStudentBalance(studentInfo.getBalance());
        orderMasterVO.setStudentName(studentInfo.getStudentName());

        orderMasterVO.setOrderDTOList(response);

        return orderMasterVO;
    }

    public static ResultVO addSuccess(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("添加成功");
        return resultVO;
    }
/*    public static ResultVO editSuccess(Object object)
    {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("编辑成功");
        return resultVO;
    }*/


    //TODO 封装工具类


    /**
     * 成功
     *
     * @param object 传入一个对象
     * @return JSON
     */
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
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
    public static ResultVO success(@Nullable String msg, @Nullable Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
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
    public static ResultVO success(Integer code, String msg) {
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


    public static ResultVO depositSuccess() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("充值成功");
        return resultVO;
    }

    public static ResultVO deleteSuccess() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("删除成功");
        return resultVO;
    }

    public static ResultVO createSuccess(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("创建订单成功");
        return resultVO;
    }

    /**
     * 支付 失败
     * 余额 不足
     *
     * @param object 一个对象/集合
     * @return JSON
     */
    public static ResultVO BalanceInsufficient(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg("余额不足");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO queryFail() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg("查询失败");
        return resultVO;
    }

    public static OrderMasterVO queryOrderMasterFail() {
        OrderMasterVO orderMasterVO = new OrderMasterVO();
        orderMasterVO.setCode(1);
        orderMasterVO.setMsg("查询失败");
        return orderMasterVO;
    }

    public static ResultVO editFail() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg("修改失败");
        return resultVO;
    }

    public static ResultVO addFail() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg("添加失败");
        return resultVO;
    }

    public static ResultVO depositFail() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg("充值失败");
        return resultVO;
    }

    public static ResultVO deleteFail() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg("删除学生失败");
        return resultVO;
    }

    public static ResultVO createFail() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg("创建订单失败");
        return resultVO;
    }

    public static ResultVO wrongPassword() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(2);
        resultVO.setMsg("密码错误");
        return resultVO;
    }
}
