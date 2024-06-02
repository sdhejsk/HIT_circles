package com.hit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hit.dao.ArticleDao;
import com.hit.dao.CommentDao;
import com.hit.dao.impl.CommentDaoimpl;
import com.hit.service.CommentService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommentServiceimpl implements CommentService {
    CommentDao com = new CommentDaoimpl();
    @Override
    public String get_all_comment(int article_id) {
        List<JSONObject> result = com.get_all_comment(article_id);
        Map<String, Object> object = new LinkedHashMap<>();
        if(result==null || result.isEmpty()){
            object.put("code", 0);
            object.put("message", "获取评论成功！");
            object.put("total", 0);
            List<JSONObject> res = new ArrayList<>();
            object.put("data", res);
        }
        else{
            int total = result.size();
            object.put("code", 0);
            object.put("message", "获取评论成功！");
            object.put("total", total);
            object.put("data", result);
            System.out.println("total: "+total);
            System.out.println("data: "+result);
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
    public String pub_comment(int user_id, int article_id, String content) {
        int result = com.pub_comment(user_id,article_id,content);
        if(result==0){
            JSONObject object = new JSONObject();
            object.put("code",0);
            object.put("message","评论成功！");
            return object.toString();
        }
        else{
            JSONObject object = new JSONObject();
            object.put("code",1);
            object.put("message","评论失败！");
            return object.toString();
        }
    }

    @Override
    public String del_comment(int comment_id, int user_id) {
        int result = com.del_comment(comment_id,user_id);
        if(result==0){
            JSONObject object = new JSONObject();
            object.put("code",0);
            object.put("message","删除评论成功！");
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
