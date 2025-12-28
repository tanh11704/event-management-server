# Java Spring Boot - Code Convention Enterprise

> **Vai trò:** Solution Architect (SA)
> **Môi trường:** Production-ready Backend Service
> **Kiến trúc:** Layered Architecture + Domain-oriented Package

---

## 📋 Phạm vi áp dụng

- **Java:** 17+
- **Framework:** Spring Boot
- **Database:** PostgreSQL
- **Cache:** Redis
- **Authentication:** JWT
- **Mapping:** MapStruct
- **ORM:** JPA/Hibernate
- **API Style:** RESTful API
- **Repository:** Monorepo Backend Service

---

## 1. Tổng quan kiến trúc (Architecture Baseline)

### 1.1 Kiến trúc tổng thể

**Layered Architecture + Domain-oriented Package**

❌ **KHÔNG dùng** package kiểu `controller/service/repository` chung cho toàn project

✅ **Mỗi bounded context/module** tự chứa `controller–service–repo`

```
com.company.product
 ├── Application.java
 ├── common
 ├── config
 ├── security
 ├── infrastructure
 ├── modules
 │    ├── auth
 │    ├── user
 │    ├── order
 │    └── product
 └── exception
```

---

## 2. Quy ước đặt tên (Naming Convention)

### 2.1 Package

| Thành phần | Quy ước |
|------------|---------|
| Root | `com.company.product` |
| Module | `modules.<domain>` |
| Controller | `controller` |
| Service | `service` |
| Repository | `repository` |
| DTO | `dto.request`, `dto.response` |
| Mapper | `mapper` |
| Entity | `entity` |

#### ❌ Không dùng:

```
controller.user
service.user
repository.user
```

#### ✅ Đúng:

```
modules.user.controller
modules.user.service
modules.user.repository
```

### 2.2 Class

| Loại | Ví dụ |
|------|-------|
| Controller | `UserController` |
| Service | `UserService` |
| Service Impl | `UserServiceImpl` |
| Repository | `UserRepository` |
| Entity | `UserEntity` |
| DTO Request | `CreateUserRequest` |
| DTO Response | `UserResponse` |
| Mapper | `UserMapper` |

#### ❌ Tránh:

- `UserDTO`
- `UserModel`
- `UserData`

---

## 3. Controller Convention

### 3.1 Nguyên tắc

**Controller KHÔNG chứa business logic**

Controller chỉ:
- Validate request
- Call service
- Return response

❌ Không dùng `ResponseEntity` lung tung → chuẩn hóa response

### 3.2 Ví dụ

```java
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {
        return ApiResponse.success(userService.createUser(request));
    }
}
```

---

## 4. Service Layer Convention

### 4.1 Nguyên tắc

- Business logic nằm **100%** ở Service
- **Interface + Implementation**
- Transaction đặt ở Service

### 4.2 Ví dụ

```java
public interface UserService {
    UserResponse createUser(CreateUserRequest request);
}
```

```java
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        UserEntity entity = userMapper.toEntity(request);
        userRepository.save(entity);
        return userMapper.toResponse(entity);
    }
}
```

---

## 5. Entity & JPA Convention (PostgreSQL)

### 5.1 Entity

- Suffix `Entity`
- **Không dùng** Lombok `@Data`
- ID dùng `UUID`

```java
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreatedDate
    private Instant createdAt;
}
```

### 5.2 Repository

```java
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
}
```

#### ⚠️ Lưu ý:

- ❌ Không viết query logic trong Service
- ❌ Không dùng native query nếu không bắt buộc

---

## 6. DTO Convention

### 6.1 Request DTO

- Chỉ chứa field từ client
- Validate bằng `jakarta.validation`

```java
public class CreateUserRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
```

### 6.2 Response DTO

- **Không trả Entity trực tiếp**
- **Không trả password, secret**

```java
public class UserResponse {
    private UUID id;
    private String email;
}
```

---

## 7. MapStruct Convention

```java
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(CreateUserRequest request);

    UserResponse toResponse(UserEntity entity);
}
```

#### ❌ Cấm:

- Không map thủ công trong service
- Không dùng `ModelMapper` trong dự án enterprise

---

## 8. Security & JWT Convention

### 8.1 JWT Components

```
security
 ├── JwtTokenProvider
 ├── JwtAuthenticationFilter
 ├── SecurityConfig
 └── CustomUserDetailsService
```

### 8.2 Nguyên tắc

JWT chỉ chứa:
- `userId`
- `role`

**Không chứa:**
- Data nhạy cảm
- Token expire phải rõ ràng

---

## 9. Redis Convention

### 9.1 Mục đích dùng Redis

- Cache
- Token blacklist
- OTP
- Rate limit

### 9.2 Naming key

```
auth:token:blacklist:{tokenId}
user:otp:{userId}
cache:user:{id}
```

---

## 10. Exception Handling Convention

### 10.1 Custom Exception

```java
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
}
```

### 10.2 Global Handler

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Void> handleBusiness(BusinessException ex) {
        return ApiResponse.error(ex.getErrorCode());
    }
}
```

---

## 11. Response Wrapper Convention

```java
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private ErrorResponse error;
}
```

#### ❌ Cấm:

- Không trả raw object
- Không lẫn success/error format

---

## 12. Logging Convention

- Dùng `slf4j`
- **Không log password/token**

```java
log.info("Create user with email={}", email);
```

---

## 13. Configuration & Secret Convention

❌ **Không commit secret**

```yaml
spring:
  datasource:
    password: ${DB_PASSWORD}
jwt:
  secret: ${JWT_SECRET}
```

---

## 14. Test Convention

- **Unit test** cho Service
- **Integration test** cho Controller

```
UserServiceTest
UserControllerIT
```

---

## 15. Dependency khuyến nghị

```
spring-boot-starter-data-jpa
spring-boot-starter-security
spring-boot-starter-validation
spring-boot-starter-data-redis
mapstruct
jjwt
flywaydb
lombok
```

---

## 16. ⛔ Những thứ TUYỆT ĐỐI CẤM trong dự án enterprise

| # | Cấm |
|---|-----|
| ❌ | Controller xử lý logic |
| ❌ | Trả Entity ra API |
| ❌ | Hardcode secret |
| ❌ | Không version API |
| ❌ | Dùng `@Data` cho Entity |
| ❌ | Map thủ công bằng setter trong service |

---

## 📌 Tóm tắt

Đây là **chuẩn code convention enterprise** áp dụng cho team backend chuyên nghiệp. Tuân thủ nghiêm ngặt để đảm bảo:

- ✅ Dễ maintain
- ✅ Scale tốt
- ✅ Secure by default
- ✅ Testable
- ✅ Production-ready
