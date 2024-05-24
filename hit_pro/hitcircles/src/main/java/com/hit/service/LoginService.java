package com.hit.service;

import com.hit.dao.LoginDao;

/*
登录模块Service层接口
 */
public interface LoginService {

    public String login(String username, String password);
}
