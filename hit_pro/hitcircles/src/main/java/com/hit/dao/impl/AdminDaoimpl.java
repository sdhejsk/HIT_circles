package com.hit.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.dao.AdminDao;
import com.hit.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AdminDaoimpl implements AdminDao {
    public class article_cls{
        public int user_id;
        public int article_id;
        public String pub_time;
    }

    @Override
    public JSONObject get_userinfo(int user_id, int admin_id) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String if_admin = "select count(*) from user_base where user_id = ? and is_admin = 1 ";
        Object[] para = {admin_id};
        try {
            Long ifadmin = qr.query(if_admin, new ScalarHandler<>(), para);
            if(ifadmin==0){//不是管理员
                return null;
            }
            //返回要查看的用户（非管理员）
            String getuserinfo = "select user_base.user_id, username, avatar_url, password from user_base, user_avatar where " +
                    "user_base.user_id = user_avatar.user_id and user_base.user_id = ? and user_base.is_admin = 0 ";
            Object[] para_getinfo = {user_id};
            List<Object[]> results = qr.query(getuserinfo, new ArrayListHandler(), para_getinfo);
            Object[] result = results.get(0);

            // Create a map for JSON serialization
            Map<String, Object> resultMap = new LinkedHashMap<>();
            resultMap.put("id", result[0]);
            resultMap.put("username", result[1]);
            resultMap.put("avatar", result[2]);
            resultMap.put("password", result[3]);

            JSONObject json = new JSONObject(resultMap);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<JSONObject> get_userlist(int admin_id) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String if_admin = "select count(*) from user_base where user_id = ? and is_admin = 1 ";
        Object[] para = {admin_id};
        try {
            Long ifadmin = qr.query(if_admin, new ScalarHandler<>(), para);
            if(ifadmin==0){//不是管理员
                return null;
            }
            //返回所有非管理员用户
            String getuserlist = "select user_base.user_id, username, avatar_url, password from user_base, user_avatar where " +
                    "user_base.user_id = user_avatar.user_id and user_base.is_admin = 0 ";
            List<Object[]> results = qr.query(getuserlist, new ArrayListHandler());
            List<JSONObject> extendedResults = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();

            for (Object[] row : results) {

                // Create a map for JSON serialization
                Map<String, Object> resultMap = new LinkedHashMap<>();
                resultMap.put("id", row[0]);
                resultMap.put("username", row[1]);
                resultMap.put("avatar", row[2]);
                resultMap.put("password", row[3]);

                JSONObject json = new JSONObject(resultMap);
                extendedResults.add(json);
            }
            return extendedResults;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int edit_user(int admin_id, int user_id, String username, String password, String avatar, int delete) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String if_admin = "select count(*) from user_base where user_id = ? and is_admin = 1 ";
        Object[] para = {admin_id};
        Long ifadmin = null;
        try {
            ifadmin = qr.query(if_admin, new ScalarHandler<>(), para);
            if(ifadmin==0){//不是管理员
                return 1;
            }
            if(delete==1){//删除用户
                String del_user = "delete from user_base where user_id = ? ";
                Object[] para_del = {user_id};
                return qr.update(del_user,para_del)>0?0:1;
            }
            else{//编辑用户
                String edit = "UPDATE user_base SET username = ?, password = ? " +
                        " WHERE user_id = ? ";
                Object[] para_edit = {username, password, user_id};
                String edit2 = "update user_avatar set avatar_url = ? where user_id = ? ";
                Object[] para_edit2 = {avatar, user_id};
                if(qr.update(edit,para_edit)>0 && qr.update(edit2,para_edit2)>0)return 0;
                else return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    @Override
    public JSONObject get_articleinfo(int article_id, int admin_id) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String if_admin = "select count(*) from user_base where user_id = ? and is_admin = 1 ";
        Object[] para = {admin_id};
        try {
            Long ifadmin = qr.query(if_admin, new ScalarHandler<>(), para);
            if(ifadmin==0){//不是管理员
                return null;
            }
            //返回要查看的文章（非管理员）
            String getarticleinfo = "select user_id, article_id, article_content from article_base where " +
                    "article_id = ? ";
            Object[] para_getinfo = {article_id};
            List<Map<String, Object>> results = qr.query(getarticleinfo, new MapListHandler(), para_getinfo);
            Map<String, Object> result = results.get(0);

            //返回该文章的用户信息
            String getuserinfo = "select username, avatar_url from user_base, user_avatar where user_base.user_id = " +
                    "user_avatar.user_id and user_base.user_id = ? ";
            Object[] para_getuserinfo = {result.get("user_id")};
            List<Object[]> user_results = qr.query(getuserinfo, new ArrayListHandler(), para_getuserinfo);
            Object[] user_result = user_results.get(0);

            // Create a map for JSON serialization
            Map<String, Object> resultMap = new LinkedHashMap<>();
            resultMap.put("username", user_result[0]);
            resultMap.put("avatar", user_result[1]);
            resultMap.put("article_id", result.get("article_id"));
            resultMap.put("content", result.get("article_content"));

            JSONObject json = new JSONObject(resultMap);
            System.out.println(json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<JSONObject> get_articlelist(int admin_id) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String if_admin = "select count(*) from user_base where user_id = ? and is_admin = 1 ";
        Object[] para = {admin_id};
        try {
            Long ifadmin = qr.query(if_admin, new ScalarHandler<>(), para);
            if(ifadmin==0){//不是管理员
                return null;
            }
            //返回所有非管理员的文章
            String getarticlelist = "select user_base.user_id, article_id, pub_time from article_base, user_base where " +
                    "user_base.user_id = article_base.user_id and is_admin != 1 order by pub_time desc";
            List<Map<String, Object>> results = qr.query(getarticlelist, new MapListHandler());

            List<JSONObject> extendedResults = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();

            for (Map<String, Object> row : results) {

                //所有对应用户的信息
                String getuserinfo = "select username, avatar_url from user_base, user_avatar where user_base.user_id = " +
                        "user_avatar.user_id and user_base.user_id = ? ";
                Object[] para_getuserinfo = {row.get("user_id")};
                List<Object[]> user_results = qr.query(getuserinfo, new ArrayListHandler(), para_getuserinfo);
                Object[] user_result = user_results.get(0);

                // Create a map for JSON serialization
                Map<String, Object> resultMap = new LinkedHashMap<>();
                resultMap.put("username", user_result[0]);
                resultMap.put("avatar", user_result[1]);
                resultMap.put("article_id", row.get("article_id"));
                resultMap.put("pub_time", row.get("pub_time"));

                JSONObject json = new JSONObject(resultMap);
                extendedResults.add(json);
            }
            return extendedResults;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int edit_article(int admin_id, int article_id, String content, int delete) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String if_admin = "select count(*) from user_base where user_id = ? and is_admin = 1 ";
        Object[] para = {admin_id};
        Long ifadmin = null;
        try {
            ifadmin = qr.query(if_admin, new ScalarHandler<>(), para);
            if(ifadmin==0){//不是管理员
                return 1;
            }
            if(delete==1){//删除文章
                String del_article = "delete from article_base where article_id = ? ";
                Object[] para_del = {article_id};
                return qr.update(del_article,para_del)>0?0:1;
            }
            else{//编辑文章
                String edit = "UPDATE article_base SET article_content = ? WHERE article_id = ? ";
                Object[] para_edit = {content,article_id};
                if(qr.update(edit,para_edit)>0)return 0;
                else return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }
}
