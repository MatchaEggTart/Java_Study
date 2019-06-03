package com.flan._05文件下载;

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
import java.io.PrintWriter;

/**
 目标: 使用Servlet实现文件下载

 实现步骤
 1. 从链接上得到文件名
 2. 设置 Content-disposition 头
 3. 得到文件的输入流：InputStream in
 4. 得到 response 的输出流：OutputStream out
 5. 写出到浏览器端
 */
@WebServlet(urlPatterns = "/down")
public class DownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 先创建个 ServletContext
        ServletContext context = this.getServletContext();
        // 2. 获得点击的文件名 因为 down?filename=file.txt 我们知道键 是谁了
        String filename = request.getParameter("filename");

        // 3. 把文件放进 输入流
        InputStream in = context.getResourceAsStream("download/" + filename);

        // 4. 造输出流前 必须设置 响应头的 attachment (下载附件的意思)
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);

        // 5. 造个输出流
        OutputStream out = response.getOutputStream();

        int len = -1;
        byte[] buf = new byte[1024];

        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, -1);
        }

        System.out.println(filename + " 正在下载");
    }
}
