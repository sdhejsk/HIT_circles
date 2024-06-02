package com.hit.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.dao.CommentDao;
import com.hit.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommentDaoimpl implements CommentDao {
    @Override
    public List<JSONObject> get_all_comment(int article_id) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = new String();
        Object[] param;
        sql = "select DISTINCT comment_id, comment_time, comment.user_id as user_id, username, avatar_url as avatar, comment_content " +
                "from comment, user_avatar, user_base where article_id = ? and "+
                "comment.user_id = user_avatar.user_id and comment.user_id = user_base.user_id order by comment_time desc";
        param = new Object[]{article_id};
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
                resultMap.put("comment_id", row[0]);
                resultMap.put("comment_time", row[1]);
                resultMap.put("user_id", row[2]);
                resultMap.put("username", row[3]);
                resultMap.put("avatar", row[4]);
                resultMap.put("comment_content", row[5]);

                JSONObject json = new JSONObject(resultMap);
                extendedResults.add(json);
            }

            return extendedResults;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int pub_comment(int user_id, int article_id, String content) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = new String();
        try {
            Long count = qr.query("SELECT count(*) FROM comment ", new ScalarHandler<>());
            Long id = qr.query("select count(*) from comment where comment_id = ? ",count.intValue()+1,new ScalarHandler<>());
            while(id>0){
                count++;
                id = qr.query("select count(*) from comment where comment_id = ? ",count.intValue()+1,new ScalarHandler<>());
            }
            sql = "INSERT INTO comment (comment_id, article_id, user_id, comment_time, comment_content)\n" +
                    "VALUES (?, ?, ?, ?, ?) ";
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Object[] param = {count.intValue()+1, article_id, user_id, timestamp, content};
            int result = qr.update(sql, param);
            return result>0?0:1;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    @Override
    public int del_comment(int comment_id, int user_id) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = new String();
        try {
            Long ifadmin = qr.query("SELECT is_admin FROM user_base WHERE user_id = "+ user_id, new ScalarHandler<>());
            int author = qr.query("SELECT user_id FROM comment WHERE comment_id = "+comment_id, new ScalarHandler<>());
            if(ifadmin==0 && author!=user_id){//不是管理员且不是作者
                return 1;
            }
            else{
                sql = "DELETE FROM comment WHERE comment_id = ?";
                Object[] param = {comment_id};
                int result = qr.update(sql, param);
                return result>0?0:1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
    }
}
