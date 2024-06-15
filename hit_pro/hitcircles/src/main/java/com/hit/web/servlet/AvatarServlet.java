package com.hit.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hit.service.AvatarService;
import com.hit.service.impl.AvatarServiceImpl;
import org.apache.commons.io.IOUtils;
import com.hit.utils.UUIDUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@WebServlet("/my/update/avatar")
public class AvatarServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");

        String authorization = req.getHeader("Authorization");
        int user_id = Integer.parseInt(authorization);

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        upload.setHeaderEncoding("utf-8");
        boolean mul = upload.isMultipartContent(req);
        if(mul){
            try {
                List<FileItem> list = upload.parseRequest(req);
                if(list != null){
                    for(FileItem item : list){
                        boolean formField = item.isFormField();
                        if(formField){
                            String fieldname = item.getFieldName();
                            String fieldvalue = item.getString("utf-8");
                            System.out.println(fieldname + "=" + fieldvalue);
                        }
                        else{
                            String filename = item.getName();
                            String newfilename = "/images/"+user_id+"_avatar.png";
                            InputStream in = item.getInputStream();
                            FileOutputStream out = new FileOutputStream("E:/ruangong/hit_pro/out/artifacts/hitcircles_Web_exploded"
                                + newfilename);
                            IOUtils.copy(in,out);
                            out.close();
                            in.close();

                            System.out.println("头像已存到本地！");
                            AvatarService ava = new AvatarServiceImpl();
                            String result = ava.updateAvatar(user_id,newfilename);
                            System.out.println(result);
                            resp.getWriter().print(result);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
