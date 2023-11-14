package com.study.mall.ordermanagement.state;

/**
 * @description 订单状态枚举类
 * @author peng
 * @date 2023/11/14 20:18
 * @version 1.0
 */
public enum OrderState {
    ORDER_WAIT_PAY,
    ORDER_WAIT_SEND,
    ORDER_WAIT_RECEIVE,
    ORDER_FINISH;
}
