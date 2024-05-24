package com.hit.dao;
import com.hit.dao.impl.LoginDaoimpl;
import org.junit.Test;

public class TestLoginDao {
    LoginDao loginDao = new LoginDaoimpl();
    //测试登录
    @Test
    public void testlogin(){
        int ret = loginDao.login("龙岚","mAFzUbmSVK");
        System.out.println(ret);
    }

}
