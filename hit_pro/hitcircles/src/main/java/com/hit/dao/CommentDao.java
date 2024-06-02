package com.hit.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface CommentDao {
    public List<JSONObject> get_all_comment(int article_id);
    public int pub_comment(int user_id, int article_id, String content);
    public int del_comment(int comment_id, int user_id);
}
