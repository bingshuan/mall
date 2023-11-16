package com.study.mall.ordermanagement.listener;

import com.study.mall.ordermanagement.state.OrderState;
import com.study.mall.ordermanagement.state.OrderStateChangeAction;
import com.study.mall.pojo.Order;
import com.study.mall.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

/**
 * @description  观察者模式实战—— 基于状态机
 * WithStateMachine 监听注解
 * @author peng
 * @date 2023/11/15 21:49
 * @version 1.0
 */
@Component
@WithStateMachine(name = "orderStateMachine")
public class OrderStateListener {
    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @OnTransition(source = "ORDER_WAIT_PAY",target = "ORDER_WAIT_SEND")
    public boolean payToSend(Message<OrderStateChangeAction> message) {
        //从redis中获取订单，并判断订单状态是否为待支付
        Order order = (Order) message.getHeaders().get("order");
        if (order.getOrderState() != OrderState.ORDER_WAIT_PAY) {
            throw new UnsupportedOperationException("Order state error!");
        }
        //支付成功后，修改订单状态为待发货，并进行redis更新
        order.setOrderState(OrderState.ORDER_WAIT_SEND);
        redisCommonProcessor.set(order.getOrderId(),order);
        //命令模式进行相关处理
        return true;
    }

    @OnTransition(source = "ORDER_WAIT_SEND", target = "ORDER_WAIT_RECEIVE")
    public boolean sendToReceive(Message<OrderStateChangeAction> message){
        Order order = (Order) message.getHeaders().get("order");
        if(order.getOrderState() != OrderState.ORDER_WAIT_SEND) {
            throw new UnsupportedOperationException("Order state error!");
        }
        order.setOrderState(OrderState.ORDER_WAIT_RECEIVE);
        redisCommonProcessor.set(order.getOrderId(), order);
        //命令模式进行相关处理（本章4.10节和4.11节进行实现）
        return true;
    }
    @OnTransition(source = "ORDER_WAIT_RECEIVE", target = "ORDER_FINISH")
    public boolean receiveToFinish(Message<OrderStateChangeAction> message){
        Order order = (Order) message.getHeaders().get("order");
        if(order.getOrderState() != OrderState.ORDER_WAIT_RECEIVE) {
            throw new UnsupportedOperationException("Order state error!");
        }
        order.setOrderState(OrderState.ORDER_FINISH);
        redisCommonProcessor.remove(order.getOrderId());
        //命令模式进行相关处理（本章4.10节和4.11节进行实现）
        return true;
    }

}
