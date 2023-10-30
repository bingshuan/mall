package com.study.mall.bridge.function;

import com.study.mall.bridge.abst.RegisterLoginComponentFactory;
import com.study.mall.pojo.UserInfo;
import com.study.mall.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author peng
 * @version 1.0
 * @description 承载核心方法的具体子类 ——支持已有的账号密码登录
 * @date 2023/10/21 13:56
 */
@Component
public class RegisterLoginByDefault extends AbstractRegisterLoginFunc implements RegisterLoginFuncInterface {
    @Autowired
    private UserRepository userRepository;

    //spirngBoot启动时，首先扫描类上的@Component注解，将 RegisterLoginByDefault注入到Bean容器中；
    // 然后立即调用标注有 @PostConstruct的initFuncMap方法，完成funcMap 的初始化
    @PostConstruct
    private void initFuncMap() {
        RegisterLoginComponentFactory.funcMap.put("Default",this);
    }

    //此处为‘重构’ login方法，仅仅将之前 userService的逻辑完全复制过来即可
    @Override
    public String login(String account, String password) {
        return super.commonLogin(account,password,userRepository);
    }

    //此处为‘重构’ register方法，仅仅将之前 userService的逻辑完全复制过来即可
    @Override
    public String register(UserInfo userInfo) {
      return super.commonRegister(userInfo,userRepository);
    }

    //此处为‘重构’ checkUserExists方法，仅仅将之前 userService的逻辑完全复制过来即可
    @Override
    public boolean checkUserExists(String userName) {
        return super.commonCheckUserExists(userName,userRepository);
    }

}
