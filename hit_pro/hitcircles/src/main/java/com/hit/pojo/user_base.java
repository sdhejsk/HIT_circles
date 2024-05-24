package com.hit.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


import java.io.Serializable;


/**
 * 用户类
 * */
@Data
public class user_base implements Serializable {
    //用户ID
    @JSONField(ordinal = 1)
    private int user_id;

    //用户名
    @JSONField(ordinal = 2)
    private String username;
    //密码
    @JSONField(ordinal = 3)
    private String password;
    //是否是管理员，0否，1是
    @JSONField(ordinal = 4)
    private int is_admin;

}

