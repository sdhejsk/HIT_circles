package com.hit.web.servlet;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.service.ArticleService;
import com.hit.service.LoginService;
import com.hit.service.impl.ArticleServiceimpl;
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

@WebServlet("/my/article/list")
public class ArticleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("即将获取参数");
        // 获取Query参数
        String ser_name = req.getParameter("username");
        // 获取Header参数
        String authorization = req.getHeader("Authorization");
        System.out.println("获取参数成功！");
        int user_id;
        user_id = Integer.parseInt(authorization);

        ArticleService art = new ArticleServiceimpl();
        System.out.println("开始查找文章");
        String result = art.get_all_articles(user_id,ser_name);
        System.out.println("查找文章结束");
        resp.getWriter().print(result);
        System.out.println("返回成功!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
