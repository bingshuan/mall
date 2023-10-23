package com.study.mall.repo;

import com.study.mall.pojo.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author peng
 * @version 1.0
 * @description TODO
 * @date 2023/10/15 22:13
 */
@Repository
public interface UserRepository extends JpaRepository<UserInfo,Integer> {
    //根据用户 userName查询信息
    UserInfo findByUserName(String userName);
    //根据用 户名称 和 密码 查询用户信息
    UserInfo findByUserNameAndUserPassword(String account, String password);

}
