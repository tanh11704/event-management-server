## 1. Nguyên tắc cốt lõi & Tiêu chuẩn Code

**Ưu tiên hàng đầu là tuân thủ các nguyên tắc SOLID, DRY và KISS.**

- **SOLID Principles:**

  - **S - Single Responsibility Principle:** Mỗi class (đặc biệt là `Service`, `Controller`, `Repository`) chỉ nên có một trách nhiệm duy nhất. Ví dụ: `UserService` chỉ quản lý logic liên quan đến người dùng, không xử lý logic cho sản phẩm.
  - **O - Open/Closed Principle:** Luôn code hướng về interface thay vì implementation. Sử dụng `@Service`, `@Repository` trên các class implementation. Điều này cho phép mở rộng mà không cần sửa đổi code hiện có.
  - **L - Liskov Substitution Principle:** Các class con có thể thay thế class cha của chúng mà không làm thay đổi tính đúng đắn của chương trình.
  - **I - Interface Segregation Principle:** Tạo các interface nhỏ, chuyên biệt thay vì một interface lớn. Ví dụ: Tách `UserReaderRepository` và `UserWriterRepository` nếu cần thiết.
  - **D - Dependency Inversion Principle:** Luôn inject các dependency thông qua interface (sử dụng `@Autowired` trên constructor). Spring sẽ quản lý việc khởi tạo implementation cụ thể.

