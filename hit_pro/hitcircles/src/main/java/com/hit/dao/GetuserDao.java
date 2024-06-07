package com.hit.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;


public interface GetuserDao {
    public List<JSONObject> get_all_user(int user_id);
}
