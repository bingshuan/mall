package com.study.mall.dprecated.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author peng
 * @version 1.0
 * @description ConText上下文角色
 * @date 2023/10/28 11:11
 */
@Component
public class DeprecatedOrderConText {
    /**
     * 引入抽象状态角色，用于状态方法的调用
     */
    private DeprecatedAbstractOrderState currentState;

    /**
     * 新创建订单的初始状态
     */
    @Autowired
    private DeprecatedCreateOrder deprecatedCreateOrder;

    public void setCurrentState(DeprecatedAbstractOrderState currentState) {
        this.currentState = currentState;
    }

    //创建订单的方法入口，直接调用状态类的方法
    public DeprecatedOrder createOrder(String orderId, String productId) {
        this.currentState = this.deprecatedCreateOrder;
        DeprecatedOrder order = currentState.createOrder(orderId, productId, this);
        return order;
    }
    //支付订单的方法入口，直接调用状态类的方法
    public DeprecatedOrder payOrder(String orderId) {
        DeprecatedOrder order = currentState.payOrder(orderId, this);
        return order;
    }
    //发送订单的方法入口，直接调用状态类的方法
    public DeprecatedOrder sendOrder(String orderId) {
        DeprecatedOrder order = currentState.sendOrder(orderId, this);
        return order;
    }
    //接收订单的方法入口，直接调用状态类的方法
    public DeprecatedOrder receiveOrder(String orderId) {
        DeprecatedOrder order = currentState.receiveOrder(orderId, this);
        return order;
    }

}
