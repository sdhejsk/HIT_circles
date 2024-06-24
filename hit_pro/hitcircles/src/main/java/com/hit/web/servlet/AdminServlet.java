package com.hit.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.service.AdminService;
import com.hit.service.AvatarService;
import com.hit.service.LoginService;
import com.hit.service.impl.AdminServiceimpl;
import com.hit.service.impl.AvatarServiceImpl;
import com.hit.service.impl.LoginServiceimpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/admin/user","/admin/listuser", "/admin/article","/admin/listarticle"})
@MultipartConfig
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");
        String requestURI = req.getRequestURI();
        System.out.println("管理员获取用户信息");
        String authorization = req.getHeader("Authorization");
        //System.out.println("url: "+requestURI);
        if (requestURI.startsWith("/hitcircles/admin/user")) {
            //处理/user请求
            if (req.getContentType() != null && req.getContentType().startsWith("multipart/form-data")) {
                //编辑用户信息
                System.out.println("编辑用户信息");
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                Map<String, Object> map = new HashMap<>();
                upload.setHeaderEncoding("utf-8");

                boolean mul = upload.isMultipartContent(req);
                int user_id = -1;
                String avatar = "";
                try {
                    List<FileItem> list = upload.parseRequest(req);
                    //提取参数
                    for(FileItem item : list){
                        boolean formField = item.isFormField();
                        if(formField){//普通表单项
                            String fieldname = item.getFieldName();
                            String fieldvalue = item.getString("utf-8");
                            System.out.println(fieldname + "=" + fieldvalue);
                            map.put(fieldname, fieldvalue);
                            if(fieldname.equals("id"))user_id = Integer.parseInt(map.get(fieldname).toString());
                        }
                        else{//文件上传项
                            String newfilename = "/images/"+user_id+"_avatar.png";
                            avatar = newfilename;
                            System.out.println("avatar=" + newfilename);
                            InputStream in = item.getInputStream();
                            FileOutputStream out = new FileOutputStream("E:/ruangong/hit_pro/out/artifacts/hitcircles_Web_exploded"
                                    + newfilename);
                            IOUtils.copy(in,out);
                            out.close();
                            in.close();
                            map.put("avatar",newfilename);
                        }
                    }

                    String username = map.get("username").toString();
                    String password = map.get("password").toString();
                    int delete = Integer.parseInt(map.get("delete").toString());
                    int admin_id = Integer.parseInt(authorization);
                    AdminService ad = new AdminServiceimpl();
                    String result = ad.edit_user(admin_id,user_id,username,password,avatar,delete);
                    resp.getWriter().print(result);
                    System.out.println("返回成功！");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                //获取一个用户信息
                System.out.println("获取单个用户");
                String user_id_s = req.getParameter("user_id");

                int user_id = Integer.parseInt(user_id_s);
                int admin_id = Integer.parseInt(authorization);

                System.out.println("user_id: " + user_id);
                System.out.println("admin_id: " + admin_id);
                AdminService ad = new AdminServiceimpl();
                String result = ad.get_userinfo(user_id, admin_id);
                System.out.println("result: " + result);
                resp.getWriter().print(result);
                System.out.println("返回成功");
            }
        } else if (requestURI.equals("/hitcircles/admin/listuser")) {
            System.out.println("获取用户列表");
            //处理/userlist请求
            int admin_id = Integer.parseInt(authorization);
            AdminService ad = new AdminServiceimpl();
            String result = ad.get_userlist(admin_id);
            resp.getWriter().print(result);
            System.out.println("返回成功");
        } else if (requestURI.startsWith("/hitcircles/admin/article")) {
            if (req.getContentType() != null && req.getContentType().startsWith("multipart/form-data")) {
                System.out.println("编辑文章");
                // 编辑文章
                String article_id_s = req.getParameter("article_id");
                String content = req.getParameter("content");
                String delete_s = req.getParameter("delete");
                System.out.println("article_id_s: "+article_id_s);
                System.out.println("content: "+content);
                System.out.println("delete: "+delete_s);

                int admin_id = Integer.parseInt(authorization);
                int article_id = Integer.parseInt(article_id_s);
                int delete = Integer.parseInt(delete_s);

                AdminService ad = new AdminServiceimpl();
                String result = ad.edit_article(admin_id,article_id,content,delete);
                resp.getWriter().print(result);
                System.out.println("返回成功！");

            }
            else{
                //获取一个用户信息
                System.out.println("获取一篇文章");
                String article_id_s = req.getParameter("article_id");

                int article_id = Integer.parseInt(article_id_s);
                int admin_id = Integer.parseInt(authorization);

                System.out.println("article_id: " + article_id);
                System.out.println("admin_id: " + admin_id);
                AdminService ad = new AdminServiceimpl();
                String result = ad.get_articleinfo(article_id,admin_id);
                System.out.println("result: " + result);
                resp.getWriter().print(result);
                System.out.println("返回成功");
            }
        } else if (requestURI.equals("/hitcircles/admin/listarticle")) {
            System.out.println("获取文章列表");
            //处理/articlelist请求
            int admin_id = Integer.parseInt(authorization);
            AdminService ad = new AdminServiceimpl();
            String results = ad.get_articlelist(admin_id);
            resp.getWriter().print(results);
            System.out.println("返回成功");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
