# student-treacher-manager

## 环境
1. 安装jdk1.8以上
2. 安装git

## 部署
1. 克隆代码 `git clone https://github.com/homesangsang/student-treacher-manager.git`
2. 进入到下载代码的目录，打开终端
3. 输入windows系统，输入`mvnw package`,linux或mac输入`./mvnw package`
4. 上面步骤会在当前目录下生成target目录，`cd target`
5. 运行`java -jar student-teacher-manager-0.0.1-SNAPSHOT.war`


# 项目说明
* 本项目采用springboot开发，底层采用maven构建项目。
* 后端框架采用spring-data-jpa作为数据持久层
* springMVC作为前端控制器，接收前端的HTTP请求，并返回响应的数据。
    springMVC的依赖在spring-boot-starter-web
* 权限控制使用springSecurity，基于springSecurity实现了JWT（JSON Web Token）认证
* 前端采用layui作为基础框架，其中最主要的为layui的数据表格和表单验证。

# 项目开发进程
1. 设计底层数据库表，根据E-R图和UML图，数据流图设计底层数据支撑
2. 采用自底向上的方式开发，先开发数据持久层（entity+dao），接着开发业务逻辑层（service），再开发接口（controller），最后开发前端
3. 权限管理在在底层数据库表创建好之后就开始写认证接口
4. 权限管理使用springSecurity开发，需要先创建MyUser类，封装JWTUser类，通过JwtUserFactory工厂类进行对象创建
5. 使用拦截器的方式，判断HTTP请求是否有权限。其中JwtAuthenticationFilter为鉴权，JWTLoginFilter为登录.路径为cn.edu.qlu.studentteachermanager.filter
6. 在MyWebSecurityConfig类中配置了拦截器的触发环境.路径为cn.edu.qlu.studentteachermanager.config
7. 判断接口是否有权限，采用注解判断权限字符串的方式，在cn.edu.qlu.studentteachermanager.controller中的TeacherController, StudentController, AdminController中均有注解`@Secured`
8. 项目采用前后端完全分离来实现，前端为纯HTML+CSS+JS，没有使用任何模板引擎（JSP，Thymeleaf，FreeMark等），前后端通过ajax来传递数据


# 项目开发环境
1. IDE采用IntelliJ IDEA
2. JDK 1.80_161
3. springBoot v1.5.8.RELEASE
4. 依赖环境
    1. spring-boot-starter-data-jpa : spring-data-jpa,底层为Hibernate
    2. spring-boot-starter-security : spring的安全认证框架，支持Session，JWT， Oauth 2
    3. spring-boot-starter-web : springMVC，前端控制器
    4. spring-boot-devtools : springboot开发模式支持，支持热加载等
    5. spring-boot-starter-actuator : 在开发的过程中查看接口映射（Mapping）的框架
    6. JJWT ： java第三方JWT框架
    7. spring-boot-starter-tomcat : 内嵌tomcat，提供servlet支持
5. 项目打包为war包，参考 部署 部分
以上均可从pom.xml中查看到
