package com.study.mall.dprecated.observer;

import com.study.mall.dprecated.DeprecatedConstants;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author peng
 * @version 1.0
 * @description 订单创建观察者
 * @date 2023/10/29 9:31
 */
@Component
public class DeprecatedCreateObserver extends DeprecatedAbstractObserver{
    //将自己添加到OBSERVER_LIST中
    //该注解在DeprecatedCreateObserver类初始化完成后，将自己添加到OBSERVER_LIST中
    @PostConstruct
    public void init() {
        DeprecatedConstants.OBSERVER_LIST.add(this);
    }
    @Override
    public void orderStateHandle(String orderId, String orderState) {
        if(!orderState.equals("ORDER_WAIT_PAY")) {
            return;
        }
        //通过命令模式进行后续处理
        System.out.println("监听到 ：订单创建成功。通过命令模式做后续处理。");
    }
}
