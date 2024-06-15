// AvatarServiceImpl.java
package com.hit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hit.dao.AvatarDao;
import com.hit.dao.impl.AvatarDaoImpl;
import com.hit.service.AvatarService;

public class AvatarServiceImpl implements AvatarService {

    AvatarDao ava = new AvatarDaoImpl();
    @Override
    public String updateAvatar(int user_id, String avatar) {
        int result = ava.updateAvatar(user_id, avatar);
        if(result==0){
            JSONObject object = new JSONObject();
            object.put("code", 1);
            object.put("message", "更新头像失败！");
            return object.toString();
        }
        else{
            JSONObject object = new JSONObject();
            object.put("code", 0);
            object.put("message", "更新头像成功！");
            return object.toString();
        }
    }
}
