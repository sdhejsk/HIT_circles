package com.hit.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface ArticleDao {
    public List<JSONObject> get_all_articles(int user_id, String ser_name);
    public JSONObject get_an_article(int user_id, int article_id);
    public int pub_article(int user_id, int from_id, String content);
    public int del_article(int user_id, int article_id);
    public int edit_article(int user_id, int article_id, String content);
}
