package com.hit.dao.impl;
import com.hit.dao.LoginDao;
import com.hit.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

/*
登录模块的DAO层实现类
 */
public class LoginDaoimpl implements LoginDao{

    @Override
    public int login(String username, String password) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "SELECT user_id FROM user_base WHERE 1=1 and username = ? and password = ? ";
            Object[] param = {username,password};
            int result = qr.query(sql, param, new ScalarHandler<>());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
