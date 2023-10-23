package com.study.mall.controller;


import com.study.mall.adapter.Login3rdAdapter;
import com.study.mall.pojo.UserInfo;
import com.study.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author peng
 * @version 1.0
 * @description TODO
 * @date 2023/10/15 22:13
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private Login3rdAdapter login3rdAdapter;

    //gitee平台回调接口，并携带code、state参数
    @GetMapping("/gitee")
    public String gitee(String code, String state)throws IOException {
        return login3rdAdapter.loginByGitee(code,state);
    }

    @PostMapping("/login")
    public String login(String account, String password){
        return userService.login(account, password);
    }

    @PostMapping("/register")
    public String register(@RequestBody UserInfo userInfo){
        return userService.register(userInfo);
    }
}
