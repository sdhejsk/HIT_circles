package com.hit.pojo;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
/*
 * 用户头像类
 * */
@Data
public class user_avatar implements Serializable{
    @JSONField(ordinal = 1)
    private int user_id;
    @JSONField(ordinal = 2)
    private String avatar_url;
    @Override
    public String toString() {
        return "user_avatar{" +
                "user_id=" + user_id +
                ", avatar_url=" + avatar_url +
                '}';
    }
}
