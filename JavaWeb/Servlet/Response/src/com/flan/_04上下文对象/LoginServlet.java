package com.flan._04上下文对象;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 创建上下文对象
        ServletContext context = this.getServletContext();
        // 2. 要创建 两个 字符串对象 截取 请求的 两个参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        // 3. 测试一下是否截取了
        // System.out.println(username + "  " + password);

        // 4. 引用 context 的键
        Integer count = (Integer)context.getAttribute("count");
        // 5. 开始写判定
        if ("admin".equals(username) && "admin".equals(password)) {
            if (count == null) {
                count = 1;
            } else  {
                count++;
            }

            context.setAttribute("count", count);
            // System.out.println("有多少用户" + count);
        } else {
            // 转去 login.html
            resp.sendRedirect("login.html");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
