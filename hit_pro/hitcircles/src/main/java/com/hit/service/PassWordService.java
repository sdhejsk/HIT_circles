package com.hit.service;

public interface PassWordService {
    String updatePassword(int userId, String oldPwd, String newPwd, String rePwd);
}
