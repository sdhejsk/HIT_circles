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

    @Override
    public String get_articleinfo(int article_id, int admin_id) {
        JSONObject result = ad.get_articleinfo(article_id, admin_id);
        Map<String, Object> object = new LinkedHashMap<>();

        if (result == null) {
            object.put("code", 1);
            object.put("message", "未找到该文章！");
        } else {
            // 修改pub_time字段中的"T"为空格
            if (result.containsKey("pub_time")) {
                String pubTime = result.getString("pub_time");
                String modifiedPubTime = pubTime.replace("T", " "); // 替换"T"为空格
                result.put("pub_time", modifiedPubTime); // 更新JSONObject中的pub_time
            }

            int total = result.size(); // 注意：使用length()方法获取JSONObject的大小
            object.put("code", 0);
            object.put("message", "获取文章成功！");
            object.put("total", total); // 这里total可能不需要，因为JSONObject只有一个元素
            object.put("data", result);
        }

// 将Map转换为JSON字符串
        String jsonString = new JSONObject(object).toString();
        return jsonString;
    }

    @Override
    public String get_articlelist(int admin_id) {
        List<JSONObject> results = ad.get_articlelist(admin_id);
        Map<String, Object> object = new LinkedHashMap<>();

        if (results == null) {
            object.put("code", 1);
            object.put("message", "获取文章列表失败！");
        } else {
            // 修改pub_time字段中的"T"为空格
            for (JSONObject result : results){
                if (result.containsKey("pub_time")) {
                    String pubTime = result.getString("pub_time");
                    String modifiedPubTime = pubTime.replace("T", " "); // 替换"T"为空格
                    result.put("pub_time", modifiedPubTime); // 更新JSONObject中的pub_time
                }
            }

            int total = results.size(); // 注意：使用length()方法获取JSONObject的大小
            object.put("code", 0);
            object.put("message", "获取文章列表成功！");
            object.put("total", total); // 这里total可能不需要，因为JSONObject只有一个元素
            object.put("data", results);
        }

        // 将Map转换为JSON字符串
        String jsonString = new JSONObject(object).toString();
        return jsonString;
    }

    @Override
    public String edit_article(int admin_id, int article_id, String content, int delete) {
        int result = ad.edit_article(admin_id,article_id,content,delete);
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
