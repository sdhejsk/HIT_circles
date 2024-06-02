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
        // 这里需要具体实现，根据用户ID从数据库中获取完整的用户信息
        // 在此处省略实现
        return null;
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
