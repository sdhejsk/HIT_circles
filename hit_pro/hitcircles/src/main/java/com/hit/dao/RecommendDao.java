package com.hit.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface RecommendDao {
    public List<JSONObject> rec_article(int user_id);
}
