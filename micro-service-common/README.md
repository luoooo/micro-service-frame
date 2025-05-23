# Micro Service Common

Common utilities and tools for micro-service framework.

## Features

- String utilities for common string operations
- Date utilities for date/time manipulation
- Encryption utilities for security operations
- JSON utilities for data serialization/deserialization

## String Utilities

```java
// Generate UUID
String uuid = StringUtil.generateUUID();

// Validate email
boolean isValidEmail = StringUtil.isValidEmail("test@example.com");

// Validate phone number (China mobile)
boolean isValidPhone = StringUtil.isValidPhone("13800138000");

// Validate URL
boolean isValidUrl = StringUtil.isValidUrl("https://example.com");

// Convert to camel case
String camelCase = StringUtil.toCamelCase("hello_world"); // helloWorld

// Convert to snake case
String snakeCase = StringUtil.toSnakeCase("helloWorld"); // hello_world

// Mask sensitive information
String maskedEmail = StringUtil.maskEmail("test@example.com"); // t***@example.com
String maskedPhone = StringUtil.maskPhone("13800138000"); // 138****8000
```

## Date Utilities

```java
// Format date
String dateStr = DateUtil.formatDate(LocalDate.now()); // 2024-03-14

// Format datetime
String dateTimeStr = DateUtil.formatDateTime(LocalDateTime.now()); // 2024-03-14 10:30:00

// Parse date
LocalDate date = DateUtil.parseDate("2024-03-14");

// Parse datetime
LocalDateTime dateTime = DateUtil.parseDateTime("2024-03-14 10:30:00");

// Get start/end of day
LocalDateTime startOfDay = DateUtil.startOfDay(LocalDate.now());
LocalDateTime endOfDay = DateUtil.endOfDay(LocalDate.now());

// Get start/end of month
LocalDate startOfMonth = DateUtil.startOfMonth(LocalDate.now());
LocalDate endOfMonth = DateUtil.endOfMonth(LocalDate.now());

// Add time
LocalDate tomorrow = DateUtil.addDays(LocalDate.now(), 1);
LocalDate nextMonth = DateUtil.addMonths(LocalDate.now(), 1);
LocalDate nextYear = DateUtil.addYears(LocalDate.now(), 1);

// Calculate time difference
long days = DateUtil.daysBetween(startDate, endDate);
long months = DateUtil.monthsBetween(startDate, endDate);
long years = DateUtil.yearsBetween(startDate, endDate);

// Check weekend
boolean isWeekend = DateUtil.isWeekend(LocalDate.now());

// Calculate age
int age = DateUtil.getAge(birthDate);
```

## Encryption Utilities

```java
// Generate AES key
String aesKey = EncryptUtil.generateAESKey();

// AES encryption/decryption
String encrypted = EncryptUtil.aesEncrypt("sensitive data", aesKey);
String decrypted = EncryptUtil.aesDecrypt(encrypted, aesKey);

// MD5 encryption
String md5Hash = EncryptUtil.md5("password");

// SHA256 encryption
String sha256Hash = EncryptUtil.sha256("password");

// Base64 encoding/decoding
String encoded = EncryptUtil.base64Encode("text");
String decoded = EncryptUtil.base64Decode(encoded);

// Password encryption with salt
String salt = EncryptUtil.generateSalt(16);
String encryptedPassword = EncryptUtil.encryptPassword("password", salt);
boolean isMatch = EncryptUtil.verifyPassword("password", salt, encryptedPassword);
```

## JSON Utilities

```java
// Convert object to JSON
String json = JsonUtil.toJson(object);

// Convert object to pretty JSON
String prettyJson = JsonUtil.toPrettyJson(object);

// Parse JSON to object
User user = JsonUtil.fromJson(json, User.class);

// Parse JSON to complex type
List<User> users = JsonUtil.fromJson(json, new TypeReference<List<User>>() {});

// Convert object to another type
UserDTO userDTO = JsonUtil.convert(user, UserDTO.class);

// Convert object to complex type
List<UserDTO> userDTOs = JsonUtil.convert(users, new TypeReference<List<UserDTO>>() {});

// Check if string is valid JSON
boolean isValid = JsonUtil.isValidJson(json);
```

## Best Practices

1. String Operations:
   - Use `StringUtil` for common string operations instead of manual string manipulation
   - Always validate input strings before processing
   - Use appropriate masking methods for sensitive data

2. Date Operations:
   - Use `DateUtil` for all date/time operations to ensure consistency
   - Prefer `LocalDate` and `LocalDateTime` over legacy `Date` class
   - Handle timezone explicitly when needed

3. Encryption:
   - Use appropriate encryption methods based on security requirements
   - Always use salt for password encryption
   - Keep encryption keys secure
   - Use strong encryption algorithms (AES, SHA256)

4. JSON Processing:
   - Use `JsonUtil` for all JSON operations
   - Handle null values appropriately
   - Use type references for complex types
   - Validate JSON before parsing

## Dependencies

- Spring Boot Starter
- Apache Commons Lang3
- Jackson (for JSON processing)
- Lombok (for reducing boilerplate code)

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request 