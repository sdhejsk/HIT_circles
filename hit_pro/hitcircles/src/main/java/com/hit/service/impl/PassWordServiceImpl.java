package com.hit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hit.dao.PassWordDao;
import com.hit.dao.impl.PassWordDaoImpl;
import com.hit.service.PassWordService;

public class PassWordServiceImpl implements PassWordService {
    PassWordDao passDao = new PassWordDaoImpl();

    @Override
    public String updatePassword(int userId, String oldPwd, String newPwd, String rePwd) {
        if (!newPwd.equals(rePwd)) {
            JSONObject object = new JSONObject();
            object.put("code", 1);
            object.put("message", "新密码和确认密码不匹配！");
            return object.toString();
        }

        int count = passDao.checkOldPassword(userId, oldPwd);
        if (count == 0) {
            JSONObject object = new JSONObject();
            object.put("code", 1);
            object.put("message", "旧密码不正确！");
            return object.toString();
        }

        int result = passDao.updatePassword(userId, newPwd);
        if (result > 0) {
            JSONObject object = new JSONObject();
            object.put("code", 0);
            object.put("message", "更新密码成功！");
            return object.toString();
        } else {
            JSONObject object = new JSONObject();
            object.put("code", 1);
            object.put("message", "更新密码失败！");
            return object.toString();
        }
    }
}
