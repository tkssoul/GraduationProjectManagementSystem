好的，我现在需要帮用户写一个高校毕业设计管理系统后端的README.md。首先，我需要根据用户提供的代码上下文和工具调用结果来提取关键信息，确保README的内容准确反映项目的结构和功能。

首先，查看用户提供的代码文件，发现项目使用了Spring Boot框架，MyBatis-Plus作为ORM工具，Redis用于缓存，JWT用于身份验证，MySQL作为数据库。这些都是技术栈的重要部分，需要在README中提到。

接下来，项目的主要功能模块可以从代码中推断。例如，StudentController处理学生的登录、登出和成绩查询；StudentServiceImpl实现了学生登录和成绩获取的业务逻辑；JwtTokenStudentInterceptor用于拦截学生请求，验证JWT令牌；application.yml和application-dev.yml配置了数据库、Redis、MyBatis等参数。这些功能模块需要在README中详细说明。

然后，项目的目录结构可以通过list_dir工具的结果了解。根目录下有.gitignore、pom.xml、README.md（当前为空）、src目录，src下有main（包含java和resources）和test目录。需要在README中列出主要目录及其作用。

另外，配置信息方面，application-dev.yml中配置了本地MySQL和Redis的连接信息，application.yml使用了属性占位符，结合了开发环境配置。这部分需要在部署说明中提到，指导用户如何修改配置以适应自己的环境。

还需要考虑安装和运行步骤。用户可能需要知道如何构建项目（使用Maven）、配置数据库和Redis、启动应用等。根据pom.xml可以推断项目使用Maven管理依赖，所以构建命令是mvn clean install。运行命令则是mvn spring-boot:run。

测试部分，虽然用户没有提供测试代码，但可以建议用户运行mvn test来执行测试用例。

最后，API文档部分，可以提到主要的接口，如学生登录（POST /api/student/login）、登出（POST /api/student/logout）、获取成绩（GET /api/student/grades/published/{id}）等，帮助用户快速了解接口用途。

现在需要将这些信息组织成结构清晰的README.md，包括项目简介、技术栈、目录结构、环境要求、安装与运行、配置说明、API文档、贡献与反馈等部分。确保内容简洁明了，用户能够根据README快速上手项目。

# 高校毕业设计管理系统（后端）

## 项目简介

本系统是针对高校毕业设计流程管理的后端服务，提供学生身份验证、成绩查询等核心功能，基于Spring Boot框架开发，支持JWT身份认证、MyBatis-Plus数据持久化及Redis缓存机制，适用于高校对毕业设计过程的数字化管理。

---

## 技术栈

| 技术/工具 | 说明                  | 版本示例（参考配置）                  |
| --------- | --------------------- | ------------------------------------- |
| 核心框架  | Spring Boot           | -（通过`pom.xml`管理）                |
| ORM工具   | MyBatis-Plus          | -（通过`pom.xml`管理）                |
| 数据库    | MySQL                 | 5.7+（配置于`application-dev.yml`）   |
| 缓存      | Redis                 | 3.2+（配置于`application-dev.yml`）   |
| 身份认证  | JWT（JSON Web Token） | 自定义密钥（配置于`application.yml`） |
| 构建工具  | Maven                 | 3.6+                                  |

---

## 目录结构

```
GraduationProjectManagementSystem/
├── .gitignore         # Git版本控制忽略配置
├── pom.xml            # Maven依赖管理文件
├── README.md          # 项目说明文档（当前文件）
└── src/
    ├── main/
    │   ├── java/      # 核心Java代码
    │   │   └── com/
    │   │       └── foufou/
    │   │           ├── config/       # 配置类（如WebMvc、拦截器）
    │   │           ├── controller/   # 接口控制器（如StudentController）
    │   │           ├── domain/       # 实体类、DTO、VO（如Student、StuGradeVO）
    │   │           ├── mapper/       # MyBatis映射器（如StudentMapper）
    │   │           └── service/      # 业务逻辑层（如StudentServiceImpl）
    │   └── resources/ # 资源文件
    │       ├── application.yml       # 主配置（环境切换、MyBatis等）
    │       ├── application-dev.yml   # 开发环境配置（数据库、Redis）
    │       └── mapper/               # MyBatis SQL映射文件（如StudentMapper.xml）
    └── test/
        └── java/      # 测试代码（待补充）
```

---

## 环境要求

- JDK 1.8+
- MySQL 5.7+（需提前创建数据库`rgks`）
- Redis 3.2+
- Maven 3.6+

---

## 安装与运行

### 1. 克隆项目

```bash
git clone [项目仓库地址]
cd GraduationProjectManagementSystem
```

### 2. 配置数据库与Redis

修改`src/main/resources/application-dev.yml`中的以下参数（根据本地环境调整）：

```yaml
fou:
  datasource:
    host: localhost # MySQL地址
    port: 3306 # MySQL端口
    database: rgks # 数据库名（需提前创建）
    username: root # MySQL用户名
    password: 68942569 # MySQL密码
  redis:
    host: localhost # Redis地址
    port: 6379 # Redis端口
    password: 123456 # Redis密码（无密码则留空）
```

### 3. 构建与启动

```bash
mvn clean install       # 构建项目（首次运行会下载依赖）
mvn spring-boot:run     # 启动后端服务（默认端口8080）
```

---

## 核心功能说明

### 学生模块

| 功能           | 接口路径                               | 说明                                                               |
| -------------- | -------------------------------------- | ------------------------------------------------------------------ |
| 学生登录       | POST /api/student/login                | 接收`studentId`（学号）和`password`（密码），返回JWT令牌及用户信息 |
| 学生登出       | POST /api/student/logout               | 基于请求头中的JWT令牌，清除Redis缓存的令牌                         |
| 查询已发布成绩 | GET /api/student/grades/published/{id} | 根据学生ID查询毕业设计成绩（包含中期、论文、答辩及最终成绩）       |

### 身份认证

- 采用JWT令牌验证，学生接口（`/api/student/**`）需在请求头中携带`token`（令牌）。
- 令牌过期时间：4小时（配置于`application.yml`的`stu-ttl`）。

---

## 贡献与反馈

- 如有Bug或功能建议，请提交Issue。
- 代码贡献请通过Pull Request提交，需符合项目代码规范（Lombok注解、MyBatis-Plus风格）。

---

**注意**：生产环境需修改`application.yml`中的`stu-secret-key`（JWT密钥）和数据库/Redis密码，确保安全性。
