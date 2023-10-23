package com.study.mall.service;

import com.study.mall.pojo.UserInfo;
import com.study.mall.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author peng
 * @version 1.0
 * @description TODO
 * @date 2023/10/15 22:14
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String login(String account,String password) {
        UserInfo userInfo = userRepository.findByUserNameAndUserPassword(account, password);
        if (userInfo == null){
            return "account / password ERROR";
        }
        return "login  SUCCESS";
    }

    public String register(UserInfo userInfo){
        if (checkUserExists(userInfo.getUserName())){
            throw new RuntimeException("User already registered.");
        }
        userInfo.setCreateDate(new Date());
        userRepository.save(userInfo);
        return "Register Success !";
    }


    public boolean checkUserExists(String userName){
        UserInfo user = userRepository.findByUserName(userName);
        if (user == null) {
            return false;
        }
        return true;
    }
}
