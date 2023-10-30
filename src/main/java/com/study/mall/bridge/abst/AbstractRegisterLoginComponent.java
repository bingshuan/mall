package com.study.mall.bridge.abst;

import com.study.mall.bridge.function.RegisterLoginFuncInterface;
import com.study.mall.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author peng
 * @version 1.0
 * @description 抽象角色——为调用端提供方法调用入口
 * @date 2023/10/21 14:15
 */
public abstract class AbstractRegisterLoginComponent {
    //面向接口编程，引入 RegisterLoginFuncInterface 接口属性。此处为 ‘桥’之所在
    protected RegisterLoginFuncInterface funcInterface;
    //通过有参构造函数，初始化 RegisterLoginFuncInterface属性
    public AbstractRegisterLoginComponent(RegisterLoginFuncInterface funcInterface) {
        //校验构造函数入参为 RegisterLoginFuncInterface类型，且不为null
        validate(funcInterface);
        this.funcInterface = funcInterface;
    }
    //校验参数为 RegisterLoginFuncInterface类型且不为null, final方法，不允许子类覆盖
    protected final void validate(RegisterLoginFuncInterface funcInterface) {
        if (!(funcInterface instanceof RegisterLoginFuncInterface)) {
            throw new UnsupportedOperationException(" Unknown register/ login function type");
        }
    }

    public abstract String login(String username, String password);
    public abstract String register(UserInfo userInfo);
    public abstract boolean checkUserExists(String userName);
    public abstract String login3rd(HttpServletRequest request);
}
