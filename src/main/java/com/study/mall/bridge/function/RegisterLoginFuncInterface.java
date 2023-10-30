package com.study.mall.bridge.function;

import com.study.mall.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author peng
 * @version 1.0
 * @description 核心方法的承载接口 （或方法）
 * @date 2023/10/21 13:59
 */
public interface RegisterLoginFuncInterface {

    public String login(String account, String password);
    public String register(UserInfo userInfo);
    public boolean checkUserExists(String userName);

    public String login3rd(HttpServletRequest request);
}
