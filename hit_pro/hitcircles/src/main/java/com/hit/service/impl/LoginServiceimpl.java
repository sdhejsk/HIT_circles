package com.hit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hit.dao.LoginDao;
import com.hit.dao.impl.LoginDaoimpl;
import com.hit.service.LoginService;
/*
登录模块Service层实现类
 */
public class LoginServiceimpl implements LoginService {
    LoginDao loginDao = new LoginDaoimpl();
    @Override
    public String login(String username, String password) {
        int us_id = loginDao.login(username,password);
        if(us_id>0){
            JSONObject object = new JSONObject();
            object.put("code",0);
            object.put("message","登录成功!");
            object.put("token",us_id);
            return object.toString();
        }
        else {
            JSONObject object = new JSONObject();
            object.put("code",1);
            object.put("message","登录失败，用户名或密码错误!");
            return object.toString();
        }
    }
}
