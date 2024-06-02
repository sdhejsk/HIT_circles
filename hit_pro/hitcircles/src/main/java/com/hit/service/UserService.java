package com.hit.service;

import com.hit.pojo.UserInfoResponse;

public interface UserService {
    UserInfoResponse getUserInfoByToken(String token);
}
