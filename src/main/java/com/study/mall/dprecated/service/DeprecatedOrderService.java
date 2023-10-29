package com.study.mall.dprecated.service;

import com.study.mall.dprecated.state.DeprecatedOrder;
import com.study.mall.dprecated.state.DeprecatedOrderConText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author peng
 * @version 1.0
 * @description TODO
 * @date 2023/10/28 12:32
 */
@Service
public class DeprecatedOrderService {
    @Autowired
    private DeprecatedOrderConText orderConText;

    public DeprecatedOrder createOrder(String productId) {
        //订单ID的生成逻辑
        String orderId = "OID" + productId;
        return orderConText.createOrder(orderId,productId);
    }

    public DeprecatedOrder pay(String orderId) {
        return orderConText.payOrder(orderId);
    }

    public DeprecatedOrder send(String orderId) {
        return orderConText.sendOrder(orderId);
    }

    public DeprecatedOrder receive(String orderId) {
        return orderConText.receiveOrder(orderId);
    }

}
