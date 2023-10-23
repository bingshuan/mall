package com.study.mall.controller;


import com.study.mall.pojo.UserInfo;
import com.study.mall.service.UserBridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author peng
 * @version 1.0
 * @description TODO
 * @date 2023/10/15 22:13
 */
@RestController
@RequestMapping("/bridge")
public class UserBridgeController {

    @Autowired
    private UserBridgeService userBridgeService;

    //gitee平台回调接口，并携带code、state参数
    @GetMapping("/gitee")
    public String gitee(HttpServletRequest request)throws IOException {
        return userBridgeService.login3rd(request,"GITEE");
    }

    @PostMapping("/login")
    public String login(String account, String password){
        return userBridgeService.login(account, password);
    }

    @PostMapping("/register")
    public String register(@RequestBody UserInfo userInfo){
        return userBridgeService.register(userInfo);
    }
}
