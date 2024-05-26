package com.hit.dao.impl;

import com.alibaba.fastjson.JSONObject;
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
import java.util.*;


public class ArticleDaoimpl implements ArticleDao {

    @Override
    public List<JSONObject> get_all_articles(int user_id, String ser_name) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = new String();
        Object[] param;
        if(ser_name==null){
            sql = "select article_id, from_id, article_base.user_id as user_id, username, avatar_url as avatar, article_content as content, pub_time as pub_date from article_base, user_follow, user_avatar, user_base where article_base.user_id = user_follow.friend_id and  user_follow.user_id = ? and "+
                    "article_base.user_id = user_avatar.user_id and article_base.user_id = user_base.user_id";
            param = new Object[]{user_id};
        }
        else{
            String seru = "select user_id from user_base where username = ?";
            Object[] p = {ser_name};
            try {
                if(qr.query(seru,p,new ScalarHandler<>())==null){
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            sql = "select article_id, from_id, article_base.user_id as user_id, username, avatar_url as avatar, article_content as content, pub_time as pub_date from article_base, user_follow, user_avatar, user_base where article_base.user_id = user_follow.friend_id and  user_follow.user_id = ? and "+
                    "article_base.user_id = user_avatar.user_id and article_base.user_id = user_base.user_id and user_base.username = ?";
            param = new Object[]{user_id,ser_name};
        }
        try {
            List<Object[]> results = qr.query(sql, new ArrayListHandler(), param);
            List<JSONObject> extendedResults = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();

            for (Object[] row : results) {
                int articleId = (int) row[0];

                // Query for like count
                String likeCountSql = "SELECT COUNT(*) FROM article_love WHERE article_id = ?";
                Long likeCount = qr.query(likeCountSql, new ScalarHandler<Long>(), articleId);

                // Query for comment count
                String commentCountSql = "SELECT COUNT(*) FROM comment WHERE article_id = ?";
                Long commentCount = qr.query(commentCountSql, new ScalarHandler<Long>(), articleId);

                // Create a map for JSON serialization
                Map<String, Object> resultMap = new LinkedHashMap<>();
                resultMap.put("article_id", row[0]);
                resultMap.put("from_id", row[1]);
                resultMap.put("user_id", row[2]);
                resultMap.put("username", row[3]);
                resultMap.put("avatar", row[4]);
                resultMap.put("content", row[5]);
                resultMap.put("pub_date", row[6]);
                resultMap.put("like_num", likeCount.intValue());
                resultMap.put("comment_num", commentCount.intValue());

                JSONObject json = new JSONObject(resultMap);
                extendedResults.add(json);
            }

/*
            // Convert the list of maps to a list of JSON strings
            List<String> jsonResults = new ArrayList<>();
            for (Map<String, Object> resultMap : extendedResults) {
                // 模拟添加pub_date字段
                resultMap.put("pub_date", java.time.LocalDateTime.now());
                mapper.registerModule(new JavaTimeModule()); // 注册JavaTimeModule

                String jsonString = mapper.writeValueAsString(resultMap);
                jsonResults.add(jsonString);
            }
            */
            return extendedResults;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
