// AvatarServiceImpl.java
package com.hit.service.impl;

import com.hit.dao.AvatarDao;
import com.hit.dao.impl.AvatarDaoImpl;
import com.hit.service.AvatarService;

public class AvatarServiceImpl implements AvatarService {
    private final AvatarDao avatarDao = new AvatarDaoImpl();

    @Override
    public String updateAvatar(String token, String avatarBase64) {
        int rowsAffected = avatarDao.updateAvatar(token, avatarBase64);
        if (rowsAffected > 0) {
            return "{\"code\": 0, \"message\": \"更新头像成功！\"}";
        } else {
            return "{\"code\": 1, \"message\": \"更新头像失败！\"}";
        }
    }
}
