# 微服务框架技术架构分享

## 目录
1. [项目背景](#项目背景)
2. [技术选型](#技术选型)
3. [架构设计](#架构设计)
4. [核心功能](#核心功能)
5. [最佳实践](#最佳实践)
6. [快速开始](#快速开始)
7. [常见问题](#常见问题)

## 项目背景

### 项目目标
- 构建一个可扩展、高性能的微服务框架
- 提供统一的开发规范和最佳实践
- 降低微服务开发门槛
- 提高开发效率和代码质量

### 解决的问题
1. 技术栈不统一
   - 统一技术选型
   - 规范开发流程
   - 提供最佳实践

2. 重复造轮子
   - 封装通用组件
   - 提供开箱即用的功能
   - 减少重复开发

3. 运维成本高
   - 统一监控方案
   - 标准化部署流程
   - 自动化运维支持

## 技术选型

### 核心框架
- Spring Boot 2.7.x：提供基础框架支持
- Spring Cloud 2021.0.x：微服务生态
- Spring Cloud Alibaba 2021.0.x：阿里微服务解决方案

### 服务注册与发现
- Consul 1.12.x：服务注册中心
- Spring Cloud Consul：服务发现实现

### 网关（可选）
- Spring Cloud Gateway：新一代网关
- Sentinel 1.8.x：限流、熔断、降级
> 注：网关模块需要单独创建，本框架不包含网关实现

### 数据库
- MySQL 8.0.x：关系型数据库
- MyBatis-Plus 3.5.x：ORM框架
- Dynamic Datasource：动态数据源

### 缓存
- Redis 6.x：分布式缓存
- Redisson 3.17.x：分布式锁、限流器

### 消息队列
- RocketMQ 4.9.x：消息中间件
- Spring Cloud Stream：消息驱动

### 监控
- SkyWalking 8.9.x：分布式追踪
- ELK 7.x：日志收集分析
- Prometheus + Grafana：监控告警

### 安全
- Spring Security：安全框架
- JWT：无状态认证
- OAuth2：授权协议

### 开发工具
- Lombok：简化代码
- MapStruct：对象映射
- Hutool：工具集
- Knife4j：API文档

## 架构设计

### 整体架构
```
                                    [API Gateway] (可选)
                                          |
                    +---------------------+---------------------+
                    |                     |                     |
              [Service A]           [Service B]           [Service C]
                    |                     |                     |
            +-------+-------+     +-------+-------+     +-------+-------+
            |               |     |               |     |               |
        [Database]     [Redis]  [Database]    [Redis]  [Database]    [Redis]
```

说明：
1. API Gateway 是可选的，需要单独创建网关服务
2. 服务之间可以直接通过 RPC 或 HTTP 调用
3. 每个服务可以独立选择启用的模块

### 核心模块
1. micro-service-starter
   - 框架启动器
   - 自动配置
   - 条件装配

2. micro-service-common
   - 通用工具类
   - 基础组件
   - 常量定义

3. micro-service-discovery
   - 服务注册
   - 服务发现
   - 健康检查

4. micro-service-database
   - 数据源管理
   - 事务管理
   - 分库分表

5. micro-service-redis
   - 缓存管理
   - 分布式锁
   - 限流器

6. micro-service-mq
   - 消息发送
   - 消息消费
   - 消息追踪

7. micro-service-rpc
   - Dubbo集成
   - OpenFeign集成
   - 负载均衡

8. micro-service-logging
   - 日志收集
   - 链路追踪
   - 监控告警

## 核心功能

### 0. 按需启用模块
```yaml
micro:
  service:
    # 启用/禁用 MQ 模块
    mq:
      enabled: true
    
    # 启用/禁用 Redis 模块
    redis:
      enabled: true
    
    # 启用/禁用数据库模块
    database:
      enabled: true
    
    # 启用/禁用 RPC 模块
    rpc:
      enabled: true
    
    # 启用/禁用服务发现模块
    discovery:
      enabled: true
    
    # 启用/禁用日志模块
    logging:
      enabled: true
```

说明：
1. 所有模块默认都是禁用的，需要显式配置 `enabled: true` 来启用
2. 未启用的模块不会加载相关配置和Bean
3. 可以根据项目需求选择性启用所需模块
4. 建议只启用必要的模块，避免引入不必要的依赖

### 1. 服务注册与发现
```yaml
spring:
  cloud:
    consul:
      host: localhost
      port: 8500
      discovery:
        service-name: ${spring.application.name}
        instance-id: ${spring.application.name}:${server.port}
        prefer-ip-address: true
```

### 2. 动态数据源
```yaml
spring:
  datasource:
    dynamic:
      primary: master
      strict: false
      datasource:
        master:
          url: jdbc:mysql://localhost:3306/your_db
          username: your_username
          password: your_password
        slave:
          url: jdbc:mysql://localhost:3306/your_db_slave
          username: your_username
          password: your_password
```

### 3. 分布式缓存
```yaml
spring:
  redis:
    host: localhost
    port: 6379
    database: 0
    timeout: 10000
    lettuce:
      pool:
        max-active: 8
        max-wait: -1
        max-idle: 8
        min-idle: 0
```

### 4. 消息队列
```yaml
rocketmq:
  name-server: localhost:9876
  producer:
    group: ${spring.application.name}-producer
    send-message-timeout: 3000
  consumer:
    group: ${spring.application.name}-consumer
    pull-batch-size: 10
```

### 5. RPC调用
```yaml
dubbo:
  protocol:
    name: dubbo
    port: -1
  registry:
    address: spring-cloud://localhost
  consumer:
    check: false
    timeout: 3000
    retries: 2

spring:
  cloud:
    openfeign:
      client:
        config:
          default:
            connectTimeout: 5000
            readTimeout: 5000
```

### 6. 日志追踪
```yaml
logging:
  level:
    root: INFO
    com.example: DEBUG
  file:
    name: logs/${spring.application.name}.log
    max-size: 100MB
    max-history: 30

skywalking:
  oap:
    server: localhost:11800
  agent:
    service_name: ${spring.application.name}
```

## 最佳实践

### 1. 服务命名规范
- 服务名：micro-service-{module}
- 包名：com.example.{module}
- 接口名：{Module}Service
- 实现类：{Module}ServiceImpl

### 2. 配置管理
- 使用 Apollo 配置中心
- 敏感配置加密存储
- 环境配置分离
- 配置版本控制

### 3. 异常处理
- 统一异常处理
- 业务异常与系统异常分离
- 异常信息国际化
- 异常链路追踪

### 4. 日志规范
- 使用 SLF4J + Logback
- 统一日志格式
- 关键操作必须记录日志
- 异常必须记录堆栈信息
- 日志分级管理

### 5. 接口规范
- RESTful API 设计
- 统一响应格式
- 接口版本控制
- 接口文档自动生成
- 接口安全控制

### 6. 安全规范
- 统一认证授权
- 敏感数据加密
- 防SQL注入
- 防XSS攻击
- 防CSRF攻击

### 7. 性能优化
- 合理使用缓存
- 异步处理
- 分页查询
- 索引优化
- 连接池优化

### 8. 监控告警
- 服务健康检查
- 性能指标监控
- 异常告警
- 容量规划
- 资源使用监控

## 快速开始

### 1. 添加父工程依赖
```xml
<parent>
    <groupId>com.example</groupId>
    <artifactId>micro-service-frame</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <relativePath>../micro-service-frame/pom.xml</relativePath>
</parent>
```

### 2. 添加启动器依赖
```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>micro-service-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### 3. 配置 application.yml
```yaml
spring:
  application:
    name: your-service-name
  cloud:
    consul:
      host: localhost
      port: 8500
  datasource:
    dynamic:
      primary: master
      datasource:
        master:
          url: jdbc:mysql://localhost:3306/your_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
          username: your_username
          password: your_password
  redis:
    host: localhost
    port: 6379

dubbo:
  protocol:
    name: dubbo
    port: -1
  registry:
    address: spring-cloud://localhost
  consumer:
    check: false

rocketmq:
  name-server: localhost:9876
  producer:
    group: ${spring.application.name}-producer
```

### 4. 创建应用类
```java
@SpringBootApplication
public class YourApplication {
    public static void main(String[] args) {
        SpringApplication.run(YourApplication.class, args);
    }
}
```

## 常见问题

### 1. 服务注册失败
- 检查 Consul 服务是否正常运行
- 检查网络连接是否正常
- 检查服务配置是否正确

### 2. 数据库连接问题
- 检查数据库服务是否正常运行
- 检查连接配置是否正确
- 检查数据库用户权限

### 3. Redis 连接问题
- 检查 Redis 服务是否正常运行
- 检查连接配置是否正确
- 检查 Redis 密码是否正确

### 4. 消息发送失败
- 检查 RocketMQ 服务是否正常运行
- 检查生产者配置是否正确
- 检查网络连接是否正常

### 5. 性能问题
- 检查数据库索引
- 检查缓存使用
- 检查线程池配置
- 检查JVM参数

### 6. 监控问题
- 检查 SkyWalking 服务是否正常运行
- 检查 agent 配置是否正确
- 检查日志配置是否正确 