package com.study.mall.adapter;

/**
 * @author peng
 * @version 1.0
 * @description 目标角色
 * @date 2023/10/17 23:37
 */
public interface Login3rdTarget {
    public String loginByGitee(String code, String state);

    public String loginByWeChat(String code, String state);

    public String loginByQQ(String code, String state);
}
