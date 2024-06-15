package com.hit.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.service.FollowService;
import com.hit.service.LoginService;
import com.hit.service.impl.FollowServiceimpl;
import com.hit.service.impl.LoginServiceimpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/my/concern"})
public class FollowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");

        System.out.println("更改关注");
        StringBuilder sb = new StringBuilder();
        try(BufferedReader reader = req.getReader()) {
            String line;
            while((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String body = sb.toString();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = null;
        System.out.println("即将获取body信息");
        map = mapper.readValue(body, Map.class);

        Object follow_id = map.get("user_id");
        String authorization = req.getHeader("Authorization");
        //int follow_id = Integer.parseInt(follow_id_string);
        int user_id = Integer.parseInt(authorization);
        FollowService fol = new FollowServiceimpl();
        String result = fol.change_follow(user_id,(int)follow_id);
        resp.getWriter().print(result);
        System.out.println("返回成功！");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
