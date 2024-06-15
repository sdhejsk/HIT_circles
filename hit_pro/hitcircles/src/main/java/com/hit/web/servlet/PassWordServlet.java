package com.hit.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.service.PassWordService;
import com.hit.service.impl.PassWordServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@WebServlet("/my/updatepwd")
public class PassWordServlet extends HttpServlet {
    // @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");

        String authHeader = req.getHeader("Authorization");
        if (authHeader == null) {
            System.out.println("Authorization is null!");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().print("{\"code\": 1, \"message\": \"身份认证失败！\"}");
            return;
        }

        //String token = authHeader.substring(7); // Remove "Bearer " prefix

        int userId = Integer.parseInt(authHeader);

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String body = sb.toString();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(body, Map.class);

        String oldPwd = map.get("old_pwd");
        String newPwd = map.get("new_pwd");
        String rePwd = map.get("re_pwd");

        PassWordService passWordService = new PassWordServiceImpl();
        String result = passWordService.updatePassword(userId, oldPwd, newPwd, rePwd);

        resp.setContentType("application/json");
        resp.getWriter().print(result);
    }

    private int extractUserIdFromToken(String token) {
        // 假设 token 中包含 user_id 部分。在实际应用中，您应该使用 JWT 库来解析 token。
        return Integer.parseInt(token.split("\\.")[1]);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
