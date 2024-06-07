// AvatarDaoImpl.java
package com.hit.dao.impl;

import com.hit.dao.AvatarDao;
import com.hit.utils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AvatarDaoImpl implements AvatarDao {
    @Override
    public int updateAvatar(String token, String avatarBase64) {
        String sql = "UPDATE users SET avatar = ? WHERE token = ?";
        try (Connection connection = DruidUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, avatarBase64);
            preparedStatement.setString(2, token);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
