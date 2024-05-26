package com.hit.web.servlet;

import com.alibaba.fastjson.JSONObject;
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

@WebServlet(urlPatterns = {"/api/login", "/api/reg"})
public class LoginServlet extends HttpServlet {
    private static class User {
        public String username;
        public String password;

        // getters and setters
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        System.out.println("String body: "+body);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("yes");
        Map<String, String> map = null;
        System.out.println("即将获取body信息");
        map = mapper.readValue(body, Map.class);
        System.out.println("即将获取username和password");

        String username = map.get("username");
        String password = map.get("password");
        String repassword = map.get("repassword");
        LoginService lg = new LoginServiceimpl();
        if(repassword==null){
            System.out.println("即将开始登录");
            String result = lg.login(username,password);
            System.out.println("即将开始返回");
            resp.getWriter().print(result);
            System.out.println("返回成功!");
        }
        else{
            if(password.equals(repassword)) {
                System.out.println("即将开始注册");
                String result = lg.register(username, password);
                System.out.println("即将开始返回");
                resp.getWriter().print(result);
                System.out.println("返回成功!");
            }
            else{
                JSONObject object = new JSONObject();
                object.put("code",1);
                object.put("message","两次输入密码不一致！");
                String result = object.toString();
                resp.getWriter().print(result);
                System.out.println("返回成功!");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
