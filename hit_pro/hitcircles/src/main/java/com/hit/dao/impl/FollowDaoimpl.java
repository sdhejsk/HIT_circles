package com.hit.dao.impl;

import com.hit.dao.FollowDao;
import com.hit.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

public class FollowDaoimpl implements FollowDao {

    @Override
    public int change_follow(int user_id, int follow_id) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String search = "select count(*) from user_base where user_id = ? ";
        try {
            Object[] sp1 = {user_id};
            Object[] sp2 = {follow_id};
            Long ser1 = qr.query(search,sp1,new ScalarHandler<>());
            Long ser2 = qr.query(search,sp2,new ScalarHandler<>());
            if(ser1==0 || ser2==0){//用户不存在
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        //用户存在
        try {
            String sql = "select count(*) from user_follow where user_id = ? and friend_id = ? ";
            Object[] param = new Object[]{user_id, follow_id};
            Long friend = qr.query(sql, param, new ScalarHandler<>());
            if(friend!=0){//是关注的好友
                String update = "delete from user_follow where user_id = ? and friend_id = ?";
                Object[] param2 = {user_id, follow_id};
                int result = qr.update(update,param2);
                return result>0?0:1;
            }
            else{//不是关注的好友
                String update = "insert into user_follow (user_id, friend_id) values(?, ?) ";
                Object[] param2 = {user_id, follow_id};
                int result = qr.update(update, param2);
                return result>0?0:1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }
}
