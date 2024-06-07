package com.hit.service.impl;

import com.hit.dao.UserDao;
import com.hit.dao.impl.UserDaoImpl;
import com.hit.pojo.UserInfoResponse;
import com.hit.service.UserService;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public UserInfoResponse getUserInfoByToken(String token) {
        // Assuming the token contains the user_id as part of it. Extract the user_id from the token.
        // In a real-world scenario, you would use a JWT library to parse the token.
        int userId = extractUserIdFromToken(token);
        return userDao.getUserInfoById(userId);
    }

    private int extractUserIdFromToken(String token) {
        // Extract user_id from the token. This is a simplified example.
        // In real-world applications, use a proper JWT library to parse the token.
        return Integer.parseInt(token);
    }
}