- **DRY (Don't Repeat Yourself):** Tránh lặp lại code. Nếu một đoạn logic được sử dụng ở nhiều nơi, hãy tái cấu trúc nó thành một phương thức private, một utility class hoặc một service chung.

- **Immutability and `final` keyword:**

  - Ưu tiên sử dụng các đối tượng bất biến (immutable), đặc biệt là DTOs. Sử dụng `record` của Java hoặc `@Value` của Lombok.
  - Luôn sử dụng từ khóa `final` cho các dependency được inject qua constructor và các biến không cần gán lại giá trị. Điều này giúp đảm bảo thread-safety và tính ổn định.

- **Exception Handling:**

  - Sử dụng các exception cụ thể thay vì `Exception` chung.
  - Sử dụng `@ControllerAdvice` và `@ExceptionHandler` để xử lý exception một cách tập trung và trả về các response lỗi nhất quán.

- **Null Safety:**
  - Sử dụng `Optional<T>` cho các phương thức có thể trả về `null` (đặc biệt trong Repository) để tránh `NullPointerException`.
  - Sử dụng các annotation như `@NonNull` và `@Nullable` để làm rõ ý định.

---

## 2. Cấu trúc dự án (Project Structure)

Dự án tuân theo cấu trúc package theo chức năng (package-by-feature) hoặc theo lớp (package-by-layer). Cấu trúc theo lớp phổ biến như sau:

- `API_BoPhieu.controller`: Chứa các REST Controller, xử lý các HTTP request.
- `API_BoPhieu.service`: Chứa business logic. Các service được inject vào controller.
- `API_BoPhieu.repository`: Chứa các Spring Data JPA repository interfaces.
- `API_BoPhieu.model` (hoặc `domain`, `entity`): Chứa các JPA Entity.
- `API_BoPhieu.dto`: Chứa các Data Transfer Objects (DTOs) để trao đổi dữ liệu với client. **Tuyệt đối không trả về JPA Entity trực tiếp từ API.**
- `API_BoPhieu.config`: Chứa các class cấu hình (`@Configuration`).
- `API_BoPhieu.exception`: Chứa các custom exception class.
- `API_BoPhieu.mapper`: Chứa logic chuyển đổi giữa Entity và DTO (ví dụ: sử dụng MapStruct).

---

## 3. Thư viện & Framework chính

- **Core:** Spring Boot 3.x, Java 17+
- **Web:** Spring Web (MVC)
- **Data:** Spring Data JPA, Hibernate
- **Database:** MySQL
- **Build Tool:** Maven (sử dụng `pom.xml`)
- **Utilities:** Lombok (sử dụng rộng rãi cho `@Data`, `@Builder`, `@Slf4j`, `@RequiredArgsConstructor`, etc.)
- **API Documentation:** Springdoc OpenAPI (`springdoc-openapi-starter-webmvc-ui`)

---

## 4. Hướng dẫn Build & Test

- **Build dự án:**
  ```bash
  mvn clean install
  ```
- **Chạy dự án (local):**
  ```bash
  mvn spring-boot:run
  ```
- **Quan trọng:** Luôn chạy `mvn clean install` trước khi commit để đảm bảo tất cả các bài test đều pass và dự án build thành công.

---

## 5. Thiết kế & Tài liệu API

- **Quy tắc đặt tên JSON (snake_case):**

  - **Tất cả các JSON request và response body phải tuân thủ quy tắc đặt tên `snake_case`**. Ví dụ: `user_id`, `full_name`.
  - Trong khi đó, code Java (tên thuộc tính trong Entity, DTO) vẫn tuân theo quy tắc `camelCase`. Ví dụ: `private String fullName;`.
  - Điều này được cấu hình toàn cục trong file `application.properties` thông qua Jackson:
    ```properties
    spring.jackson.property-naming-strategy=SNAKE_CASE
    ```

- **RESTful Principles:** Tuân thủ các nguyên tắc RESTful. Sử dụng đúng các HTTP methods (GET, POST, PUT, DELETE, PATCH).

- **DTOs:** Luôn sử dụng DTOs cho request body và response body. Dùng validation annotations (`@Valid`, `@NotBlank`, `@NotNull`, `@Size`, etc.) trên các DTO.

- **Versioning:** API được phiên bản hóa thông qua URL path, ví dụ: `/api/v1/users`.

- **Documentation:** Sử dụng Swagger/OpenAPI để tự động tạo tài liệu API. Viết các annotation `@Operation` và `@ApiResponse` để mô tả rõ ràng các endpoint.

---

## 6. Bảo mật 🛡️

- **Xác thực Đầu vào (Input Validation):** **Không bao giờ tin tưởng đầu vào từ người dùng.** Luôn xác thực tất cả dữ liệu đến từ client (body, params, query) trước khi xử lý.
- **Quản lý Bí mật (Secrets Management):** **Không bao giờ** lưu trữ thông tin nhạy cảm (API keys, mật khẩu database) trực tiếp trong code. Sử dụng biến môi trường hoặc các dịch vụ quản lý bí mật.

---

## 5. Xác thực với JWT & Refresh Token 🔑

Kiến trúc xác thực là **stateless**, dựa trên JWT.

- **Access Token:** Thời gian sống ngắn, dùng để truy cập tài nguyên, chứa thông tin người dùng (user claims).
- **Refresh Token:** Thời gian sống dài, chỉ dùng để cấp lại Access Token mới.
- **Luồng hoạt động:**
  1.  **Đăng nhập:** Client gửi thông tin xác thực. Server trả về cả Access Token và Refresh Token.
  2.  **Truy cập:** Client gửi Access Token trong `Authorization` header. Một **middleware/filter** sẽ xác thực token này trên mỗi request.
  3.  **Làm mới:** Khi Access Token hết hạn, client gửi Refresh Token đến một endpoint đặc biệt để nhận lại Access Token mới.

---

## 6. Caching ⚡

- **Chiến lược:** Sử dụng caching cho các hoạt động **đọc nhiều, ít thay đổi** để giảm tải cho database.
- **Vô hiệu hóa (Invalidation):** Phải có chiến lược làm mới hoặc xóa cache khi dữ liệu gốc thay đổi (ví dụ: sau khi cập nhật hoặc xóa một bản ghi).
- **Đặt tên Key:** Sử dụng quy tắc đặt tên key cho cache một cách nhất quán và rõ ràng để tránh xung đột.

---

## 7. Quy tắc xử lý Quan hệ Dữ liệu (Manual Relationship Handling)

**LƯU Ý CỰC KỲ QUAN TRỌNG:** Dự án này **KHÔNG** sử dụng các annotation quan hệ của JPA như `@OneToMany`, `@ManyToOne`, `@ManyToMany`. Mọi logic liên kết dữ liệu giữa các bảng đều được xử lý thủ công tại **Service Layer**.

- **Trong lớp Entity (`model`):**

  - Các class Entity chỉ chứa các trường khóa ngoại (foreign key) dưới dạng kiểu dữ liệu gốc (ví dụ: `private Long userId;`, `private Long categoryId;`).
  - **Tuyệt đối không** chứa các đối tượng tham chiếu trực tiếp (ví dụ: `private User user;`, `private Category category;`).

- **Trong lớp Service (`service`):**
  - Đây là nơi duy nhất thực hiện việc "join" dữ liệu.
  - Khi cần lấy một đối tượng phức tạp bao gồm dữ liệu từ các bảng khác, hãy tuân theo quy trình:
    1.  Gọi repository để lấy entity chính (ví dụ: `postRepository.findById(postId)`).
    2.  Từ entity chính, lấy ra ID của khóa ngoại (ví dụ: `post.getUserId()`).
    3.  Gọi repository tương ứng khác để lấy entity phụ thuộc (ví dụ: `userRepository.findById(userId)`).
    4.  Sử dụng một lớp Mapper để kết hợp thông tin từ entity chính và các entity phụ thuộc vào một đối tượng DTO duy nhất để trả về cho Controller.
