package com.hit.web.servlet;

import com.hit.service.ArticleService;
import com.hit.service.impl.ArticleServiceimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/my/article/list", "/my/article/info", "/my/article/add"})
@MultipartConfig
public class ArticleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("即将获取参数");
        // 获取Query参数
        String ser_name = req.getParameter("username");
        String article_id_string = req.getParameter("article_id");
        String authorization = req.getHeader("Authorization");

        if(req.getContentType() != null && req.getContentType().startsWith("multipart/form-data")){//是一个multipart/form-data请求
            // 从请求中获取参数

            String from = req.getParameter("from");
            String content = req.getParameter("content");
            String id = req.getParameter("id");
            if(from!=null) {//发布文章
                int from_id = -1;
                int user_id = Integer.parseInt(authorization);
                if (from != null) {
                    from_id = Integer.parseInt(from);
                }
                ArticleService art = new ArticleServiceimpl();
                System.out.println("content: " + content);
                String result = art.pub_article(user_id, from_id, content);
                resp.getWriter().print(result);
            }
            else if(id!=null){
                if(content.equals("")){//删除文章
                    int article_id = Integer.parseInt(id);
                    int user_id = Integer.parseInt(authorization);
                    ArticleService art = new ArticleServiceimpl();
                    String result = art.del_article(user_id, article_id);
                    resp.getWriter().print(result);
                }
                else if(content!=null){//编辑文章
                    int article_id = Integer.parseInt(id);
                    int user_id = Integer.parseInt(authorization);
                    ArticleService art = new ArticleServiceimpl();
                    String result = art.edit_article(user_id,article_id,content);
                    resp.getWriter().print(result);
                }
                else{//获取一篇文章
                    int article_id = Integer.parseInt(article_id_string);
                    ArticleService art = new ArticleServiceimpl();
                    System.out.println("开始查找文章");
                    String result = art.get_an_article(article_id);
                    System.out.println("查找文章结束");
                    resp.getWriter().print(result);
                    System.out.println("返回成功!");
                }
            }
        }
        else if(article_id_string!=null){//获取一篇文章
            int article_id = Integer.parseInt(article_id_string);
            ArticleService art = new ArticleServiceimpl();
            System.out.println("开始查找文章");
            String result = art.get_an_article(article_id);
            System.out.println("查找文章结束");
            resp.getWriter().print(result);
            System.out.println("返回成功!");
        }
        else{//获取全部文章
            // 获取Header参数
            System.out.println("获取参数成功！");
            int user_id;
            user_id = Integer.parseInt(authorization);

            ArticleService art = new ArticleServiceimpl();
            System.out.println("开始查找文章");
            String result = art.get_all_articles(user_id, ser_name);
            System.out.println("查找文章结束");
            resp.getWriter().print(result);
            System.out.println("返回成功!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
