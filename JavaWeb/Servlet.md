# Servlet 概述

* 参考 
  - 深入分析 Java Web 技术内幕(修订版)
  - <https://blog.csdn.net/yw_1207/article/details/78706701>
  - <https://www.cnblogs.com/widget90/p/5640359.html>

<br><br>

## Servlet && Servlet容器

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

## Servlet容器启动过程

### Tomcat 的启动类

- Tomcat 7 支持嵌入式功能， 有一个启动类 org.apache.catalina.startup.Tomcat

  - 用start方法 启动 Tomcat
  - 用这个 对象(Tomcat对象)来增加、修改Tomcat的配置参数 (ex: 动态增加Context、Servlet.etc)

  <br>

- 一个 Web应用 对应 一个 Context容器， 也就是Servlet运行时的Servlet容器！

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

## Web 应用的初始化工作

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

## 创建 Servlet 对象

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

## 初始化 Servlet

- initServlet()
  - 这个是 StandardWrapper类中的方法
  - 方法就是 调用 Servlet的init()方法， 同时包装了 StandardWrapper对象的 StandardWrapperFacade 作为 ServletConfig 传给 Servlet。
  - 如果 该Servlet 关联的是一个 JSP文件， 就会模拟一次请求， 请求调用这个JSP文件， 以便编译这个JSP文件 为类， 并初始化这个类。
  - 事实上 Servlet从 被 web.xml 解析到完成初始化， 这个过程非常复杂， 包括各种容器状态转化引起的监听事件的出发、各种访问权限的控制和一些不可预料的错误发生的判断行为... p.251页有例子

<br>
<br>

# Servlet 体系结构

## Servlet 规范

### 握手型交互性

- Java Web应用是基于 Servlet规范运转的

- Servlet的运行模式是 “握手型的交互性”
- “握手型的交互性” 就是两个模块为了交换数据通常都会准备一个交易场景，这个场景一直跟随这个交易过程直到这个交易完成为止。 交易场景的初始化 根据这次 「交易对象」指定的参数来定制的。这些指定参数通常就是一个配置类(ServletConfig)

### 规范的类

- Servlet规范 基于几个类运转

- Servlet
- ServletConfig
- ServletRequest
- ServletContext
- ServletResponse

