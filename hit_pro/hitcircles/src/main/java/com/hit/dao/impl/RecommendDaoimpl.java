package com.hit.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.dao.RecommendDao;
import com.hit.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RecommendDaoimpl implements RecommendDao {
    @Override
    public List<JSONObject> rec_article(int user_id) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = new String();
        Object[] param;
        sql = "SELECT t1.article_id, t1.user_id, t1.article_content " +
                "FROM ( " +
                "    SELECT article_id, user_id, article_content, " +
                "           ROW_NUMBER() OVER(PARTITION BY user_id ORDER BY RAND()) as row_num " +
                "    FROM article_base " +
                "    WHERE user_id != ? AND user_id NOT IN ( " +
                "        SELECT friend_id FROM user_follow WHERE user_id = ? " +
                "    ) " +
                ") t1 " +
                "WHERE t1.row_num = 1 " +
                "ORDER BY RAND() " +
                "LIMIT 10 ";
        param = new Object[]{user_id, user_id};
        try {
            List<Object[]> results = qr.query(sql, new ArrayListHandler(), param);
            List<JSONObject> extendedResults = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (Object[] row : results) {
                int article_id = (int) row[0];
                int us_id = (int) row[1];
                String get_avatar = "Select avatar_url from user_avatar where user_id = ? ";
                String get_username = "select username from user_base where user_id = ? ";
                String get_likenum = "select count(*) from article_love where article_id = ? ";
                String get_mutual = "SELECT COUNT(*) as common_friends " +
                        "FROM ( " +
                        "    SELECT friend_id " +
                        "    FROM user_follow " +
                        "    WHERE user_id = ? " +
                        ") as user1 " +
                        "INNER JOIN ( " +
                        "    SELECT friend_id " +
                        "    FROM user_follow " +
                        "    WHERE user_id = ? " +
                        ") as user2 ON user1.friend_id = user2.friend_id ";
                Object[] para_avatar = {us_id};
                Object[] para_article = {article_id};
                Object[] para_mutual = {us_id,user_id};
                Object[] para_username = {us_id};
                String avatar = qr.query(get_avatar,new ScalarHandler<>(), para_avatar);
                Long likenum = qr.query(get_likenum,new ScalarHandler<>(), para_article);
                Long mutual = qr.query(get_mutual,new ScalarHandler<>(), para_mutual);
                String username = qr.query(get_username,new ScalarHandler<>(), para_username);

                // Create a map for JSON serialization
                Map<String, Object> resultMap = new LinkedHashMap<>();
                resultMap.put("user_id", row[1]);
                resultMap.put("username", username);
                resultMap.put("avatar", avatar);
                resultMap.put("article_content", row[2]);
                resultMap.put("mutual_friend", mutual);
                resultMap.put("like_num", likenum);

                JSONObject json = new JSONObject(resultMap);
                extendedResults.add(json);
            }
            return extendedResults;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
