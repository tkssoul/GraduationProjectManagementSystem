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
