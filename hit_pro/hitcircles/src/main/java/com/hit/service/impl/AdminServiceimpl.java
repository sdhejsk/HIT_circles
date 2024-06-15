package com.hit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hit.dao.AdminDao;
import com.hit.dao.impl.AdminDaoimpl;
import com.hit.service.AdminService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AdminServiceimpl implements AdminService {
    AdminDao ad = new AdminDaoimpl();
    @Override
    public String get_userinfo(int user_id, int admin_id) {
        JSONObject result = ad.get_userinfo(user_id, admin_id);
        Map<String, Object> object = new LinkedHashMap<>();
        if(result==null){
            object.put("code", 1);
            object.put("message", "身份认证失败！");
        }
        else{
            object.put("code", 0);
            object.put("message", "获取用户信息成功！");
            object.put("data", result);
        }
        ObjectMapper objm = new ObjectMapper();
        String json = null;
        try {
            objm.registerModule(new JavaTimeModule()); // 注册JavaTimeModule
            json = objm.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public String get_userlist(int admin_id) {
        List<JSONObject> result = ad.get_userlist(admin_id);
        Map<String, Object> object = new LinkedHashMap<>();
        if(result==null){
            object.put("code", 1);
            object.put("message", "获取用户列表失败！");
        }
        else if(result.isEmpty()){
            object.put("code", 0);
            object.put("message", "获取用户列表成功！");
            List<JSONObject> res = new ArrayList<>();
            object.put("total", 0);
            object.put("data", res);
        }
        else{
            int total = result.size();
            object.put("code", 0);
            object.put("message", "获取用户列表成功！");
            object.put("total", total);
            object.put("data", result);
        }

        ObjectMapper objm = new ObjectMapper();
        String json = null;
        try {
            objm.registerModule(new JavaTimeModule()); // 注册JavaTimeModule
            json = objm.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public String edit_user(int admin_id, int user_id, String username, String password,String avatar, int delete) {
        int result = ad.edit_user(admin_id,user_id,username,password,avatar,delete);
        Map<String, Object> object = new LinkedHashMap<>();
        if(result==1){//编辑错误
            object.put("code", 1);
            object.put("message", "操作失败！");
        }
        else{
            object.put("code", 0);
            object.put("message", "操作成功！");
        }
        ObjectMapper objm = new ObjectMapper();
        String json = null;
        try {
            objm.registerModule(new JavaTimeModule()); // 注册JavaTimeModule
            json = objm.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
