# Micro Service Framework

A comprehensive microservice framework that provides a solid foundation for building distributed systems.

## Features

### Service Registration and Discovery
- Consul as the service registry
- Health check support
- Configuration auto-refresh

### Configuration Center
- Apollo configuration management
- Hot configuration update
- Environment isolation

### RPC Support
- Dubbo service calls
- OpenFeign declarative calls
- Load balancing

### Database Support
- Dynamic datasource
- MyBatis Plus
- Master-slave separation

### Logging and Tracing
- ELK log collection
- SkyWalking distributed tracing
- Unified log format

### Redis Support
- Redis caching
- Redisson distributed locks
- Redis cluster support

### Message Queue
- RocketMQ integration
- Message persistence
- Transactional messages

### Other Features
- Unified exception handling
- Unified response format
- Unified parameter validation
- Unified API documentation (Knife4j)

## Technology Stack

### Core Framework
- Spring Boot 3.2.x
- Spring Cloud 2023.x
- Spring Cloud Alibaba 2022.x
- Java 17+

### Service Discovery & Configuration
- Consul for Service Registration
- Apollo for Configuration Management

### API Gateway & RPC
- Spring Cloud Gateway
- Apache Dubbo 3.x
- OpenFeign

### Database & ORM
- MySQL 8.0+
- MyBatis Plus 3.5.x

### Message Queue & Cache
- RocketMQ 5.x
- Redis 7.x (Single/Cluster)
- Redisson

### Monitoring & Tracing
- SkyWalking for Distributed Tracing
- Prometheus + Grafana for Metrics
- Spring Boot Admin for Application Monitoring
- ELK Stack for Logging

### Security
- Spring Security + OAuth2
- JWT
- Distributed Session Management

### Development Tools
- MyBatis Plus Code Generator
- Knife4j (Swagger) for API Documentation
- JUnit 5 + Mockito for Testing
- Testcontainers for Integration Testing

## Detailed Module Documentation

### Common Module (micro-service-common)
The common module provides fundamental utilities and base classes:

1. Unified Response Format:
```java
// Success response
Result.success(data);
Result.success();  // No data

// Error response
Result.error("Error message");
Result.error(ResultCode.VALIDATE_FAILED);
Result.error(ResultCode.BUSINESS_ERROR, "Custom error message");
```

2. Exception Handling:
```java
// Business exception
throw new BusinessException("Error message");
throw new BusinessException(ResultCode.VALIDATE_FAILED);
throw new BusinessException(ResultCode.BUSINESS_ERROR, "Custom error message");

// Global exception handler automatically handles:
// - BusinessException
// - ValidationException
// - MethodArgumentNotValidException
// - BindException
// - Other exceptions
```

3. Base Entity:
```java
@Data
@EqualsAndHashCode(callSuper = true)
public class YourEntity extends BaseEntity {
    // Your fields
    // Automatically includes:
    // - id (Long)
    // - createTime (LocalDateTime)
    // - createBy (String)
    // - updateTime (LocalDateTime)
    // - updateBy (String)
    // - deleted (Integer)
    // - remark (String)
}
```

### Discovery Module (micro-service-discovery)
Service registration and discovery with Consul:

1. Configuration:
```yaml
spring:
  cloud:
    consul:
      host: localhost
      port: 8500
      discovery:
        instance-id: ${spring.application.name}:${random.value}
        prefer-ip-address: true
        health-check-path: /actuator/health
        health-check-interval: 15s
```

2. Usage:
```java
@SpringBootApplication
@EnableDiscoveryClient
public class YourApplication {
    public static void main(String[] args) {
        SpringApplication.run(YourApplication.class, args);
    }
}
```

### Database Module (micro-service-database)
Database operations with MyBatis-Plus and dynamic datasource:

1. Configuration:
```yaml
spring:
  datasource:
    dynamic:
      primary: master
      strict: false
      datasource:
        master:
          url: jdbc:mysql://localhost:3306/master_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
          username: root
          password: root
          driver-class-name: com.mysql.cj.jdbc.Driver
        slave:
          url: jdbc:mysql://localhost:3306/slave_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
          username: root
          password: root
          driver-class-name: com.mysql.cj.jdbc.Driver

mybatis-plus:
  mapper-locations: classpath*:/mapper/**/*.xml
  type-aliases-package: com.example.your.package.entity
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.slf4j.Slf4jImpl
  global-config:
    db-config:
      id-type: auto
      logic-delete-field: deleted
      logic-delete-value: 1
      logic-not-delete-value: 0
```

