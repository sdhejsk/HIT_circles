package com.hit.web.servlet;

import com.hit.service.GetuserService;
import com.hit.service.impl.GetuserServiceimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/my/userlist"})
public class GetuserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authorization = req.getHeader("Authorization");
        int user_id = Integer.parseInt(authorization);
        GetuserService us = new GetuserServiceimpl();
        System.out.println("获取全部用户");
        String result = us.get_all_user(user_id);
        resp.getWriter().print(result);
        System.out.println("返回成功！");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
