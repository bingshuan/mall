package com.study.mall.dprecated.state;

import com.study.mall.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author peng
 * @version 1.0
 * @description 具体状态类 ——创建订单
 * @date 2023/10/28 11:02
 */
@Component
public class DeprecatedCreateOrder extends DeprecatedAbstractOrderState {
    @Autowired
    private RedisCommonProcessor redisProcessor;

    /**
     * 订单创建完成后的下一个状态： 待支付
     */
    @Autowired
    private DeprecatedPayOrder deprecatedPayOrder;

    @Override
    public DeprecatedOrder createOrder(String orderId, String productId, DeprecatedOrderConText context) {
        //创建订单对象，设置状态 ORDER_WAIT_PAY
        DeprecatedOrder order = DeprecatedOrder.builder()
                .orderId(orderId)
                .productId(productId)
                .state(ORDER_WAIT_PAY)
                .build();
        //将新订单存入 redis缓存，15分钟后失效
        redisProcessor.set(orderId, order, 900);
        //观察者模式：发送订单创建event
        super.notifyObserver(orderId, ORDER_WAIT_PAY);
        //订单创建完成，设置context上下文角色的 currentState状态为待支付状态
        context.setCurrentState(this.deprecatedPayOrder);
        return order;
    }
}
