# common-spring-boot-starter

这是一个通用的 Spring Boot Starter 项目，旨在为开发者提供一系列便捷的功能模块，以简化 Spring Boot 应用程序的开发流程。该项目包含多个模块，涵盖分页、异常处理、工具类、MyBatis 增强、雪花 ID 生成、线程池管理、敏感数据处理以及全局异常拦截等功能。

## 模块介绍

###  1. common-spring-boot-starter-core（核心基础模块）

**核心能力**：提供应用开发的基础支撑组件，实现通用功能的标准化封装

- 统一异常处理
  - 定义基础异常类 `AxeException` 及业务子类体系
  - 全局异常拦截器自动捕获未处理异常，统一封装响应格式
- 工具类集合
  - `AssertUtils`：增强断言工具，简化参数校验逻辑
  - `CollectionUtils`：集合操作增强，提供批量处理、空安全判断等方法
  - `DateUtils`：日期时间格式化、转换、计算等工具方法
  - `StringUtils`：字符串脱敏、校验、格式化等增强功能
- 响应标准化
  - 统一接口返回格式 `ResultFull`，包含状态码、消息及数据字段
  - 自动包装响应结果，减少前后端交互格式适配成本

**适用场景**：所有 Spring Boot 应用的基础能力集成，尤其适合企业级项目的标准化开发。

### 2. common-spring-boot-starter-mybatis（数据访问增强模块）

**核心能力**：基于 MyBatis Plus 扩展，优化数据访问层实现

- 通用数据操作
  - 通用接口 `AxeBaseMapper` 封装 CRUD 基础操作
  - 服务实现类 `AxeServiceImpl` 提供批量插入优化，支持大数据量高效写入
- 查询增强
  - 集成 PageHelper 分页插件，支持物理分页与复杂查询场景
  - 自动处理分页参数，简化分页查询代码
- 并发控制
  - 内置乐观锁插件，通过版本号机制解决并发更新冲突
  - 自动拦截更新操作，实现版本号递增与冲突检测

**适用场景**：基于 MyBatis Plus 的数据访问层开发，需标准化数据库操作的项目。

### 3.common-spring-boot-starter-security（数据安全模块）

**核心能力**：提供敏感数据处理机制，保障数据隐私与存储安全

- 敏感信息脱敏
  - `@Sensitive`注解标记需脱敏字段，支持多种预设类型：
    - 手机号（中间 4 位隐藏）、邮箱（域名前字符部分隐藏）
    - 身份证号（保留首尾字符）、银行卡号（保留后 4 位）
  - 序列化时自动脱敏，不影响原始数据存储
- 字段加密存储
  - `@Encrypt`注解实现字段级加密，支持算法：
    - Argon2：适用于密码存储的现代加密算法
    - BCrypt：工业级密码哈希算法，自带盐值
  - 自动完成加密存储与解密读取，透明化加密逻辑

**适用场景**：对用户隐私数据（如手机号、身份证）和敏感配置（如密码）有保护需求的应用。

### 4.common-spring-boot-starter-snowflake（分布式 ID 模块）

**核心能力**：基于雪花算法的分布式唯一 ID 生成方案

- 高可用 ID 生成器
  - `SnowflakeIdGenerator` 线程安全实现，支持每秒生成百万级 ID
  - 可配置 Worker ID 与 Datacenter ID，适配多节点部署
- 自动填充机制
  - `@SnowflakeId` 注解标记实体主键字段
  - `SnowflakeIdInterceptor` 拦截器在数据插入前自动生成并填充 ID
  - 支持未手动赋值时自动填充，避免业务代码侵入

**适用场景**：分布式系统、微服务架构中需保证主键全局唯一的场景，如订单、用户等核心表。

### 5. common-spring-boot-starter-thread（线程池管理模块）

**核心能力**：提供可配置的线程池解决方案，优化异步任务执行

- 灵活配置
  - `ThreadPoolConfig`支持核心参数自定义：
    - 核心线程数、最大线程数、队列容量
    - 线程空闲时间、线程名称前缀
  - 支持通过配置文件动态调整参数，无需代码变更
- 增强容错
  - 自定义拒绝策略 `CallerFallbackPolicy`，支持任务重试机制
  - 可配置重试次数与重试队列容量，提升任务执行成功率

**适用场景**：高并发场景下的异步任务处理，如消息推送、数据同步、批量处理等。

## 快速开始

### 添加依赖

在你的 Spring Boot 项目的 `pom.xml` 中添加以下依赖：

```xml
<dependency>
    <groupId>com.axe</groupId>
    <artifactId>common-spring-boot-starter-all</artifactId>
    <version>1.0.0</version>
</dependency>
```

> 如需单独引入模块，可替换 artifactId 为具体模块名（如 `common-spring-boot-starter-core`）

### 配置

根据需要在 `application.yml` 中添加相应的配置：

```yaml
axe:
  # 雪花算法配置
  snowflake:
    worker-id: 1          # 节点 ID（0-31）
    datacenter-id: 1      # 数据中心 ID（0-31）
  
  # 线程池配置
  thread-pool:
    core-size: 5          # 核心线程数
    max-size: 10          # 最大线程数
    queue-capacity: 50    # 任务队列容量
    keep-alive-seconds: 60  # 空闲线程存活时间
    thread-name-prefix: axe-task-  # 线程名称前缀
    await-termination-seconds: 30  # 线程池关闭等待时间
    wait-for-tasks: true   # 是否等待任务完成后关闭
    retry-queue-capacity: 50  # 重试队列容量
    max-retries: 3         # 最大重试次数
```

### 使用示例

#### 异常处理

```java
@RestController
public class ExampleController {
    
    @GetMapping("/test/exception")
    public ResultFull<String> testException() {
        // 直接抛出异常，全局拦截器自动处理
        AssertUtils.isTrue(false, "参数校验失败");
        // 或手动抛出业务异常
        throw new AxeException("业务处理异常", 5001);
    }
}
```

#### 敏感数据脱敏

```java
public class UserInfo {
    private Long id;
    
    // 手机号脱敏（示例：138****1234）
    @Sensitive(type = SensitiveType.PHONE)
    private String phone;
    
    // Getter/Setter 省略
}
```

#### 数据加密

```java
public class UserInfo {
    private Long id;
    
    // 密码加密存储（使用 Argon2 算法）
    @Encrypt(type = EncryptType.ARGON2_ID)
    private String loginPwd;
    
    // Getter/Setter 省略
}
```

#### 分布式 ID 自动填充

```java
@TableName("t_user")
public class User {
    // 插入时自动生成雪花 ID
    @SnowflakeId
    private Long id;
    
    private String username;
    // 其他字段省略
}
```

#### 线程池使用

```java
@Service
public class AsyncService {
    
    // 注入框架线程池
    @Resource
    private ThreadPoolTaskExecutor axeTaskExecutor;
    
    public void executeAsyncTask() {
        // 提交异步任务
        axeTaskExecutor.execute(() -> {
            // 执行耗时操作（如文件处理、网络请求）
            log.info("异步任务执行中...");
        });
    }
}
```

## 贡献指南

欢迎贡献代码和改进文档。请遵循以下步骤：

1. Fork 本仓库。
2. 创建新分支 (`git checkout -b feature/new-feature`)。
3. 提交更改 (`git commit -am 'Add new feature'`)。
4. 推送分支 (`git push origin feature/new-feature`)。
5. 创建 Pull Request。

## 许可证

本项目使用 MIT 许可证。详情请查看 [LICENSE](LICENSE) 文件。