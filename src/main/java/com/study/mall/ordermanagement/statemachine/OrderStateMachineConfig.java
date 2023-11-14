package com.study.mall.ordermanagement.statemachine;

import com.study.mall.ordermanagement.state.OrderState;
import com.study.mall.ordermanagement.state.OrderStateChangeAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.RepositoryStateMachinePersist;
import org.springframework.statemachine.redis.RedisStateMachineContextRepository;
import org.springframework.statemachine.redis.RedisStateMachinePersister;

import java.util.EnumSet;

/**
 * @description spring状态机配置类
 * @author peng
 * @date 2023/11/14 20:35
 * @version 1.0
 */
@Configuration
@EnableStateMachine(name = "orderStateMachine")
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderState, OrderStateChangeAction> {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean(name = "stateMachineRedisPersister")
    public RedisStateMachinePersister<OrderState, OrderStateChangeAction> getRedisPersister() {
        RedisStateMachineContextRepository<OrderState, OrderStateChangeAction> repository
                = new RedisStateMachineContextRepository<>(redisConnectionFactory);
        RepositoryStateMachinePersist persist
                = new RepositoryStateMachinePersist<>(repository);
        return new RedisStateMachinePersister<>(persist);
    }

    /**
     * 覆写 configure方法，进行状态机的初始化操作
     */
    @Override
    public void configure(StateMachineStateConfigurer<OrderState, OrderStateChangeAction> states) throws Exception {
        //设置订单创建成功后的初始状态为待支付 ORDER_WAIT_PAY
        states.withStates().initial(OrderState.ORDER_WAIT_PAY)
                //将订单的所有状态，加载到状态机中
                .states(EnumSet.allOf(OrderState.class));
    }

    /**
     * 配置订单状态的转换流程
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<OrderState, OrderStateChangeAction> transitions) throws Exception {
        transitions.withExternal()
                //从ORDER_WAIT_PAY到ORDER_WAIT_SEND状态，需要PAY_ORDER操作
                .source(OrderState.ORDER_WAIT_PAY)
                .target(OrderState.ORDER_WAIT_SEND)
                .event(OrderStateChangeAction.PAY_ORDER)
                .and().withExternal()
                .source(OrderState.ORDER_WAIT_SEND)
                .target(OrderState.ORDER_WAIT_RECEIVE)
                .event(OrderStateChangeAction.SEND_ORDER)
                .and().withExternal()
                .source(OrderState.ORDER_WAIT_RECEIVE)
                .target(OrderState.ORDER_FINISH)
                .event(OrderStateChangeAction.RECEIVE_ORDER);
    }

}
