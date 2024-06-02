package com.hit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hit.dao.ArticleDao;
import com.hit.dao.impl.ArticleDaoimpl;
import com.hit.service.ArticleService;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hit.dao.ArticleDao;
import com.hit.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleServiceimpl implements ArticleService {
    ArticleDao art = new ArticleDaoimpl();
    @Override
    public String get_all_articles(int user_id, String ser_name) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<JSONObject> result = art.get_all_articles(user_id,ser_name);
        Map<String, Object> object = new LinkedHashMap<>();
        if(result==null || result.isEmpty()){
            object.put("code", 0);
            object.put("message", "获取文章列表成功！");
            object.put("total", 0);
            List<JSONObject> res = new ArrayList<>();
            object.put("data", res);
        }
        else if(result.get(0).containsKey("null")){
            object.put("code", 1);
            object.put("message", "未找到该用户！");
        }
        else if(result.get(0).containsKey("not_friend")){
            object.put("code", 1);
            object.put("message", "您未关注该用户！");
        }
        else{
            int total = result.size();
            object.put("code", 0);
            object.put("message", "获取文章列表成功！");
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
    public String get_an_article(int article_id) {
        JSONObject result = art.get_an_article(article_id);
        Map<String, Object> object = new LinkedHashMap<>();

        if(result==null){
            object.put("code", 1);
            object.put("message", "未找到该文章！");
        }
        else{
            object.put("code", 0);
            object.put("message", "获取文章成功！");
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
    public String pub_article(int user_id, int from_id, String content) {
        int result = art.pub_article(user_id,from_id,content);
        if(result==0){
            JSONObject object = new JSONObject();
            object.put("code",0);
            object.put("message","发布成功！");
            return object.toString();
        }
        else{
            JSONObject object = new JSONObject();
            object.put("code",1);
            object.put("message","发布失败！");
            return object.toString();
        }
    }

    @Override
    public String del_article(int user_id, int article_id) {
        int result = art.del_article(user_id, article_id);
        if(result==0){
            JSONObject object = new JSONObject();
            object.put("code",0);
            object.put("message","删除文章成功！");
            return object.toString();
        }
        else{
            JSONObject object = new JSONObject();
            object.put("code",1);
            object.put("message","身份认证失败！");
            return object.toString();
        }
    }

    @Override
    public String edit_article(int user_id, int article_id, String content) {
        int result = art.edit_article(user_id, article_id,content);
        if(result==0){
            JSONObject object = new JSONObject();
            object.put("code",0);
            object.put("message","修改文章成功！");
            return object.toString();
        }
        else{
            JSONObject object = new JSONObject();
            object.put("code",1);
            object.put("message","身份认证失败！");
            return object.toString();
        }
    }

}
