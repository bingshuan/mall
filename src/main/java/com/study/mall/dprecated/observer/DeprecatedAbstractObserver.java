package com.study.mall.dprecated.observer;

/**
 * @author peng
 * @version 1.0
 * @description 抽象观察者
 * @date 2023/10/29 9:28
 */
public abstract class DeprecatedAbstractObserver {
    /**
     * 订单状态发生变更时，调用此方法
     */
    public abstract void orderStateHandle(String orderId, String orderState);
}
