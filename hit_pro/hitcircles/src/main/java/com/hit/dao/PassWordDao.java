package com.hit.dao;

public interface PassWordDao {
    // 验证旧密码
    int checkOldPassword(int userId, String oldPwd);

    // 更新密码
    int updatePassword(int userId, String newPwd);
}
