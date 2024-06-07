package com.hit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hit.dao.FollowDao;
import com.hit.dao.impl.FollowDaoimpl;
import com.hit.service.FollowService;

public class FollowServiceimpl implements FollowService {
    FollowDao fol = new FollowDaoimpl();
    @Override
    public String change_follow(int user_id, int follow_id) {
        int result = fol.change_follow(user_id,follow_id);
        if(result==0){
            JSONObject object = new JSONObject();
            object.put("code",0);
            object.put("message","切换状态成功！");
            return object.toString();
        }
        else{
            JSONObject object = new JSONObject();
            object.put("code",1);
            object.put("message","身份认证失败！");
            return object.toString();
        }
    }
}
