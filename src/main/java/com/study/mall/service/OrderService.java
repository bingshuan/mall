package com.study.mall.service;

import com.study.mall.ordermanagement.state.OrderState;
import com.study.mall.ordermanagement.state.OrderStateChangeAction;
import com.study.mall.pojo.Order;
import com.study.mall.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

/**
 * @description 订单类
 * @author peng
 * @date 2023/11/15 22:20
 * @version 1.0
 */
@Service
public class OrderService {
    //依赖注入spring状态机
    @Autowired
    private StateMachine<OrderState, OrderStateChangeAction> orderStateMachine;

    //依赖注入spring状态机的存取工具，持久化状态机
    @Autowired
    private StateMachinePersister<OrderState,OrderStateChangeAction,String> stateMachinePersister;

    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    //订单创建
    public Order createOrder(String productId) {
        //此处orderId，需要生成全局的唯一ID，在4.4.2.1小节，笔者已经做过详细引申
        String orderId = "OID"+productId;
        //创建订单并存储到Redis
        Order order = Order.builder()
                .orderId(orderId)
                .productId(productId)
                .orderState(OrderState.ORDER_WAIT_PAY)
                .build();
        redisCommonProcessor.set(order.getOrderId(), order, 900);
        return order;
    }

    //订单支付
    public Order pay(String orderId) {
        //从Redis中获取 订单
        Order order = (Order) redisCommonProcessor.get(orderId);
        //包装 订单状态变更 Message，并附带订单操作 PAY_ORDER
        Message message = MessageBuilder
                .withPayload(OrderStateChangeAction.PAY_ORDER).setHeader("order", order).build();
        //将Message传递给Spring状态机
        if(changeStateAction(message,order)) {
            return order;
        }
        return null;
    }
    //订单发送
    public Order send(String orderId) {
        //从Redis中获取 订单
        Order order = (Order) redisCommonProcessor.get(orderId);
        //包装 订单状态变更 Message，并附带订单操作 SEND_ORDER
        Message message = MessageBuilder
                .withPayload(OrderStateChangeAction.SEND_ORDER).setHeader("order", order).build();
        //将Message传递给Spring状态机
        if(changeStateAction(message,order)) {
            return order;
        }
        return null;
    }
    //订单签收
    public Order receive(String orderId) {
        //从Redis中获取 订单
        Order order = (Order) redisCommonProcessor.get(orderId);
        //包装 订单状态变更 Message，并附带订单操作 RECEIVE_ORDER
        Message message = MessageBuilder
                .withPayload(OrderStateChangeAction.RECEIVE_ORDER).setHeader("order", order).build();
        //将Message传递给Spring状态机
        if(changeStateAction(message,order)) {
            return order;
        }
        return null;
    }

    //状态机的相关操作
    private boolean changeStateAction(Message<OrderStateChangeAction>  message, Order order) {
        try {
            //启动状态机
            orderStateMachine.start();
            //从redis中读取状态机，缓存的key为 orderId+"STATE"
            stateMachinePersister.restore(orderStateMachine,order.getOrderId()+"STATE");
            //将message发送给 orderStateListener
            boolean res = orderStateMachine.sendEvent(message);
            //将更改完订单状态的 状态机 存储到 redis缓存
            stateMachinePersister.persist(orderStateMachine,order.getOrderId()+"STATE");
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            orderStateMachine.stop();
        }
        return false;
    }

}
