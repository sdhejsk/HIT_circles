package com.hit.dao;

import com.hit.pojo.UserInfoResponse;

public interface UserDao {
    UserInfoResponse getUserInfoById(int userId);
}
