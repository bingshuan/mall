package com.study.mall.dprecated.controller;

import com.study.mall.dprecated.service.DeprecatedOrderService;
import com.study.mall.dprecated.state.DeprecatedOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author peng
 * @version 1.0
 * @description 订单状态管理控制层
 * @date 2023/10/28 12:44
 */
@RestController
@RequestMapping("/deprecated/order")
public class DeprecatedOrderController {
    @Autowired
    private DeprecatedOrderService deprecatedOrderService;

    @PostMapping("/create")
    public DeprecatedOrder createOrder(@RequestParam String productId) {
        return deprecatedOrderService.createOrder(productId);
    }

    @PostMapping("/pay")
    public DeprecatedOrder payOrder(@RequestParam String orderId){
        return deprecatedOrderService.pay(orderId);
    }

    @PostMapping("/send")
    public DeprecatedOrder send(@RequestParam String orderId) {
        return deprecatedOrderService.send(orderId);
    }

    @PostMapping("/receive")
    public DeprecatedOrder receive(@RequestParam String orderId) {
        return deprecatedOrderService.receive(orderId);
    }
}
