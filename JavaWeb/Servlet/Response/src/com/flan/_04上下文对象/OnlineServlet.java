package com.flan._04上下文对象;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "OnlineServlet", urlPatterns = "/online")
public class OnlineServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 先设置 响应类的 编码
        response.setContentType("text/html;charset=utf-8");
        // 2. 获取 上下文对象 count 值
        ServletContext context = this.getServletContext();
        Object value = context.getAttribute("count");
        System.out.println("当前在线人数 " + value);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
