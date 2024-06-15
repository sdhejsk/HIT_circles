package com.hit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hit.dao.RecommendDao;
import com.hit.dao.impl.RecommendDaoimpl;
import com.hit.service.RecommendService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RecommendServiceimpl implements RecommendService {
    RecommendDao rec = new RecommendDaoimpl();
    @Override
    public String rec_article(int user_id) {
        List<JSONObject> result = rec.rec_article(user_id);
        Map<String, Object> object = new LinkedHashMap<>();
        if(result==null || result.isEmpty()){
            object.put("code", 0);
            object.put("message", "获取推荐文章失败！");
            object.put("total", 0);
            List<JSONObject> res = new ArrayList<>();
            object.put("data", res);
        }
        else{
            int total = result.size();
            object.put("code", 0);
            object.put("message", "获取推荐文章成功！");
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
}
