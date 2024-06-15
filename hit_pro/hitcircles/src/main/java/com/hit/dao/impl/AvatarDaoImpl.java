// AvatarDaoImpl.java
package com.hit.dao.impl;

import com.hit.dao.AvatarDao;
import com.hit.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AvatarDaoImpl implements AvatarDao {

    @Override
    public int updateAvatar(int user_id, String avatar) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "UPDATE user_avatar SET avatar_url = ? WHERE user_id = ?";
        Object[] params = {avatar, user_id};
        try {
            return qr.update(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
