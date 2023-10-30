package com.study.mall.bridge.function;

import com.study.mall.pojo.UserInfo;
import com.study.mall.repo.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author peng
 * @version 1.0
 * @description
 * @date 2023/10/22 9:13
 */
public abstract class AbstractRegisterLoginFunc implements RegisterLoginFuncInterface {

    //新增 commonLogin方法，新增 userRepository参数，代码逻辑与子类中的 login方法完全一致。 protected，仅供子类使用
    protected String commonLogin(String account, String password, UserRepository userRepository){
        UserInfo userInfo = userRepository.findByUserNameAndUserPassword(account, password);
        if (userInfo == null){
            return "account / password ERROR";
        }
        return "login  SUCCESS";
    }

    protected String commonRegister(UserInfo userInfo, UserRepository userRepository) {
        if (commonCheckUserExists(userInfo.getUserName(), userRepository)){
            throw new RuntimeException("User already registered.");
        }
        userInfo.setCreateDate(new Date());
        userRepository.save(userInfo);
        return "Register Success !";
    }

    protected boolean commonCheckUserExists(String userName, UserRepository userRepository) {
        UserInfo user = userRepository.findByUserName(userName);
        if (user == null) {
            return false;
        }
        return true;
    }

    @Override
    public String login(String account, String password) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String register(UserInfo userInfo) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean checkUserExists(String userName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String login3rd(HttpServletRequest request) {
        throw new UnsupportedOperationException();
    }
}
