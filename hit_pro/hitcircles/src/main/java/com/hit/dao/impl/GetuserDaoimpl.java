package com.hit.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.dao.GetuserDao;
import com.hit.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GetuserDaoimpl implements GetuserDao {

    @Override
    public List<JSONObject> get_all_user(int user_id) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = new String();
        Object[] param;
        sql = "SELECT \n" +
                "    ub.user_id, \n" +
                "    ub.username, \n" +
                "    ua.avatar_url as avatar,\n" +
                "    CASE WHEN uf.friend_id IS NOT NULL THEN 1 ELSE 0 END as user_state\n" +
                "FROM \n" +
                "    user_base as ub\n" +
                "LEFT JOIN\n" +
                "    user_avatar as ua\n" +
                "    ON ua.user_id = ub.user_id\n" +
                "LEFT JOIN \n" +
                "    user_follow as uf\n" +
                "    ON uf.friend_id = ub.user_id AND uf.user_id = ?\n" +
                "WHERE \n" +
                "    ub.user_id != ? " +
                "order by user_state desc";
        param = new Object[]{user_id, user_id};
        try {
            List<Object[]> results = qr.query(sql, new ArrayListHandler(), param);
            List<JSONObject> extendedResults = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();

            for (Object[] row : results) {
                int id = (int) row[0];



                // Create a map for JSON serialization
                Map<String, Object> resultMap = new LinkedHashMap<>();
                resultMap.put("user_id", row[0]);
                resultMap.put("username", row[1]);
                resultMap.put("avatar", row[2]);
                resultMap.put("state", row[3]);

                JSONObject json = new JSONObject(resultMap);
                extendedResults.add(json);
            }
            return extendedResults;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
    }

}
