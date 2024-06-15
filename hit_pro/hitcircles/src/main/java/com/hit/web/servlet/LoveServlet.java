package com.hit.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.service.FollowService;
import com.hit.service.LoveService;
import com.hit.service.impl.FollowServiceimpl;
import com.hit.service.impl.LoveServiceimpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/my/like"})
public class LoveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");

        System.out.println("更改点赞");
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

        Object article_id = map.get("article_id");
        String authorization = req.getHeader("Authorization");
        int user_id = Integer.parseInt(authorization);
        LoveService love = new LoveServiceimpl();
        String result = love.change_love((int)article_id,user_id);
        System.out.println(result);
        resp.getWriter().print(result);
        System.out.println("返回成功！");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
