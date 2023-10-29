package com.study.mall.dprecated.state;

import com.study.mall.dprecated.DeprecatedConstants;
import com.study.mall.dprecated.observer.DeprecatedAbstractObserver;

import java.util.List;

/**
 * @author peng
 * @version 1.0
 * @description state抽取状态角色
 * @date 2023/10/28 10:50
 */
public abstract class DeprecatedAbstractOrderState {
    //订单状态定义，待支付、待发货、待收货、订单完成
    protected final String ORDER_WAIT_PAY = "ORDER_WAIT_PAY";
    protected final String ORDER_WAIT_SEND = "ORDER_WAIT_SEND";
    protected final String ORDER_WAIT_RECEIVE = "ORDER_WAIT_RECEIVE";
    protected final String ORDER_FINISH = "ORDER_FINISH";

    //订单方法定义—— 创建订单
    protected DeprecatedOrder createOrder(String orderId, String productId, DeprecatedOrderConText context) {
        throw new UnsupportedOperationException();
    }
    //订单方法定义—— 订单支付
    protected DeprecatedOrder payOrder(String orderId, DeprecatedOrderConText context) {
        throw new UnsupportedOperationException();
    }
    //订单方法定义—— 订单发送
    protected DeprecatedOrder sendOrder(String orderId, DeprecatedOrderConText context) {
        throw new UnsupportedOperationException();
    }
    //订单方法定义—— 订单签收
    protected DeprecatedOrder receiveOrder(String orderId, DeprecatedOrderConText context) {
        throw new UnsupportedOperationException();
    }

    /**
     * 关联抽象观察者，以list的形式进行关联，因为要支持观察者的添加和移除操作
     */
    protected final List<DeprecatedAbstractObserver> observerList = DeprecatedConstants.OBSERVER_LIST;

    /**
     * 新增观察者
     */
    public void addObserver(DeprecatedAbstractObserver observer) {
        this.observerList.add(observer);
    }

    /**
     * 移除观察者
     */
    public void removeObserver(DeprecatedAbstractObserver observer) {
        this.observerList.remove(observer);
    }

    /**
     * 通知观察者进行相关操作，并调用 orderStateHandle 方法
     * @param orderId
     * @param orderState
     */
    public void notifyObserver(String orderId, String orderState) {
        for (DeprecatedAbstractObserver observer : this.observerList) {
            observer.orderStateHandle(orderId, orderState);
        }
    }

}
