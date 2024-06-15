package com.hit.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.dao.AdminDao;
import com.hit.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AdminDaoimpl implements AdminDao {
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
}
