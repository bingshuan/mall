package com.study.mall.bridge.function;

import com.alibaba.fastjson.JSONObject;
import com.study.mall.bridge.abst.RegisterLoginComponentFactory;
import com.study.mall.pojo.UserInfo;
import com.study.mall.repo.UserRepository;
import com.study.mall.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author peng
 * @version 1.0
 * @description TODO
 * @date 2023/10/21 14:06
 */
@Component
public class RegisterLoginByGitee extends AbstractRegisterLoginFunc implements RegisterLoginFuncInterface {

    @Value("${gitee.state}")
    private String giteeState;
    @Value("${gitee.token.url}")
    private String giteeTokenUrl;
    @Value("${gitee.user.url}")
    private String giteeUserUrl;
    @Value("${gitee.user.prefix}")
    private String giteeUserPrefix;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void initFuncMap() {
        RegisterLoginComponentFactory.funcMap.put("GITEE",this);
    }

    @Override
    public String login3rd(HttpServletRequest request) {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
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
        if (super.commonCheckUserExists(userName,userRepository)){
            return super.commonLogin(userName,password,userRepository);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setUserPassword(password);
        userInfo.setCreateDate(new Date());
        super.commonRegister(userInfo,userRepository);
        return super.commonLogin(userName,password,userRepository);
    }

}
