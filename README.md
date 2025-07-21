# common-spring-boot-starter

这是一个通用的 Spring Boot Starter 项目，旨在为开发者提供一系列便捷的功能模块，以简化 Spring Boot 应用程序的开发流程。该项目包含多个模块，涵盖分页、异常处理、工具类、MyBatis 增强、雪花 ID 生成、线程池管理、敏感数据处理以及全局异常拦截等功能。

## 模块介绍

### common-spring-boot-starter-core
- 提供基础类和工具类，如 `BasePage`、`IdParams`、`R` 等。
- 包含通用异常类 `AxeException` 及其子类。
- 提供常用工具类，如 `AssertUtils`、`CollectionUtils`、`DateUtils`、`StringUtils` 等。

### common-spring-boot-starter-mybatis
- 提供 MyBatis Plus 的批量插入支持。
- 包含自定义 SQL 注入器 `InsertBatchSqlInjector`。
- 提供基础 Mapper 接口 `AxeBaseMapper` 和基础服务类 `AxeServiceImpl`。

### common-spring-boot-starter-sensitive
- 提供敏感数据处理支持，通过 `@Sensitive` 注解实现字段脱敏。
- 支持多种脱敏类型，如手机号、邮箱等。

### common-spring-boot-starter-snowflake
- 提供雪花 ID 生成器 `SnowflakeIdGenerator`。
- 支持通过 `@SnowflakeId` 注解自动填充实体类 ID。
- 包含拦截器 `SnowflakeIdInterceptor`，用于自动设置 ID。

### common-spring-boot-starter-thread
- 提供线程池配置 `ThreadPoolConfig`。
- 包含自定义拒绝策略 `CallerFallbackPolicy`，支持任务重试机制。

### common-spring-boot-starter-web
- 提供全局异常处理器 `GlobalExceptionHandler`。
- 支持统一的响应包装 `ResponseWrapperAdvice`。
- 提供 `@IgnoreResponseWrapper` 注解用于跳过响应包装。

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

### 配置

根据需要在 `application.yml` 中添加相应的配置：

```yaml
axe:
  snowflake:
    worker-id: 1
    datacenter-id: 1
  thread-pool:
    core-size: 5
    max-size: 10
    queue-capacity: 25
    keep-alive-seconds: 60
    thread-name-prefix: axe-task-
    await-termination-seconds: 30
    wait-for-tasks: true
    retry-queue-capacity: 3
    max-retries: 3
```

### 使用示例

#### 异常处理

```java
@RestController
public class ExampleController {

    @GetMapping("/test")
    public R<String> test() {
        throw new AxeException("This is a test exception.");
    }
}
```

#### 敏感数据脱敏

```java
public class User {

    @Sensitive(type = SensitiveType.MOBILE_PHONE)
    private String phone;

    // Getter and Setter
}
```

#### 自动填充 ID

```java
public class User extends BaseEntity {

    private String name;

    // Getter and Setter
}
```

#### 线程池使用

```java
@Autowired
private ThreadPoolTaskExecutor axeTaskExecutor;

@GetMapping("/async")
public void asyncTask() {
    axeTaskExecutor.execute(() -> {
        // Your task here
    });
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