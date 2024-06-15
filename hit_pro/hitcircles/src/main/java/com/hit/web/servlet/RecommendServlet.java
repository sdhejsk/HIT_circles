package com.hit.web.servlet;

import com.hit.service.RecommendService;
import com.hit.service.impl.RecommendServiceimpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/my/recommend")
public class RecommendServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");

        String authorization = req.getHeader("Authorization");
        int user_id = Integer.parseInt(authorization);
        System.out.println("获取推荐文章");
        RecommendService rec = new RecommendServiceimpl();
        String result = rec.rec_article(user_id);
        resp.getWriter().print(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
