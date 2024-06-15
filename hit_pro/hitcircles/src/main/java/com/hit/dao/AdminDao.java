package com.hit.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface AdminDao {
    public JSONObject get_userinfo(int user_id, int admin_id);
    public List<JSONObject> get_userlist(int admin_id);

    public int edit_user(int admin_id, int user_id, String username, String password,String avatar, int delete);
}
