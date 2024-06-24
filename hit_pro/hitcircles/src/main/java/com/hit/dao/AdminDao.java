package com.hit.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface AdminDao {
    public JSONObject get_userinfo(int user_id, int admin_id);
    public List<JSONObject> get_userlist(int admin_id);

    public int edit_user(int admin_id, int user_id, String username, String password,String avatar, int delete);
    public JSONObject get_articleinfo(int article_id, int admin_id);
    public List<JSONObject> get_articlelist(int admin_id);
    public int edit_article(int admin_id, int article_id, String content, int delete);

}
