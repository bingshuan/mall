package com.study.mall.pojo;

import com.study.mall.ordermanagement.state.OrderState;
import lombok.*;

/**
 * @description 订单对象
 * @author peng
 * @date 2023/11/14 20:26
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    private String orderId;
    private String productId;
    private OrderState orderState; //订单状态
    private Float price; //商品价格
}
