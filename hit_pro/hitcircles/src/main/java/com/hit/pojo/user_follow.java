package com.hit.pojo;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
/*
 * 关注类
 * */
@Data
public class user_follow implements Serializable{
    @JSONField(ordinal = 1)
    private int user_id;
    @JSONField(ordinal = 2)
    private int friend_id;
    @Override
    public String toString() {
        return "user_follow{" +
                "user_id=" + user_id +
                ", friend_id=" + friend_id +
                '}';
    }
}
