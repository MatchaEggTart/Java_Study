# Servlet 概述

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

<br>

### Servlet 与 Servlet容器 的关系

- Tomcat容器 包含着 Container容器 ， Container容器 包含 Engine ， Engine 包含 host ， host包含 Servlet容器， Servlet容器 包含 Context ， Context 包含 Wrapper

- Tomacat-> Container容器-> Engine -> Host-> Servlet容器 -> Context-> Wrapper

  ![image](<https://github.com/matchaeggtart/Java_Study/raw/master/JavaWeb/Pic/TomcatModel.png>)

- 真正管理 Servlet 的容器 是 Context容器。它管理 Servlet在容器中的包装类 Wrapper

  - 一个Context 对应一个 Web工程

    - 在 Tomcat 配置文件中 有一段标签 tomcat目录/conf/context.xml

    ```xml
    <Context>
        <!-- Default set of monitored resources. If one of these changes, the    -->
        <!-- web application will be reloaded.                                   -->
        <WatchedResource>WEB-INF/web.xml</WatchedResource>
        <WatchedResource>${catalina.base}/conf/web.xml</WatchedResource>
    
        <!-- Uncomment this to disable session persistence across Tomcat restarts -->
        <!--
        <Manager pathname="" />
        -->
    </Context>
    ```

    Context 对应一个工程 并且 管理 Servlet

- Servlet 与 Servlet容器 的解耦

  - Servlet 是个 接口， 为了规范 HTTP 的 Response 跟 Request。 而 Servlet容器是个管理、实例 Servlet的工具， 所以Servlet容器变成独立发展的产品， 有很多种类各特点。 Tomcat 就是其中一个管理Servlet容器 的容器。

- 为什么要有 Servlet容器

  - 因为 企业们觉得我们可能很水， 你可能不会写 监听、请求、响应的代码， 怕你出bug， 就把这些功能写好 包装起来， 留下几个接口给我们， 让我们处理以下 请求成功后我们做什么， 响应成功后又要做什么。 
    - (我们就是小菜鸡， 我用外包别逼逼。 更何况， 不要重复造轮子， 毕竟没钱有开源免费， 有钱用甲骨文。)

<br>

<br>

## 2 Servlet容器启动过程

### Tomcat 的启动类

- Tomcat 7 支持嵌入式功能， 有一个启动类 org.apache.catalina.startup.Tomcat

  - 用start方法 启动 Tomcat
  - 用这个 对象(Tomcat对象)来增加、修改Tomcat的配置参数 (ex: 动态增加Context、Servlet.etc)

  <br>

- 一个 Web应用 对应 一个 Context容器， 也就是Servlet运行时的Servlet容器！

- 

  - 添加 Web应用 要调用 addWebapp()方法

  - 而 addWebapp()方法会 使用 Context类创造实例！

  - ex:

    - 调用Tomcat的实例方法start前 新增Web应用

    ```java
    Tomcat tomcat = getTomcatInstance();
    ...
    tomcat.addWebapp(null, "/examples", appDir.getAbsolutePath());
    tomcat.start();
    ```

    - addWebapp() 代码

    ```java
    public Context addWebapp(Host host, String url, String path) {
    	silence(url);
        Context ctx = new StandardContext();	 /* 创建了 StandardContext容器且被Context的
        									  实例引用 */
    											// 这里开始给 Context容器设置必要的参数
        ctx.setPath(url);
        ctx.setDocBase(path);
        if (defaultRealm == null) {
            initSimpleAuth();
        }
        ctx.setRealm(defaultRealm);
        ctx.addLifecycleListener(new DefaultWebXmlListenner());
        
        ContextConfig ctxCfg = new ContextConfig();	// 这个配置类负责整个Web应用配置的解析工作
        ctx.addLifecycleListener(ctxCfg);
        ctxCfg.setDefaultWebXml("org/apache/catalin/startup/NO_DEFAULT_XML");
        if (host == null) {
            getHost().addChild(ctx);
        } else {
            host.addChild(ctx);
        }
        return ctx;
    }
    ```

  - ContextConfig 这个配置类负责整个Web应用配置的解析工作, 极其重要

  - 最后会把 Context 容器 加到父容器 Host中( getHost()方法 )

<br>

### Lifecycle

- 所有容器都继承 Lifecycle 接口
- Lifecycle 管理着容器的生命周期(所以所有容器都要继承啊， 不然没有生命周期规范怎么快乐玩耍)
- 所有容器的修改和状态的改变都会由它去通知已经注册的观察者 (Listener)

<br>

### ContextConfig

- Context

  - Context容器初始化状态设为init(启动)时，添加到Contex容器的Listener将会被调用

    ```java
        ctx.addLifecycleListener(new DefaultWebXmlListenner());
    ```

- ContextConfig 

  - ContextConfig 继承了 LifecycleListener 接口， 在 调用 addWebapp时被加入 StandardContext容器中。 ContextConfig 负责整个 Web应用 的 配置文件 的 解析工作

    ```java
    ContextConfig ctxCfg = new ContextConfig();
    ctx.addLifecycleListener(ctxCfg);
    ```

- init方法主要功能

  - 创建解析xml的对象， 解析三个配置文件 context Host Context ， 最后设置DocBase
    - 创建用于解析XML配置文件的 contexDigester对象。
    - 读取默认的context.xml 配置文件， 如果存在就解析它。
    - 读取默认的Host配置文件， 如果存在就解析它。
    - 读取默认的Context自身的配置文件， 如果存在则解析它
    - 设置Context 的 DocBase。

- startInternal 方法

  - init方法文成后， 执行 startInternal方法， 很复杂
  - 创建两个对象， 搞好路径， 初始化子容器... 真的很复杂！！！
    - 创建读取资源文件的对象。
    - 创建ClassLoader对象。
    - 设置应用的工作目录
    - 启动相关的辅助类， logger、realm、resources...
    - 修改启动状态， 通知感兴趣的观察者(Web 应用的配置)
    - 子容器的初始化
    - 获取 ServletContext 并设置必要的参数
    - 初始化 “load on startup” 的 Servlet

<br>

## 3 Web 应用的初始化工作

### ContextConfig

- Web应用的初始化工作是在 ContextConfig 的 configureStart方法中实现的
- 主要是 解析 web.xml文件 (里面有一个关键信息———— Web应用的入口)
- 自己看 4000多行

<br>

### 过程

- 先查找 conf/web.xml
- 然后找 hostWebXml 可能在 systemgetProperty("catalina.base")/conf/${EngineName}/${HostName}/web.xml.default
- 接着找 examples/WEB-INF/web.xml
- 把xml各个配置项 解析成 相应属性 保存 在 WebXml 对象中

<br>

- ----以下是Servlet3.0新特性----

- 解析额外xml文件(jar包 META-INF/web-fragment.xml...) 和 对 annotations的支持

- -----------------------------

<br>

- 接着 把 WebXml对象 的 属性 设置到 Context容器中， WebXml 有个 方法 configureContext方法 干这个！ 这里包括创建 Servlet对象、filter、listener...  

- 然后 跑 Servlet的代码

  ```java
  for (ServletDef servlet : servlets.values()) {
      Wrapper wrapper = context.createWrapper();
      ...
      context.addChild(wrapper);
  }
  ```

- 为什么要包装成Context容器中的StandardWrapper， 而不直接包装成Servlet对象？

<br>

### 为什么要要把 Serlet 包装成 Wrapper

- 因为 StandardWrapper是 Tomcat容器中的一部分， 有容器特征， 而Servlet是一套独立Web开发标准， 不应该 强耦合在 Tomcat中。 
  - 我的理解： 又不是一家公司， 每次Servlet更新 你耦合在Tomcat中， 改起来不久累成狗了? 用Wrapper容器包住 Servlet， 操作的是Wrapper， 无论Servlet怎么更新， 只要改一点点适配就好了， 该怎么操作Wrapper就继续怎么操作

<br>

### Context 就是 Servlet容器！

- 除了Servlet包装成 StandardWrapper 并作为子容器(addChild)添加到Context中， 其他所有的 web.xml 属性 都被 解析到 Context中， 所以 Context才是真正运行 Servlet的 Servlet容器！
- 一个Web应用对应一个 Context容器， 容器配置属性由应用的web.xml指定(都被解析到Context里面去了)，所以 知道web.xml能干嘛了

<br>

<br>

# 创建 Servlet 实例

- 虽然 Wrapper添加到 Context容器中， 但还没有实例化它。

<br>

## 1 创建 Servlet 对象

### load-on-startup

- Servlet 的 load-on-startup配置项 大于 0, 那么Context容器启动时就会被实例化

### 两个 Servlet

- 在 conf web.xml文件中 定义了两个 Servlet

  - 1

    ```xml
        <servlet>
            <servlet-name>default</servlet-name>
            <servlet-class>org.apache.catalina.servlets.DefaultServlet</servlet-class>
            <init-param>
                <param-name>debug</param-name>
                <param-value>0</param-value>
            </init-param>
            <init-param>
                <param-name>listings</param-name>
                <param-value>false</param-value>
            </init-param>
            <load-on-startup>1</load-on-startup>
        </servlet>
    ```

  - 2

    ```xml
        <servlet>
            <servlet-name>jsp</servlet-name>
            <servlet-class>org.apache.jasper.servlet.JspServlet</servlet-class>
            <init-param>
                <param-name>fork</param-name>
                <param-value>false</param-value>
            </init-param>
            <init-param>
                <param-name>xpoweredBy</param-name>
                <param-value>false</param-value>
            </init-param>
            <load-on-startup>3</load-on-startup>
        </servlet>
    ```

  <br>

- 所以当 Tomcat启动时， 这两个 Servlet就会被启动。

<br>

### 创建Servlet实例的方法

- 创建实例 从 Wrapper.loadServlet方法开始
  - loadServlet方法 就是为了获取 servletClass， 将它交给 InstanceManager去创建一个基于 servletClass.class 的对象

<br>

## 2 初始化 Servlet

- initServlet()
  - 这个是 StandardWrapper类中的方法
  - 方法就是 调用 Servlet的init()方法， 同时包装了 StandardWrapper对象的 StandardWrapperFacade 作为 ServletConfig 传给 Servlet。
  - 如果 该Servlet 关联的是一个 JSP文件， 就会模拟一次请求， 请求调用这个JSP文件， 以便编译这个JSP文件 为类， 并初始化这个类。
  - 事实上 Servlet从 被 web.xml 解析到完成初始化， 这个过程非常符炸， 包括各种容器状态转化引起的监听事件的出发、各种访问权限的控制和一些不可预料的错误发生的判断行为... p.251页有例子

<br>

# Servlet 体系结构