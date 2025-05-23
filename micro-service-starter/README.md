# Micro Service Starter

This module provides auto-configuration for all micro-service framework components. You can enable or disable specific components based on your needs.

## Features

- Modular design with conditional auto-configuration
- Easy to use with minimal configuration
- Flexible component enabling/disabling

## Configuration

Add the following dependency to your project:

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>micro-service-starter</artifactId>
    <version>${project.version}</version>
</dependency>
```

### Component Configuration

You can enable or disable specific components using the following properties:

```yaml
micro:
  service:
    # Enable/disable MQ module (default: true)
    mq:
      enabled: true
    
    # Enable/disable Redis module (default: true)
    redis:
      enabled: true
    
    # Enable/disable Database module (default: true)
    database:
      enabled: true
    
    # Enable/disable RPC module (default: true)
    rpc:
      enabled: true
    
    # Enable/disable Service Discovery module (default: true)
    discovery:
      enabled: true
    
    # Enable/disable Logging module (default: true)
    logging:
      enabled: true
```

### Example

If you don't need MQ and Redis in your project, you can disable them:

```yaml
micro:
  service:
    mq:
      enabled: false
    redis:
      enabled: false
```

This will:
1. Prevent the MQ and Redis components from being auto-configured
2. Reduce application startup time
3. Reduce memory footprint
4. Avoid unnecessary connections to MQ and Redis servers

## Component Details

### MQ Module
- Provides RocketMQ integration
- Includes producer and consumer utilities
- Requires `micro.service.mq.enabled=true`

### Redis Module
- Provides Redis integration
- Includes Redis utility class
- Requires `micro.service.redis.enabled=true`

### Database Module
- Provides MyBatis-Plus integration
- Includes dynamic datasource support
- Requires `micro.service.database.enabled=true`

### RPC Module
- Provides Dubbo and OpenFeign integration
- Includes RPC utilities
- Requires `micro.service.rpc.enabled=true`

### Service Discovery Module
- Provides Consul integration
- Includes service registration and discovery
- Requires `micro.service.discovery.enabled=true`

### Logging Module
- Provides logging configuration
- Includes ELK and SkyWalking integration
- Requires `micro.service.logging.enabled=true`

## Best Practices

1. Only enable the components you need
2. Configure component-specific properties only when the component is enabled
3. Use the starter in combination with Spring Boot's auto-configuration
4. Monitor component usage and performance

## Troubleshooting

If you encounter issues:

1. Check if the required component is enabled
2. Verify component-specific configuration
3. Check application logs for detailed error messages
4. Ensure all required dependencies are available 