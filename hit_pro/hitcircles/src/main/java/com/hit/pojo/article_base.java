package com.hit.pojo;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/*
 * 博客类
 * */
@Data
public class article_base implements Serializable{
    @JSONField(ordinal = 1)
    private int article_id;
    @JSONField(ordinal = 2)
    private int user_id;
    @JSONField(ordinal = 3)
    private int from_id;
    @JSONField(ordinal = 4)
    private String pub_time;
    @JSONField(ordinal = 5)
    private String article_content;
    @Override
    public String toString() {
        return "article_base{" +
                "article_id=" + article_id +
                ", user_id=" + user_id +
                ", from_id=" + from_id +
                ", pub_time='" + pub_time + '\'' +
                ", article_content=" + article_content +
                '}';
    }
}
