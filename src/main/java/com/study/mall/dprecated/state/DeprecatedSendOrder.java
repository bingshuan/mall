package com.study.mall.dprecated.state;

import com.study.mall.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author peng
 * @version 1.0
 * @description 具体状态类 ——发送订单
 * @date 2023/10/28 11:02
 */
@Component
public class DeprecatedSendOrder extends DeprecatedAbstractOrderState {
    @Autowired
    private RedisCommonProcessor redisProcessor;

    /**
     * 支付订单完成后的下一个状态： 待收货
     */
    @Autowired
    private DeprecatedReceiveOrder deprecatedReceiveOrder;

    @Override
    protected DeprecatedOrder sendOrder(String orderId, DeprecatedOrderConText context) {
        //从redis中取出当前订单，并判断当前订单状态是否为待发货状态
        DeprecatedOrder order = (DeprecatedOrder) redisProcessor.get(orderId);
        if (! order.getState().equals(ORDER_WAIT_SEND)) {
            throw new RuntimeException("Order state should be ORDER_WAIT_SEND" +
                    ".But now it is state is :" + order.getState());
        }
        //点击发货后，修改订单状态为待收货，并更新redis
        order.setState(ORDER_WAIT_RECEIVE);
        redisProcessor.set(orderId,order);
        //观察者模式： 发送订单支付Event
        super.notifyObserver(orderId, ORDER_WAIT_RECEIVE);
        //订单支付成功，设置Context上下文角色的 currentState为待发货状态
        context.setCurrentState(this.deprecatedReceiveOrder);
        return order;
    }
}
