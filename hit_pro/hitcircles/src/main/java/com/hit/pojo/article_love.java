package com.hit.pojo;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
/*
 * 点赞类
 * */
@Data
public class article_love implements Serializable{
    @JSONField(ordinal = 1)
    private int article_id;
    @JSONField(ordinal = 2)
    private int user_id;
    @Override
    public String toString() {
        return "article_love{" +
                "article_id=" + article_id +
                ", user_id=" + user_id +
                '}';
    }
}
