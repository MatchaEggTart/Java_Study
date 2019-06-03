package com.flan._04上下文对象;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet (urlPatterns = "/image")
public class ReadResourceImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 创建一个 上下文对象， 用来把 服务器文件 变成流
        ServletContext context = this.getServletContext();

        // 2. 创建输入流, 引用上下文对象的 getResourceAsStream方法
        InputStream in = context.getResourceAsStream("WEB-INF/img/404a.jpg");

        // 3. 创建输出流 引用response 的 Servlet的输出流
        OutputStream out = resp.getOutputStream();

        // 4. 把输入流 的 数据 传给 输出流
        int len = -1;
        byte[] buf = new byte[1024];

        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }

        String path = context.getRealPath("WEB-INF/img/404a.jpg");
        System.out.println(path);
    }
}