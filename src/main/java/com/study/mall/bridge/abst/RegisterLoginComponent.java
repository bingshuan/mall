package com.study.mall.bridge.abst;

import com.study.mall.bridge.function.RegisterLoginFuncInterface;
import com.study.mall.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author peng
 * @version 1.0
 * @description TODO
 * @date 2023/10/21 14:13
 */
public class RegisterLoginComponent extends AbstractRegisterLoginComponent {
    //通过构造函数，传入 ‘桥梁’RegisterLoginFuncInterface的具体类型
    public RegisterLoginComponent(RegisterLoginFuncInterface funcInterface) {
        super(funcInterface);
    }

    @Override
    public String login(String username, String password) {
        //直接通过桥梁，调用调用右路 Implementor的方法即可，把具体实现交给右路的实现类
        return funcInterface.login(username, password);
    }

    @Override
    public String register(UserInfo userInfo) {
        return funcInterface.register(userInfo);
    }

    @Override
    public boolean checkUserExists(String userName) {
        return funcInterface.checkUserExists(userName);
    }

    @Override
    public String login3rd(HttpServletRequest request) {
        return funcInterface.login3rd(request);
    }
}
