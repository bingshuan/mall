package com.study.mall.adapter;

import com.alibaba.fastjson.JSONObject;
import com.study.mall.pojo.UserInfo;
import com.study.mall.service.UserService;
import com.study.mall.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author peng
 * @version 1.0
 * @description 适配器
 * @date 2023/10/17 23:32
 */
@Component
public class Login3rdAdapter extends UserService implements Login3rdTarget{
    @Value("${gitee.state}")
    private String giteeState;
    @Value("${gitee.token.url}")
    private String giteeTokenUrl;
    @Value("${gitee.user.url}")
    private String giteeUserUrl;
    @Value("${gitee.user.prefix}")
    private String giteeUserPrefix;

    @Override
    public String loginByGitee(String code, String state) {
        //进行state判断，state值是前端和后端商定好的，前端传递给gitee，gitee再回传state给回调接口
        if (!giteeState.equals(state)){
            throw new UnsupportedOperationException("Invalid state!");
        }
        //请求gitee平台获取token,并携带code
        String tokenUrl = giteeTokenUrl.concat(code);
        JSONObject tokenResponse = HttpClientUtils.execute(tokenUrl, HttpMethod.POST);
        String token = String.valueOf(tokenResponse.get("access_token"));
        //请求用户信息，并携带token
        String userUrl = giteeUserUrl.concat(token);
        JSONObject userInfoResponse = HttpClientUtils.execute(userUrl, HttpMethod.GET);
        //获取用户信息， 加上前缀，密码等于用户名
        String userName = giteeUserPrefix.concat(String.valueOf(userInfoResponse.get("name")));
        String password = userName;
        //自动登录和注册功能，对之前方法的复用
        return autoRegister3rdAndLogin(userName,password);
    }

    private String autoRegister3rdAndLogin(String userName, String password) {
        if (super.checkUserExists(userName)){
            return super.login(userName,password);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setUserPassword(password);
        userInfo.setCreateDate(new Date());
        super.register(userInfo);
        return super.login(userName,password);
    }

    @Override
    public String loginByWeChat(String code, String state) {
        return null;
    }

    @Override
    public String loginByQQ(String code, String state) {
        return null;
    }
}
