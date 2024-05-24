package com.hit.pojo;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import java.io.Serializable;

/*
 * 评论类
 * */
@Data
public class comment implements Serializable{
    @JSONField(ordinal = 1)
    private int comment_id;
    @JSONField(ordinal = 2)
    private int article_id;
    @JSONField(ordinal = 3)
    private int user_id;
    @JSONField(ordinal = 4)
    private String comment_time;
    @JSONField(ordinal = 5)
    private String comment_content;

    @Override
    public String toString() {
        return "comment{" +
                "comment_id=" + comment_id +
                ", article_id=" + article_id +
                ", user_id=" + user_id +
                ", comment_time='" + comment_time + '\'' +
                ", comment_content=" + comment_content +
                '}';
    }
}
