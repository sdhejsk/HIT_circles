package com.hit.web.servlet;

import com.hit.pojo.UserInfoResponse;
import com.hit.service.UserService;
import com.hit.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/my/userinfo")
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");

        // 获取 Authorization 头部信息
        String authorizationHeader = request.getHeader("Authorization");

        // 检查 Authorization 头部信息是否为空
        if (authorizationHeader == null) {
            sendErrorResponse(response, 1, "身份认证失败！");
            return;
        }

        // 解析 token 获取用户信息
        //String token = authorizationHeader.substring(7); // 移除 "Bearer " 前缀
        UserInfoResponse userInfo = userService.getUserInfoByToken(authorizationHeader);

        // 构建响应
        if (userInfo != null) {
            String jsonResponse = "{\"code\":0,\"message\":\"获取用户信息成功！\",\"data\":" + userInfoToJson(userInfo) + "}";
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        } else {
            String jsonResponse = "{\"code\":1,\"message\":\"获取用户信息失败！\"}";
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        }
    }

    // 将 UserInfoResponse 对象转换为 JSON 字符串
    private String userInfoToJson(UserInfoResponse userInfo) {
        return "{\"id\":" + userInfo.getId() + ",\"username\":\"" + userInfo.getUsername() + "\",\"avatar\":\"" + userInfo.getAvatar() + "\",\"is_admin\":\"" + userInfo.getIs_admin() + "\"}";
    }

    // 发送错误响应
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        String jsonResponse = "{\"code\":" + statusCode + ",\"message\":\"" + message + "\"}";
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);
        response.getWriter().write(jsonResponse);
    }
}
