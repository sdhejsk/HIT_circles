package com.hit.dao.impl;

import com.hit.dao.PassWordDao;
import com.hit.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

public class PassWordDaoImpl implements PassWordDao {
    @Override
    public int checkOldPassword(int userId, String oldPwd) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "SELECT COUNT(*) FROM user_base WHERE user_id = ? AND password = ?";
            Object[] params = {userId, oldPwd};
            Long count = qr.query(sql, new ScalarHandler<Long>(), params);
            return count != null ? count.intValue() : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updatePassword(int userId, String newPwd) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "UPDATE user_base SET password = ? WHERE user_id = ?";
            Object[] params = {newPwd, userId};
            return qr.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
