package com.study.mall.dprecated.state;

import com.study.mall.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author peng
 * @version 1.0
 * @description 具体状态类 ——订单完成
 * @date 2023/10/28 11:02
 */
@Component
public class DeprecatedReceiveOrder extends DeprecatedAbstractOrderState {
    @Autowired
    private RedisCommonProcessor redisProcessor;

    @Override
    protected DeprecatedOrder receiveOrder(String orderId, DeprecatedOrderConText context) {
        //从redis中取出当前订单，并判断当前订单状态是否为代收付状态
        DeprecatedOrder order = (DeprecatedOrder) redisProcessor.get(orderId);
        if (! order.getState().equals(ORDER_WAIT_RECEIVE)) {
            throw new RuntimeException("Order state should be ORDER_WAIT_RECEIVE" +
                    ".But now it is state is :" + order.getState());
        }
        //用户收货后，修改订单状态为订单完成状态，并删除redis缓存
        order.setState(ORDER_FINISH);
        //观察者模式：发送订单收货event
        super.notifyObserver(orderId, ORDER_FINISH);
        redisProcessor.remove(orderId);
        return order;
    }
}
