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
        List<JSONObject> result = art.get_all_articles(user_id,ser_name);
        if(result==null){
            JSONObject object = new JSONObject();
            object.put("code",1);
            object.put("message","未找到该用户！");
            return object.toString();
        }
        else{
            /*
            StringBuilder jsonArrayString = new StringBuilder();
            jsonArrayString.append("[");
            int total = result.size();
            for (int i = 0; i < result.size(); i++) {
                jsonArrayString.append(result.get(i));
                if (i < result.size() - 1) {
                    jsonArrayString.append(",");
                }
            }
            jsonArrayString.append("]");


            */

            /*
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = null;
            try {
                // 模拟添加pub_date字段
                //result.put("pub_date", java.time.LocalDateTime.now());
                objectMapper.registerModule(new JavaTimeModule()); // 注册JavaTimeModule
                jsonString = objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

             */

            int total = result.size();
            Map<String, Object> object = new LinkedHashMap<>();
            object.put("status", 0);
            object.put("message", "获取文章列表成功！");
            object.put("total", total);
            object.put("data", result);

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
}
