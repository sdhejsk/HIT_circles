package com.hit.service;

import com.alibaba.fastjson.JSONObject;

public interface ArticleService {
    public String get_all_articles(int user_id, String ser_name);
    public String get_an_article(int user_id, int article_id);
    public String pub_article(int user_id, int from_id, String content);
    public String del_article(int user_id, int article_id);
    public String edit_article(int user_id, int article_id, String content);
}
