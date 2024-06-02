package com.hit.service;

public interface CommentService {
    public String get_all_comment(int article_id);
    public String pub_comment(int user_id, int article_id, String content);
    public String del_comment(int comment_id, int user_id);
}
