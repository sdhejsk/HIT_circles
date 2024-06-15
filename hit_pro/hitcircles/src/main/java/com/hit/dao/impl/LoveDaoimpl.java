package com.hit.dao.impl;

import com.hit.dao.LoveDao;
import com.hit.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

public class LoveDaoimpl implements LoveDao {
    @Override
    //返回值：-1代表用户不存在；-2代表文章不存在；-3代表数据库更新错误；其他数字代表该篇文章现在的点赞数
    public int[] change_love(int article_id, int user_id) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String search_user = "select count(*) from user_base where user_id = ? ";
        String search_article = "select count(*) from article_base where article_id = ? ";
        Object[] para_user = {user_id};
        Object[] para_article = {article_id};
        //检查用户是否存在
        try {
            Long ifuser = qr.query(search_user,para_user,new ScalarHandler<>());
            int result[] = new int[2];
            result[0] = 0;
            result[1] = -1;
            if(ifuser==0)return result;
        } catch (Exception e) {
            e.printStackTrace();
            int result[] = new int[2];
            result[0] = 0;
            result[1] = -1;
            return result;
        }
        //检查文章是否存在
        try {
            Long ifarticle = qr.query(search_article,para_article,new ScalarHandler<>());
            int result[] = new int[2];
            result[0] = 0;
            result[1] = -2;
            if(ifarticle==0)return result;
        } catch (Exception e) {
            e.printStackTrace();
            int result[] = new int[2];
            result[0] = 0;
            result[1] = -2;
            return result;
        }
        try {
            String get_love = "select count(*) from article_love where article_id = ? and user_id = ? ";
            Object[] param = new Object[]{article_id,user_id};
            Long iflove = qr.query(get_love, param, new ScalarHandler<>());
            int result[] = new int[2];
            if(iflove!=0){//已经点赞
                result[0] = 0;
                //取消点赞
                String update = "delete from article_love where article_id = ? and user_id = ?";
                Object[] param2 = {article_id,user_id};
                int re = qr.update(update,param2);
                // 获取文章的点赞数
                String get_love_num = "select count(*) from article_love where article_id = ? ";
                Object[] para_lovenum = new Object[]{article_id};
                Long love_num = qr.query(get_love_num, para_lovenum, new ScalarHandler<>());
                if(re==0)result[1] = -3;
                else result[1] = love_num.intValue();
                return result;
            }
            else{//还未点赞
                result[0] = 1;
                //user_id为该篇文章点赞
                String update = "insert into article_love (article_id, user_id) values(?, ?) ";
                Object[] param2 = {article_id,user_id};
                int re = qr.update(update, param2);
                // 获取文章的点赞数
                String get_love_num = "select count(*) from article_love where article_id = ? ";
                Object[] para_lovenum = new Object[]{article_id};
                Long love_num = qr.query(get_love_num, para_lovenum, new ScalarHandler<>());
                if(re==0)result[1] = -3;
                else result[1] = love_num.intValue();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            int result[] = new int[2];
            result[0] = 0;
            result[1] = -3;
            return result;
        }
    }
}
