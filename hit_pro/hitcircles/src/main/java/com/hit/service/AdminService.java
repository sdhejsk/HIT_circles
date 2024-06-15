package com.hit.service;

public interface AdminService {
    public String get_userinfo(int user_id, int admin_id);
    public String get_userlist(int admin_id);
    public String edit_user(int admin_id, int user_id, String username, String password,String avatar, int delete);
}