![image](<https://github.com/matchaeggtart/Java_Study/raw/master/JavaWeb/Pic/ServletRun.png>)

<br>

- 与 Servlet主动关联的是 三个类: ServletConfig、ServletRequest、ServletResponse。
  - 这三个类都是通过容器传递给 Servlet
  - ServletConfig 在 Servlet初始化时就传给 Servlet了， ServletRequest ServletResponse是请求达到时调用 Servlet 传递过来的。

## ServletConfig 和 ServletContext

### ServletConfig

- 这是个接口
- 它的方法 都是为了 获取 Servlet 一些配置属性， 交互时的定制的参数集合
- ServletConfig 是在 Servlet init时容器传过来，StandardWrapper 和 StandardWrapperFacade(是 StandardWrapper 的门面类) 都实现了 ServletConfig 接口，传给Servlet 的是 StandardWrapperFacade 对象。保证 StandardWrapper 能拿到 ServletConfig 规定的数据， 又不暴露 ServletCongit 不需要传递的数据。

### ServletContext

- Servlet的运行模式是 “握手型的交互性”
- ServletContext 是用来描述 交互时的的交互场景
- 比如 从 Context容器获取一些必要信息。 工作路径、容器支持Servlet的最小版本。
- 也有个 Facade 功能的对象 ApplicationContextFacade 对象。同样传输 容器的数据， 和封装容器的数据。 由 ApplicationContext 对象 创造 ApplicationContextFacade 对象 负责接收 封装 ServletContext 的数据

### 理解

- StandarWrapper 创造了 StandardWrapperFacade 对象， 它接收 ServletConfig 的数据， 封装不必要的数据。 然后把数据返回给 StandarWrapper。 ApplicationContext 对象 创造了ApplicationContextFacade 对象， 接收 ServletContext 的数据和封装起来

## ServletReponse ServletRequest

### 概述

- 他们 就是 交互的具体对象， 作为运输工具来传递交互结果。

### HttpServletRequest HttpServletResponse

- 继承了 ServletRequest 和 ServletReponse

### 工作原理

- Tomcat 接到请求， 会创建 coyote库 的 Request类 和 Response类 的两个对象， 轻量级的类， 作用: 服务器接到请求后， 简单解析请求 「快速」分配给 后续县城 去处理， 对象很小， JVM 很容易回收。(就为了 节省资源 跟 快速 传输)
- 然后传输给 一个用户线程去处理 这个 请求时， 又创建 catalina.connector库 的 Request类 和 Response对象。 这两个对象 贯穿整个 Servlet容器(就是一直存在直到交给 Servlet) 直到传给 Servlet， 传给 Servlet的 门面类 RequestFacade 和 ResponseFacade ， 没错 他们也是同样功能， 封装容器的数据。 再交给 HttpServletRequest HttpServletResponse

<br><br>

# Servlet 如何工作

## 调用Servlet

### 工作原理

- 用户 从浏览器 向服务器发送请求 通常 包含如下信息: 

  ```
  http://hostname: port/contextpath/servletpath
  ```

  - hostname port 用来与 服务器 创建 TCP 连接
  - 后面的 URL采用来选择 在服务器中哪个子容器服务用户的请求。

- Tomcat 会创建一个类 来完成映射工作

  - org.apache.tomcat.util.http.mapper
  - 这个类 保存了 Container 容器中 的 所有 子容器 的信息
  - 在请求类 进入 Container容器 前， Mapper 会根据请求的 hostname contextpath 将 host context 容器设置到 Request的 mappingData 属性中。
  - 就是Request 送去 Container 之前， 先把 url 信息 提取出来， 设置好 host context的容器属性， 因为url 就写名 hostname contexpath 至少路径信息可以设置好， 然后把 host容器 context容器 设置到 Request里面， Request就知道它要去哪个容器， 
  - 有点像火车票， 先去火车站买票， 买票给目的地时间(url)， 然后机器根据目的地时间出票(host context)， 然后你拿到票 就知道要去 哪列 哪个座位

<br>

### Mapper

- MapperListener类的init方法

  ```java
  public void init() {
      findDefaultHost();
      Engine engine = (Engine) connector.getService().getContainer();
      engine.addContainerListener(this);
      Container[] conHosts = engine.findChildren();
      for (Container conHost : conHosts) {
          Host host = (Host) conHost;
          if (!LifecyccleState.NEW.equals(host.getState())) {
              host.addLifecycleListener(this);
              registerHose(host);
          }
      }
  }
  ```

  - 造了个 Engine类， 明显容器了， 加了个监听器， 又造了 Host实例， 又加了监听。
  - 所以Contrainer 下的每一个 (敲黑板， 是的 每一个)容器都有个监听器， 任何变化， MapperListener都能被通知， 保存容器关系的 MapperListener 的 mapper 属性也会被修改。
  - for 循环就 把所有 host及子容器 都注册到mapper中 

### 过程

- Request 传入 host url 到 Mapper ， 
- Mapper 设置 请求容器路径， 并且 执行 MapperListener init 设置所有容器 跟监听器， 把路径返回给Request
- Request Pipeline(?) 到 Engine
- 在Engine容器 用 request.getHost 到 Host容器
- 在Host容器 用  request.getContext 去到 Contest容器
- 在Contest容器 用 request.getWrapper 去到 Wrapper容器
- 执行 Filter链 和 listener
- 接下来执行 Servlet 的 servlet 方法， 我们一般不实现servlet， 而是继承 HttpServlet类 或者 GenericServlet(HttpServlet的daddy)类
- 现在不是全部交互用 Servlet来实现， 而是MVC框架来实现， MVC(momodel view controller )原理 是将 所有请求映射到一个 Servlet， 然后实现 service 方法， 这个方法也是MVC框架的入口。
- 当Servlet从Servlet容器中一处是， 生命周期结束， 就执行 Servlet的 destroy方法， 做扫尾工作。

<br>

<br>

# Listener

## 概述

### 是什么

- 基于 观察者模式 设计的 。 Listener 的设计 为 开发 Servlet应用程序提供了 一个快捷手段， 方便从另一个纵向维度控制程序和数据。

### 6个两类事件观察者接口

- EventListeners类型
  - ServletContextAttributeListener
  - ServletRequestAttributeListener
  - ServletRequestListener
  - HttpSessionAttributeListener
- LifecycleListeners类型
  - ServletContextListener
  - HttpSessionListener
- 这些接口 涵盖了整个 Servlet 生命周期中感兴趣的事件。 Listener的实现类可以配置在 wen.xml listener 标签中。
  - 除了ServletContextListener在容器启动之后就不能在添加新的， 其他都可以动态添加。因为 ServletContextListenner 监听的时间不会在发生， 因为 servlet已经建好了

### Spring 监听

- Spring 实现了 ServletContextListener， 当容器加载时启动 Spring容器 ContextLoaderListener 在contextInitialized方法中初始化 Spring容器。 有几个方法 加载 Spring容器

  - 通过 web.xml 的 context-param 标签中配置Spring 的 applicationContext.xml路径， 文件名随便取

  - 如果没有配置， 将在/WEB-INF/路径下查找 默认 applicationContext.xml文件。

  - ContextLoaderListener 的 contextInitialized 方法代码

    ```java
    public void contextInitialized(ServletContextEvent event) {
        this.contextLoader = createContextLoader();
        if (this.contextLoader == null) {
            this.contextLoader = this;
        }
        this.contextLoader.initWebApplicationContext(event.getServlet-Context());
    }
    ```

<br>

<br>

# Filter

## 概述

### 是什么

- Filter 是在 web.xml 常用的配置项， 用 filter filter-mapping 组合来使用 Filter。
- 实际上， Filter可以完成 Servlet同样工作。 甚至更灵活。
- 除了 request response对象外， 他还有 FilterChain对象， 让我们更灵活低控制请求的流转。

### 实现类

- FilterConfig FilterChain 的实现类 分别是 ApplicationFilterConfig 和 ApplicationFilterChain
- Filter 的实现类 由用户自定义， 只要实现 接口定义的三个接口就行 FilterConfig、doFilter 和 destroy (Servlet 主动关联的三个类)
- Filter 类 的 核心 是 传递 FilterChain的对象， 这个对象保存了最终Servlet对象的所有 Filter对象， 这些对象保存在 ApplicationFilterChain 对象的 filters数组中。 
- 在FilterChain链上每执行一个Filter对象， 数组当前计数会加1。直到计数等于数组长度， 当FilterChain 上所有 Filter对象执行完， 再执行最终的 Servlet

<br>

### Filter类中的三个接口

- init(FilterConfig): 初始化接口， 从 Wrapper对象获得 容器的 环境类 ServletContext对象， 并且在web.xml 的 filter 下配置 init-param 参数值
- doFilter(ServletRequest, ServletResponse, FilterChain): 每个用户的请求进来时这个方法都被调用， 而且 在 Servlet的service方法之前调用。 FilterChain参数就是代表当前的整个请求链， 所以用 FilterChain.doFilter 参数的方法 可以继续传递下去 。 这是一个责任链设计模式
- destroy: 当Filter对象被销毁时， 这个方法被调用， Web容器调用这个方法后， 容器会在调用一次 doFilter (why?)

### 过程

- ? 到底 能不能用 Filter 取代 Servlet, 不能， 它只是进入Servlet前的 一个 筛选。

### 理解

- 就是 Servlet 执行 request responde 之前， 会执行doFithler， 再执行 Servlet 的 service， 执行完 service， responde 后， 在执行 doFilter
- 坐飞机中转托运， 中转的时候把托运东西拿出来 扔掉还是加点东西。

<br>

<br>

# Servlet 中的url-pattern

## 配置项

### 有什么配置项

- 在 web.xml 中 servlet-mapping 和 filter-mapping 都有 url-pattern 配置项

### 什么时候配置

- 请求 会分配到 Servlet 是通过 Mapper类 完成， 这个类会根据请求的URL来匹配在每个 Servlet中配置的 url-pattern， 所以在一个请求被创建时就已经匹配了。
- Filter 的 url-pattern 匹配 是在 创建 ApplicationFilterChain 对象时进行的， 它会把所有定义的 Filter 的 url-pattern 与当前的URL匹配， 匹配成功就把这个Filter保存到 ApplicationFilterChain 的 filters 数组中， 然后在 FilterChain 中依次调用。

### 匹配失败

- 在 web.xml 加载时， 会检查 url-pattern 配置是否符合规则， 这个检查是在 StandardContext 的 validateURLPattern 方法中检查的， 如果检查不成功， Context 容器启动会失败， 并且报错: 

  ```
  java.lang.IllegalArgumentException:Invalid<url-pattern>/a/*.htm in Servlet mapping 错误
  ```

### url-pattern 的解析规则

有三种规则， Servlet 和 Filter 是一样的

- 解析规则
  - 精确匹配: 如 /foo.htm 只会匹配 /foo.htm 这个 URL
  - 路径匹配: 如 /foo/* 会匹配所有前缀是 foo 的 URL
  - 后缀匹配: 如 *.htm 会匹配所有以 .htm 为后缀的 URL
- 匹配顺序
  - 如果 Servlet 同时定义了多个 url-pattern， 会先用精确匹配， 然后考虑最长路径匹配，最后根据后缀匹配
  - 一次请求只会成功匹配到一个 Servlet
  - Filter匹配规则 跟 Servlter 有些不同。 只要匹配成功， 这些 Filter 都会在请求链上被调用。
  - 请遵守三种规则， 其他写法都是不允许的。