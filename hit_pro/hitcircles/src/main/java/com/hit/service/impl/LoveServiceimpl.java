package com.hit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hit.dao.LoveDao;
import com.hit.dao.impl.LoveDaoimpl;
import com.hit.service.LoveService;

import java.util.LinkedHashMap;
import java.util.Map;

public class LoveServiceimpl implements LoveService {
    LoveDao lv = new LoveDaoimpl();
    @Override
    public String change_love(int article_id, int user_id) {
        int[] result = lv.change_love(article_id,user_id);

        if(result[1]==-1){
            JSONObject object = new JSONObject();
            //Map<String, Object> object = new LinkedHashMap<>();
            object.put("code",1);
            object.put("message","用户不存在！");
            return object.toString();
        }
        else if(result[1]==-2){
            JSONObject object = new JSONObject();
            //Map<String, Object> object = new LinkedHashMap<>();
            object.put("code",1);
            object.put("message","文章不存在！");
            return object.toString();
        }
        else if(result[1]==-3){
            JSONObject object = new JSONObject();
            //Map<String, Object> object = new LinkedHashMap<>();
            object.put("code",1);
            object.put("message","数据库更新错误！");
            return object.toString();
        }
        else {
            JSONObject object = new JSONObject();
            //Map<String, Object> object = new LinkedHashMap<>();
            object.put("code",0);
            object.put("message","切换状态成功！");
            object.put("like_num",result[1]);
            object.put("is_like",result[0]);
            return object.toString();
        }
    }
}
