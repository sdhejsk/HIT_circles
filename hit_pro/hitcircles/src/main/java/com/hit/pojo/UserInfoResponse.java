package com.hit.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import java.io.Serializable;

@Data
public class UserInfoResponse implements Serializable {
    @JSONField(ordinal = 1)
    private int id;
    @JSONField(ordinal = 2)
    private String username;
    @JSONField(ordinal = 3)
    private String avatar;
    @JSONField(ordinal = 4)
    private int is_admin;
}
