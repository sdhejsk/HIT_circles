package com.hit.web.servlet;

import com.hit.service.ArticleService;
import com.hit.service.CommentService;
import com.hit.service.impl.ArticleServiceimpl;
import com.hit.service.impl.CommentServiceimpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/my/comment/list","/my/comment/add","/my/comment/delete"})
@MultipartConfig
public class CommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");

        System.out.println("即将获取参数");
        // 获取Query参数
        //String ser_name = req.getParameter("article_id");
        String article_id_string = req.getParameter("article_id");
        String comment_id_string = req.getParameter("comment_id");
        String authorization = req.getHeader("Authorization");
        System.out.println("comment_id_string: "+comment_id_string);
        System.out.println("article_id: "+ article_id_string);
        System.out.println("Authorazation: "+authorization);

        if(req.getContentType() != null &&req.getContentType().startsWith("multipart/form-data")){//是一个multipart/form-data请求
            // 从请求中获取参数
            String id_String = req.getParameter("article_id");
            String content = req.getParameter("content");
            System.out.println("content: "+content);
            System.out.println("article_id: "+id_String);
            System.out.println("发表评论");
            int user_id = Integer.parseInt(authorization);
            int article_id = Integer.parseInt(id_String);
            CommentService com = new CommentServiceimpl();
            String result = com.pub_comment(user_id, article_id, content);
            resp.getWriter().print(result);
            System.out.println("返回成功!");
        }
        else if(article_id_string!=null){//获取全部评论
            System.out.println("获取全部评论");
            int article_id = Integer.parseInt(article_id_string);
            CommentService com = new CommentServiceimpl();
            String result = com.get_all_comment(article_id);
            resp.getWriter().print(result);
            System.out.println("返回成功!");
        }
        else{//删除评论
            System.out.println("删除评论");
            int comment_id = Integer.parseInt(comment_id_string);
            int user_id = Integer.parseInt(authorization);
            CommentService com = new CommentServiceimpl();
            String result = com.del_comment(comment_id,user_id);

            resp.getWriter().print(result);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
