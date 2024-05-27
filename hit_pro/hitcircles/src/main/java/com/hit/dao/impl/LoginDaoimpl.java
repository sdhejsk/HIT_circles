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

    @Override
    public int register(String username,String password) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "SELECT user_id FROM user_base WHERE 1=1 and username = ? ";
        Object[] param = {username};
        try {
            if(qr.query(sql, param, new ScalarHandler<>())==null){
                try {
                    Long count = qr.query("SELECT count(*) FROM user_base ", new ScalarHandler<>());
                    Long id = qr.query("select count(*) from user_base where user_id = ? ",count.intValue()+1,new ScalarHandler<>());
                    while(id>0){
                        count++;
                        id = qr.query("select count(*) from user_base where user_id = ? ",count.intValue()+1,new ScalarHandler<>());
                    }
                    String insert = "INSERT INTO user_base (user_id, username, password, is_admin)\n" +
                            "VALUES (?, ?, ?, 0) ";
                    Object[] param2 = {count.intValue()+1,username,password};
                    int result = qr.update(insert, param2);
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }
}
