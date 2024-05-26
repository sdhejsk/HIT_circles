package com.hit.dao;
/*
登录模块Dao层接口
 */
public interface LoginDao {
    public int login(String username, String password);
    public int register(String username,String password);
}
