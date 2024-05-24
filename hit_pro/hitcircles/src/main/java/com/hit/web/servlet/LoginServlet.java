package com.hit.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.service.LoginService;
import com.hit.service.impl.LoginServiceimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {
    private static class User {
        public String username;
        public String password;

        // getters and setters
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
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
        // 将body解析为User对象
        User user = null;
        try {
            user = objectMapper.readValue(body, User.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        String username = user.username;
        String password = user.password;
        // 在此处进行登录处理
        // ...

         */

        StringBuilder sb = new StringBuilder();
        System.out.println("即将开始提取body信息");
        try(BufferedReader reader = req.getReader()) {
            String line;
            while((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("body信息提取完成");
        String body = sb.toString();
        System.out.println("what?");
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("yes");
        Map<String, String> map = null;
        System.out.println("即将获取body信息");
        map = mapper.readValue(body, Map.class);
        System.out.println("即将获取username和password");
        String username = map.get("username");
        String password = map.get("password");
        LoginService lg = new LoginServiceimpl();
        System.out.println("即将开始登录");
        String result = lg.login(username,password);
        System.out.println("登录成功!即将开始返回");
        resp.getWriter().print(result);
        System.out.println("返回成功!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
