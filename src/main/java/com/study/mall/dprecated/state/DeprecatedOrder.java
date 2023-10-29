package com.study.mall.dprecated.state;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author peng
 * @version 1.0
 * @description DeprecatedOrder对象
 * @date 2023/10/28 11:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeprecatedOrder {
    /**
     * 订单唯一编号
     */
    private String orderId;

    /**
     * 商品信息
     */
    private String productId;

    /**
     * 订单状态
     */
    private String state;

}
