package com.study.mall.ordermanagement.state;

/**
 * @Author: pengos
 * @Date: 2023/11/14 20:23
 * @Version: v1.0.0
 * @Description: 订单操作枚举类
 **/
public enum OrderStateChangeAction {
    PAY_ORDER, //支付操作
    SEND_ORDER,   //发货操作
    RECEIVE_ORDER; //收货操作
}
