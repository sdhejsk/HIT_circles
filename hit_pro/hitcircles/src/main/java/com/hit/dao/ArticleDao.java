package com.hit.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface ArticleDao {
    public List<JSONObject> get_all_articles(int user_id, String ser_name);
}