2. Usage:
```java
@Service
public class YourService {
    @Autowired
    private YourMapper yourMapper;

    // Use master datasource (default)
    public void writeOperation() {
        yourMapper.insert(entity);
    }

    // Use slave datasource
    @DS("slave")
    public List<YourEntity> readOperation() {
        return yourMapper.selectList(null);
    }
}
```

### Redis Module (micro-service-redis)
Redis operations with Redisson support:

1. Configuration:
```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password: 
    database: 0
    timeout: 10s
    lettuce:
      pool:
        min-idle: 0
        max-idle: 8
        max-active: 8
        max-wait: -1ms

redisson:
  address: redis://localhost:6379
  password: 
  database: 0
  timeout: 3000
  connectionPoolSize: 64
  connectionMinimumIdleSize: 10
  subscriptionConnectionPoolSize: 50
  dnsMonitoringInterval: 5000
  threads: 16
  nettyThreads: 32
  transportMode: "NIO"
```

2. Usage:
```java
@Service
public class YourService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private RedissonClient redissonClient;

    // Redis operations
    public void redisOperation() {
        redisTemplate.opsForValue().set("key", "value");
    }

    // Distributed lock
    public void distributedLock() {
        RLock lock = redissonClient.getLock("lockKey");
        try {
            lock.lock();
            // Your critical section
        } finally {
            lock.unlock();
        }
    }
}
```

### MQ Module (micro-service-mq)
Message queue operations with RocketMQ:

1. Configuration:
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

2. Usage:
```java
// Producer
@Service
public class YourProducer {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void sendMessage() {
        rocketMQTemplate.convertAndSend("topic", "message");
    }
}

// Consumer
@Component
@RocketMQMessageListener(
    topic = "topic",
    consumerGroup = "${rocketmq.consumer.group}"
)
public class YourConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        // Process message
    }
}
```

### RPC Module (micro-service-rpc)
RPC operations with Dubbo and OpenFeign:

1. Configuration:
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

2. Usage:
```java
// Dubbo Service
@DubboService
public class YourDubboService implements YourInterface {
    @Override
    public String yourMethod() {
        return "result";
    }
}

// Dubbo Consumer
@DubboReference
private YourInterface yourService;

// OpenFeign Client
@FeignClient(name = "service-name")
public interface YourFeignClient {
    @GetMapping("/api/endpoint")
    Result<YourDTO> yourMethod();
}
```

### Logging Module (micro-service-logging)
Logging and tracing with ELK and SkyWalking:

1. Configuration:
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

2. Usage:
```java
@Slf4j
@Service
public class YourService {
    public void yourMethod() {
        // Log with trace ID (automatically added by SkyWalking)
        log.info("Your log message");
        
        // Log with MDC
        MDC.put("key", "value");
        log.info("Log with MDC");
        MDC.clear();
    }
}
```

## Quick Start

1. Add the starter dependency to your project:

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>micro-service-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

2. Configure your application.yml:

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

3. Create your application class:

```java
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableDubbo
public class YourApplication {
    public static void main(String[] args) {
        SpringApplication.run(YourApplication.class, args);
    }
}
```

## Module Structure

- `micro-service-common`: Common utilities and base classes
- `micro-service-discovery`: Service discovery and registration
- `micro-service-database`: Database related modules
- `micro-service-redis`: Redis support
- `micro-service-mq`: Message queue support
- `micro-service-rpc`: RPC related modules
- `micro-service-logging`: Logging and tracing
- `micro-service-starter`: Starter module for quick bootstrapping
- `micro-service-test`: Example project demonstrating framework usage

## Prerequisites

- JDK 17+
- Maven 3.6+
- Consul
- MySQL 8.0+
- Redis 7.x
- RocketMQ 5.x
- ELK Stack
- SkyWalking

## Development Guidelines

1. Use the unified response format:
```java
@GetMapping("/example")
public Result<YourDTO> example() {
    return Result.success(yourData);
}
```

2. Extend the base entity for your entities:
```java
@Data
@EqualsAndHashCode(callSuper = true)
public class YourEntity extends BaseEntity {
    // Your fields
}
```

3. Use the business exception for error handling:
```java
if (condition) {
    throw new BusinessException("Error message");
}
```

4. Use dynamic datasource annotation:
```java
@DS("slave")
public List<YourEntity> queryFromSlave() {
    // Your query
}
```

5. Use Redis distributed lock:
```java
@RedisLock(key = "#key", expire = 30)
public void yourMethod(String key) {
    // Your critical section
}
```

6. Use RocketMQ message:
```java
@RocketMQMessageListener(
    topic = "your-topic",
    consumerGroup = "${rocketmq.consumer.group}"
)
public class YourConsumer implements RocketMQListener<YourMessage> {
    @Override
    public void onMessage(YourMessage message) {
        // Process message
    }
}
```

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 