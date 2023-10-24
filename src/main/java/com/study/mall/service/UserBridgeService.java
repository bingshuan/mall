package com.study.mall.service;

import com.study.mall.bridge.abst.AbstractRegisterLoginComponent;
import com.study.mall.bridge.abst.RegisterLoginComponentFactory;
import com.study.mall.pojo.UserInfo;
import com.study.mall.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author peng
 * @version 1.0
 * @description 桥接模式
 * @date 2023/10/15 22:14
 */
@Service
public class UserBridgeService {
    @Autowired
    private UserRepository userRepository;

    public String login(String account,String password) {
        //用左路的具体子类创建调用入口
//        AbstractRegisterLoginComponent registerLoginComponent =
//                new RegisterLoginComponent(new RegisterLoginByDefault());
//        return registerLoginComponent.login(account,password);
        AbstractRegisterLoginComponent component =
                RegisterLoginComponentFactory.getComponent("Default");
        return component.login(account,password);
    }

    public String register(UserInfo userInfo){
        AbstractRegisterLoginComponent component =
                RegisterLoginComponentFactory.getComponent("Default");
        return component.register(userInfo);
    }


    public boolean checkUserExists(String userName){
        AbstractRegisterLoginComponent component =
                RegisterLoginComponentFactory.getComponent("Default");
        return component.checkUserExists(userName);
    }

    public String login3rd(HttpServletRequest request, String type) {
        AbstractRegisterLoginComponent component =
                RegisterLoginComponentFactory.getComponent(type);
        return component.login3rd(request);
    }
}
