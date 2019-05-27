---
typora-root-url: ./
---

# Servlet

* 参考 
  - 深入分析 Java Web 技术内幕(修订版)
  - <https://blog.csdn.net/yw_1207/article/details/78706701>
  - <https://www.cnblogs.com/widget90/p/5640359.html>

<br><br>

## 1 Servlet && Servlet容器

* 先了解 Servlet容器 再了解 Servlet

<br>

### Web 服务器

- Web 服务器使用 HTTP 协议传输数据， 用 URL 就能获取网页信息
  - url是统一资源定位符，对可以从互联网上得到的资源的位置和访问方法的一种简洁的表示，是互联网上标准资源的地址。互联网上的每个文件都有一个唯一的URL，它包含的信息指出文件的位置以及浏览器应该怎么处理它。
- Web 服务器就是把储存的信息 通过 他人使用URL 访问 来把信息传送给访问者

<br>

### Servlet容器 是什么

- 容器: 一个对象A存放另一个对象B或者更多信息集合C的， A 就是 B 或者 C 的容器<br>
- Servlet容器 就是 用来装着 Servlet。
- Servlet容器 的功能
  - Servlet 没有 main方法！(敲黑板) 不能独立运行， 必须由 Servlet容器 来实例化 Servlet 和 调用 Servlet方法。 Servlet生命周期内 都被 Servlet容器 管理。
- 出名的Servlet容器: Tomcat

<br>

### Servlet 是什么

- javax.servlet包中的一个接口。 包含了 三个 方法

  - (1) init()

  - (2) service()

  - (3) destroy()

    ```java
    public interface Servlet {
        void init(ServletConfig var1) throws ServletException;
    
        ServletConfig getServletConfig();
    
        void service(ServletRequest var1, ServletResponse var2) throws ServletException, IOException;
    
        String getServletInfo();
    
        void destroy();
    }
    ```

- Servlet 也是运行在 JVM 中。



### Servlet 与 Servlet容器 的关系

- Tomcat容器 包含着 Container容器 ， Container容器 包含 Engine ， Engine 包含 host ， host包含 Servlet容器， Servlet容器 包含 Context ， Context 包含 Wrapper

- Tomacat-> Container容器-> Engine -> Host-> Servlet容器 -> Context-> Wrapper

  ![TomcatModel](/Pic/TomcatModel.png)

- 真正管理 Servlet 的容器 是 Context容器。