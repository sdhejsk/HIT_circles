package com.hit.dao.impl;

import com.hit.dao.UserDao;
import com.hit.pojo.UserInfoResponse;
import com.hit.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    @Override
    public UserInfoResponse getUserInfoById(int userId) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "SELECT ub.user_id AS id, ub.username, ua.avatar_url AS avatar " +
                        "FROM user_base ub JOIN user_avatar ua ON ub.user_id = ua.user_id " +
                        "WHERE ub.user_id = ?";
            return qr.query(sql, new BeanHandler<>(UserInfoResponse.class), userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserInfoResponse getUserBaseInfoById(int userId) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "SELECT u.user_id, u.username, a.avatar_url FROM user_base u JOIN user_avatar a ON u.user_id = a.user_id WHERE u.user_id = ?";
            return qr.query(sql, new BeanHandler<>(UserInfoResponse.class), userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
