# SOFTWARE REQUIREMENTS SPECIFICATION (SRS)

## HỆ THỐNG QUẢN LÝ SỰ KIỆN VÀ ĐIỂM DANH THÔNG MINH TRONG KHUÔN VIÊN TRƯỜNG ĐẠI HỌC

**Phiên bản:** 1.0  
**Ngày:** 28/12/2025  
**Trạng thái:** Draft

---

## LỊCH SỬ THAY ĐỔI

| Phiên bản | Ngày       | Người thực hiện | Mô tả thay đổi     |
| --------- | ---------- | --------------- | ------------------ |
| 1.0       | 28/12/2025 | -               | Phiên bản đầu tiên |

---

## MỤC LỤC

1. [GIỚI THIỆU](#1-giới-thiệu)
2. [MÔ TẢ TỔNG QUAN](#2-mô-tả-tổng-quan)
3. [YÊU CẦU GIAO DIỆN](#3-yêu-cầu-giao-diện)
4. [PHÂN QUYỀN & KIỂM SOÁT TRUY CẬP](#4-phân-quyền--kiểm-soát-truy-cập)
5. [YÊU CẦU CHỨC NĂNG CHI TIẾT](#5-yêu-cầu-chức-năng-chi-tiết)
6. [YÊU CẦU PHI CHỨC NĂNG](#6-yêu-cầu-phi-chức-năng)
7. [YÊU CẦU BẢO MẬT](#7-yêu-cầu-bảo-mật)
8. [YÊU CẦU DỮ LIỆU](#8-yêu-cầu-dữ-liệu)
9. [YÊU CẦU TÍCH HỢP](#9-yêu-cầu-tích-hợp)
10. [PHỤ LỤC](#10-phụ-lục)

---

## 1. GIỚI THIỆU

### 1.1. Mục đích tài liệu

Tài liệu Đặc tả Yêu cầu Phần mềm (Software Requirements Specification - SRS) này mô tả đầy đủ, chi tiết và nhất quán các yêu cầu chức năng và phi chức năng của **Hệ thống Quản lý Sự kiện và Điểm danh Thông minh trong Khuôn viên Trường Đại học** (sau đây gọi là "Hệ thống" hoặc "SEMS - Smart Event Management System").

Tài liệu này nhằm mục đích:

- Cung cấp cơ sở để thỏa thuận giữa các bên liên quan về những gì hệ thống sẽ làm
- Giảm thiểu nỗ lực phát triển bằng cách xác định rõ ràng yêu cầu từ đầu
- Cung cấp cơ sở để ước tính chi phí và lịch trình dự án
- Cung cấp baseline để validation và verification
- Tạo điểm tham chiếu cho bảo trì và nâng cấp trong tương lai

### 1.2. Đối tượng sử dụng tài liệu

- **Khách hàng/Chủ đầu tư:** Ban lãnh đạo trường, Phòng CTSV, Phòng Đào tạo
- **Nhà phát triển:** Team phát triển phần mềm, kiến trúc sư hệ thống
- **Nhà thử nghiệm:** Team QA/QC
- **Người bảo trì:** Team vận hành và bảo trì
- **Người quản lý dự án:** Project Manager, Product Owner

### 1.3. Phạm vi hệ thống

#### 1.3.1. Mục tiêu hệ thống

SEMS là một giải pháp tích hợp toàn diện nhằm:

1. **Số hóa quy trình quản lý sự kiện:** Thay thế các quy trình thủ công, giấy tờ bằng hệ thống tự động
2. **Tối ưu hóa điểm danh:** Sử dụng công nghệ AI nhận diện khuôn mặt để giảm thời gian và sai sót
3. **Nâng cao trải nghiệm người dùng:** Cung cấp giao diện thân thiện trên cả Web và Mobile
4. **Hỗ trợ ra quyết định:** Cung cấp báo cáo và phân tích dữ liệu thời gian thực
5. **Tăng cường tương tác:** Sử dụng Chatbot AI để hỗ trợ 24/7

#### 1.3.2. Phạm vi chức năng

Hệ thống bao gồm các module chính:

**Module 1: Quản lý Người dùng & Phân quyền**

- Quản lý tài khoản đa cấp (Admin, CTSV/Đào tạo, BTC, Sinh viên, Lãnh đạo)
- Phân quyền hai lớp: System Role (cố định) và Event Role (động)
- Quản lý profile và thông tin cá nhân

**Module 2: Quản lý Sự kiện**

- Tạo, chỉnh sửa, xóa sự kiện với workflow đầy đủ
- Phân loại sự kiện: Bắt buộc/Tự chọn, Công khai/Nội bộ
- Quản lý thời gian, địa điểm, dung lượng
- AI hỗ trợ viết mô tả sự kiện

**Module 3: Đăng ký & Quản lý Tham gia**

- Đăng ký tự nguyện cho sự kiện tự chọn
- Chỉ định bắt buộc cho sự kiện mandatory (theo khoa/lớp/khóa/danh sách)
- Quản lý danh sách chờ (waitlist)
- Xác nhận tham gia

**Module 4: Điểm danh Thông minh**

- Nhận diện khuôn mặt bằng AI (Face Recognition)
- Check-in/Check-out tự động
- Xử lý trường hợp đặc biệt (vào muộn, ra sớm)
- Điểm danh thủ công (fallback)
- Chỉnh sửa điểm danh với audit trail

**Module 5: Quản lý Giám khảo/Khách mời**

- Khai báo thông tin giám khảo/khách mời
- Phân bổ vị trí ngồi
- Check-in giám khảo
- Quản lý lịch trình và tài liệu riêng

**Module 6: Quản lý Tài liệu Sự kiện**

- Upload, lưu trữ tài liệu đa định dạng (PDF, PPT, DOCX, hình ảnh, video link)
- Phân quyền truy cập (Public/Registered/Private)
- Theo dõi lượt xem và tải xuống
- Version control

**Module 7: Chatbot AI**

- Chatbot cho sinh viên: tra cứu sự kiện, hướng dẫn check-in, FAQ
- Chatbot cho BTC: hỗ trợ viết nội dung, trả lời dựa trên tài liệu
- Tích hợp NLP và RAG (Retrieval-Augmented Generation)

**Module 8: Báo cáo & Thống kê**

- Dashboard tổng quan
- Báo cáo theo sự kiện, khoa, lớp, sinh viên
- Phân tích xu hướng tham gia
- Xuất báo cáo đa định dạng (Excel, PDF, CSV)

**Module 9: Thông báo & Nhắc nhở**

- Gửi thông báo qua nhiều kênh (Email, SMS, Push notification, In-app)
- Nhắc nhở tự động trước sự kiện
- Thông báo thay đổi lịch trình

#### 1.3.3. Phạm vi ngoài hệ thống

Các chức năng sau KHÔNG nằm trong phạm vi của phiên bản hiện tại:

- Quản lý tài chính và ngân sách sự kiện
- Hệ thống bán vé/thu phí
- Quản lý catering và logistics chi tiết
- Tích hợp mạng xã hội (Facebook, Instagram) để streaming
- Hệ thống live streaming sự kiện
- Gamification và reward system
- Mobile app cho BTC (chỉ web admin cho BTC)

### 1.4. Thuật ngữ và Viết tắt

| Thuật ngữ/Viết tắt | Ý nghĩa                        | Giải thích                                           |
| ------------------ | ------------------------------ | ---------------------------------------------------- |
| SEMS               | Smart Event Management System  | Tên gọi của hệ thống                                 |
| SV                 | Sinh viên                      | Người học đại học                                    |
| BTC                | Ban tổ chức                    | Nhóm người tổ chức sự kiện                           |
| CTSV               | Công tác Sinh viên             | Phòng quản lý công tác sinh viên                     |
| AI                 | Artificial Intelligence        | Trí tuệ nhân tạo                                     |
| NLP                | Natural Language Processing    | Xử lý ngôn ngữ tự nhiên                              |
| RAG                | Retrieval-Augmented Generation | Kỹ thuật AI kết hợp tìm kiếm và sinh văn bản         |
| RBAC               | Role-Based Access Control      | Kiểm soát truy cập dựa trên vai trò                  |
| System Role        | Vai trò hệ thống               | Vai trò cố định trong hệ thống (Admin, CTSV, SV...)  |
| Event Role         | Vai trò sự kiện                | Vai trò động theo từng sự kiện (Owner, Organizer...) |
| Check-in           | Điểm danh vào                  | Xác nhận có mặt tại sự kiện                          |
| Check-out          | Điểm danh ra                   | Xác nhận rời khỏi sự kiện                            |
| Mandatory Event    | Sự kiện bắt buộc               | Sự kiện sinh viên phải tham gia                      |
| Optional Event     | Sự kiện tự chọn                | Sự kiện sinh viên có thể đăng ký tự nguyện           |
| Face Vector        | Vector đặc trưng khuôn mặt     | Dữ liệu số biểu diễn khuôn mặt để nhận diện          |
| Audit Trail        | Nhật ký kiểm toán              | Lịch sử thay đổi dữ liệu                             |
| Fallback           | Phương án dự phòng             | Giải pháp thay thế khi phương án chính thất bại      |

### 1.5. Tài liệu tham khảo

- IEEE Std 830-1998: IEEE Recommended Practice for Software Requirements Specifications
- ISO/IEC 25010:2011: Systems and software Quality Requirements and Evaluation (SQuaRE)
- GDPR (General Data Protection Regulation): Quy định bảo vệ dữ liệu cá nhân
- Nghị định 13/2023/NĐ-CP về Bảo vệ dữ liệu cá nhân (Việt Nam)
- NIST Special Publication 800-63B: Digital Identity Guidelines

### 1.6. Tổng quan tài liệu

Tài liệu này được tổ chức thành 10 phần chính:

- **Phần 1-2:** Giới thiệu và mô tả tổng quan về hệ thống, người dùng, môi trường
- **Phần 3-4:** Yêu cầu giao diện và phân quyền chi tiết
- **Phần 5:** Yêu cầu chức năng chi tiết từng module với use case, flow và acceptance criteria
- **Phần 6-7:** Yêu cầu phi chức năng và bảo mật
- **Phần 8-9:** Yêu cầu về dữ liệu và tích hợp
- **Phần 10:** Phụ lục với các thông tin bổ sung

---

## 2. MÔ TẢ TỔNG QUAN

### 2.1. Tầm nhìn sản phẩm

SEMS hướng tới trở thành **nền tảng trung tâm** cho mọi hoạt động sự kiện trong khuôn viên trường đại học, giúp:

- **Sinh viên:** Dễ dàng khám phá, đăng ký và tham gia sự kiện; tích lũy điểm rèn luyện
- **Ban tổ chức:** Tiết kiệm thời gian và công sức trong việc tổ chức, quản lý và báo cáo
- **Nhà trường:** Có cái nhìn tổng quan về các hoạt động ngoại khóa, đánh giá hiệu quả

### 2.2. Nhóm người dùng

#### 2.2.1. System Admin

**Vai trò:** Quản trị viên hệ thống

**Số lượng:** 1-3 người

**Đặc điểm:**

- Có kiến thức kỹ thuật về hệ thống
- Làm việc toàn thời gian với hệ thống
- Chịu trách nhiệm về hoạt động ổn định của hệ thống

**Nhiệm vụ chính:**

- Quản lý tài khoản người dùng (tạo, khóa, reset password)
- Cấu hình hệ thống (tham số, template, quy tắc)
- Quản lý dữ liệu master (khoa, lớp, khóa học)
- Giám sát hiệu năng và bảo mật
- Backup và restore dữ liệu
- Xử lý sự cố kỹ thuật

**Kỹ năng công nghệ:** Cao

#### 2.2.2. Phòng CTSV/Đào tạo

**Vai trò:** Quản lý cấp cao các sự kiện

**Số lượng:** 5-10 người

**Đặc điểm:**

- Cán bộ phòng ban chuyên trách
- Có quyền phê duyệt sự kiện
- Giám sát tổng thể các hoạt động

**Nhiệm vụ chính:**

- Tạo và phê duyệt sự kiện cấp trường
- Phân công ban tổ chức
- Quản lý sự kiện bắt buộc
- Xem báo cáo tổng hợp
- Đánh giá hiệu quả sự kiện

**Kỹ năng công nghệ:** Trung bình

#### 2.2.3. Ban tổ chức sự kiện (BTC)

**Vai trò:** Người tổ chức và vận hành sự kiện cụ thể

**Số lượng:** 50-100 người (cho toàn trường)

**Đặc điểm:**

- Có thể là sinh viên, giảng viên, hoặc cán bộ
- Có thể tham gia nhiều sự kiện khác nhau với vai trò khác nhau
- Làm việc part-time với hệ thống

**Phân loại chi tiết:**

**a) Event Owner (Chủ sự kiện)**

- Quyền cao nhất đối với sự kiện
- Phê duyệt thay đổi quan trọng
- Phân quyền cho các thành viên khác

**b) Event Organizer (Điều phối viên)**

- Quản lý nội dung, lịch trình sự kiện
- Quản lý đăng ký và điểm danh
- Upload tài liệu
- Gửi thông báo

**c) Event Staff (Nhân viên hỗ trợ)**

- Hỗ trợ check-in tại sự kiện
- Xử lý các tình huống phát sinh
- Không có quyền chỉnh sửa thông tin sự kiện

**Nhiệm vụ chung:**

- Tạo và quản lý nội dung sự kiện
- Quản lý đăng ký và danh sách tham gia
- Điểm danh và check-in
- Quản lý tài liệu
- Gửi thông báo và nhắc nhở
- Xem báo cáo của sự kiện mình quản lý

**Kỹ năng công nghệ:** Trung bình đến thấp

#### 2.2.4. Sinh viên

**Vai trò:** Người tham gia sự kiện

**Số lượng:** ~6,000 sinh viên

**Đặc điểm:**

- Độ tuổi 18-25
- Quen thuộc với smartphone
- Sử dụng app mobile chủ yếu
- Đa dạng về khoa, khóa, lớp

**Phân loại:**

- Sinh viên năm 1-2: Quan tâm sự kiện định hướng, kỹ năng mềm
- Sinh viên năm 3-4: Quan tâm sự kiện chuyên môn, việc làm
- Cán bộ lớp/đoàn: Vừa là SV, vừa có thể là BTC

**Nhiệm vụ chính:**

- Khám phá sự kiện quan tâm
- Đăng ký/hủy đăng ký sự kiện tự chọn
- Xem lịch sự kiện cá nhân
- Check-in bằng nhận diện khuôn mặt
- Tải tài liệu sự kiện
- Xem lịch sử tham gia và điểm rèn luyện
- Sử dụng chatbot để hỏi đáp

**Kỹ năng công nghệ:** Cao (với mobile app)

#### 2.2.5. Giám khảo/Khách mời

**Vai trò:** Người tham gia đặc biệt tại sự kiện

**Số lượng:** 10-50 người/sự kiện

**Đặc điểm:**

- Có thể là giảng viên, chuyên gia bên ngoài, lãnh đạo
- Không phải tài khoản thường xuyên
- Cần quy trình đơn giản

**Nhiệm vụ chính:**

- Nhận thông tin sự kiện
- Check-in tại sự kiện
- Truy cập tài liệu được cấp quyền

**Kỹ năng công nghệ:** Trung bình

#### 2.2.6. Ban lãnh đạo

**Vai trò:** Người xem báo cáo tổng hợp

**Số lượng:** 5-10 người

**Đặc điểm:**

- Lãnh đạo cấp cao (Hiệu trưởng, Phó Hiệu trưởng, Trưởng phòng)
- Quan tâm số liệu tổng quan
- Ít khi truy cập hệ thống

**Nhiệm vụ chính:**

- Xem dashboard tổng quan
- Xem báo cáo định kỳ
- Export báo cáo

**Kỹ năng công nghệ:** Thấp đến trung bình

### 2.3. Môi trường vận hành

#### 2.3.1. Nền tảng Web Admin

**Đối tượng sử dụng:** Admin, CTSV/Đào tạo, BTC, Lãnh đạo

**Yêu cầu:**

- **Browser hỗ trợ:**
  - Chrome/Edge (khuyến nghị): Phiên bản 2 năm gần nhất
  - Firefox: Phiên bản 2 năm gần nhất
  - Safari: Phiên bản macOS/iOS mới nhất
- **Độ phân giải màn hình:** Tối thiểu 1366x768, khuyến nghị 1920x1080
- **Kết nối Internet:** Tốc độ tối thiểu 5 Mbps
- **JavaScript:** Bắt buộc phải bật
- **Cookies:** Bắt buộc phải cho phép

**Tính năng đặc biệt:**

- Responsive design (hỗ trợ tablet)
- Progressive Web App (PWA) - có thể cài đặt như app
- Offline mode hạn chế (cache dữ liệu cơ bản)

#### 2.3.2. Mobile App (iOS & Android)

**Đối tượng sử dụng:** Sinh viên, Giám khảo/Khách mời

**Yêu cầu Android:**

- OS: Android 8.0 (API level 26) trở lên
- RAM: Tối thiểu 2GB
- Storage: 100MB trống
- Camera: Có, độ phân giải tối thiểu 5MP
- Quyền cần thiết: Camera, Location (optional), Notification

**Yêu cầu iOS:**

- OS: iOS 13.0 trở lên
- Thiết bị: iPhone 6s trở lên
- Storage: 100MB trống
- Camera: Có

**Tính năng đặc biệt:**

- Face ID/Touch ID/Biometric authentication
- Push notification
- QR code scanner (fallback cho face recognition)
- Offline mode: Xem lịch sự kiện, tài liệu đã tải

#### 2.3.3. Hạ tầng Server

**Kiến trúc:** Microservices trên Cloud/Hybrid

**Các thành phần chính:**

**1. Web Server**

- Nginx/Apache
- Load balancer
- SSL/TLS encryption

**2. Application Server**

- Backend API: Node.js/Python/Java
- Container: Docker + Kubernetes
- Autoscaling

**3. Database**

- Primary DB: PostgreSQL (quan hệ)
- Cache: Redis
- File Storage: S3-compatible object storage

**4. AI Services**

- Face Recognition Engine: OpenCV + Deep Learning model
- NLP Engine: cho Chatbot (GPT-based hoặc tương tự)
- Model Serving: TensorFlow Serving hoặc TorchServe

**5. Supporting Services**

- Message Queue: RabbitMQ/Kafka
- Email Service: SMTP/SendGrid
- SMS Gateway: Twilio/Esendex
- Push Notification: Firebase Cloud Messaging

**Yêu cầu hạ tầng:**

- CPU: 16 cores (có thể scale)
- RAM: 32GB (có thể scale)
- Storage: 500GB SSD (có thể mở rộng)
- Network: 1Gbps
- Backup: Daily full + incremental

#### 2.3.4. Thiết bị Điểm danh

**Tại cổng check-in:**

**Option 1: Camera Station (Khuyến nghị)**

- Camera IP: Độ phân giải Full HD (1920x1080) trở lên
- Frame rate: 30 FPS
- Low light performance: Tốt (sự kiện tối)
- Kết nối: Ethernet/WiFi
- Xử lý: Edge computing device (Jetson Nano, Raspberry Pi 4 hoặc tương đương)
- Màn hình: 15-24 inch touchscreen để hiển thị kết quả

**Option 2: Tablet Station**

- Tablet Android/iOS với camera trước chất lượng cao
- Giá đỡ cố định
- Kết nối WiFi ổn định

**Option 3: Smartphone Staff**

- Smartphone của staff BTC
- App hỗ trợ check-in thủ công + face recognition
- Fallback khi camera chính gặp sự cố

**Số lượng thiết bị:**

- Sự kiện nhỏ (<100 người): 1 điểm check-in
- Sự kiện trung bình (100-300 người): 2-3 điểm check-in
- Sự kiện lớn (>300 người): 4-6 điểm check-in

### 2.4. Giả định và Phụ thuộc

#### 2.4.1. Giả định (Assumptions)

1. **Về Người dùng:**

   - Sinh viên có smartphone với camera hoạt động tốt
   - Người dùng có kiến thức cơ bản về sử dụng web/mobile app
   - Email sinh viên được quản lý bởi trường và có tính chính thống

2. **Về Hạ tầng:**

   - Trường có hệ thống WiFi phủ sóng tốt trong khuôn viên
   - Có nguồn điện ổn định cho các thiết bị check-in
   - Có không gian đủ để đặt thiết bị điểm danh tại các sự kiện

3. **Về Dữ liệu:**

   - Dữ liệu sinh viên (mã SV, tên, khoa, lớp) đã được số hóa
   - Có thể truy cập dữ liệu này thông qua API hoặc import định kỳ
   - Sinh viên đồng ý cung cấp ảnh khuôn mặt cho mục đích điểm danh

4. **Về Quy trình:**
   - Có quy trình rõ ràng về phê duyệt sự kiện
   - Có chính sách về điểm rèn luyện và sự kiện bắt buộc
   - Có team IT hỗ trợ vận hành hệ thống

#### 2.4.2. Phụ thuộc (Dependencies)

1. **Dịch vụ bên ngoài:**

   - Email service (SMTP server hoặc third-party như SendGrid)
   - SMS gateway (nếu có)
   - Cloud storage (S3 hoặc tương đương)
   - AI/ML APIs (nếu không tự host)

2. **Hệ thống hiện có:**

   - Hệ thống quản lý sinh viên (Student Information System - SIS)
   - Hệ thống SSO/LDAP (nếu có)
   - Hệ thống tính điểm rèn luyện (nếu có)

3. **Pháp lý:**

   - Tuân thủ Nghị định 13/2023/NĐ-CP về Bảo vệ dữ liệu cá nhân
   - Có văn bản đồng ý của sinh viên về thu thập dữ liệu sinh trắc học

4. **Công nghệ:**
   - Thư viện Face Recognition mã nguồn mở hoặc có license
   - Framework/Platform đã chọn (React, Flutter, etc.)
   - Database và các dependencies

### 2.5. Ràng buộc

#### 2.5.1. Ràng buộc về Quy mô

- **Số lượng người dùng:** ~6,000 sinh viên + 500 cán bộ/giảng viên
- **Số sự kiện:** Ước tính 200-300 sự kiện/năm học
- **Concurrent users:** Peak 500-1000 người (khi đăng ký sự kiện hot)
- **Check-in volume:** ≥1,000 lượt/ngày tại peak

#### 2.5.2. Ràng buộc về Ngân sách

- **Ngân sách triển khai:** Trung bình (chi tiết thương lượng)
- **Chi phí vận hành:** Cần tính toán cloud cost, license, bảo trì
- **ROI:** Dự kiến thu hồi vốn sau 2-3 năm bằng tiết kiệm nhân lực

#### 2.5.3. Ràng buộc về Thời gian

- **Timeline phát triển:** 6-9 tháng cho MVP
- **Deployment:** Trước năm học mới (tháng 8-9)
- **Training:** 1 tháng trước go-live

#### 2.5.4. Ràng buộc về Kỹ thuật

- **Technology Stack:** Cần thống nhất với IT team hiện tại
- **Security:** Phải tuân thủ quy định bảo vệ dữ liệu cá nhân
- **Performance:** Thời gian nhận diện < 1 giây/người
- **Availability:** 99.5% uptime (cho phép downtime bảo trì)

#### 2.5.5. Ràng buộc về Pháp lý và Quy định

- **PDPA (Personal Data Protection Act):** Tuân thủ Nghị định 13/2023
- **Quy chế sinh viên:** Tuân thủ quy chế của Bộ GD&ĐT và của trường
- **Quyền riêng tư:** Không được sử dụng dữ liệu khuôn mặt ngoài mục đích điểm danh
- **Lưu trữ dữ liệu:** Chỉ lưu vector đặc trưng, không lưu ảnh gốc

---

## 3. YÊU CẦU GIAO DIỆN

### 3.1. Giao diện Người dùng (User Interface)

#### 3.1.1. Nguyên tắc Thiết kế

**1. Nhất quán (Consistency)**

- Sử dụng design system thống nhất (màu sắc, typography, component)
- Icons và terminology giống nhau trên toàn hệ thống
- Behavior pattern nhất quán (navigation, feedback, error handling)

**2. Đơn giản (Simplicity)**

- Interface sạch sẽ, không cluttered
- Ưu tiên các tác vụ chính, ẩn các tính năng nâng cao
- Progressive disclosure: Hiển thị thông tin theo mức độ cần thiết

**3. Phản hồi (Feedback)**

- Mọi action của người dùng đều có feedback ngay lập tức
- Loading state cho các tác vụ dài
- Success/error messages rõ ràng

**4. Khả năng sử dụng (Usability)**

- Dễ học, dễ nhớ
- Minimize số lượng click/tap
- Shortcuts cho power users

**5. Accessibility**

- Contrast ratio đạt chuẩn WCAG 2.1 AA
- Keyboard navigation
- Screen reader friendly
- Font size có thể điều chỉnh

#### 3.1.2. Layout và Navigation

**A. Web Admin Layout**

```
┌─────────────────────────────────────────────────────────┐
│ Header: Logo | Navigation Menu | User Profile | Logout  │
├───────────┬─────────────────────────────────────────────┤
│           │                                             │
│ Sidebar   │          Main Content Area                  │
│ Menu      │                                             │
│           │  ┌───────────────────────────────────────┐  │
│ • Dashboard│  │                                       │  │
│ • Events  │  │        Page Content                   │  │
│ • Users   │  │                                       │  │
│ • Reports │  │                                       │  │
│ • ...     │  └───────────────────────────────────────┘  │
│           │                                             │
│           │  Pagination / Load more                     │
├───────────┴─────────────────────────────────────────────┤
│ Footer: Copyright | Support | Version                   │
└─────────────────────────────────────────────────────────┘
```

**B. Mobile App Layout**

```
┌─────────────────────────┐
│   Header Bar            │
│   [Menu] Title [Search] │
├─────────────────────────┤
│                         │
│   Main Content          │
│   (Scrollable)          │
│                         │
│   Cards / Lists         │
│                         │
│                         │
├─────────────────────────┤
│   Bottom Navigation     │
│ [Home][Events][QR][Me]  │
└─────────────────────────┘
```

#### 3.1.3. Color Scheme

**Primary Colors:**

- Primary: #1976D2 (Blue) - Actions, links, buttons
- Secondary: #FF9800 (Orange) - Highlights, secondary actions
- Success: #4CAF50 (Green) - Success messages, confirmed status
- Warning: #FFC107 (Amber) - Warnings, pending status
- Error: #F44336 (Red) - Errors, critical issues
- Info: #2196F3 (Light Blue) - Information, tips

**Neutral Colors:**

- Background: #FAFAFA
- Surface: #FFFFFF
- Text Primary: #212121
- Text Secondary: #757575
- Divider: #BDBDBD

#### 3.1.4. Typography

- **Font Family:**

  - Primary: 'Roboto', sans-serif (cho chữ Latin)
  - Vietnamese: 'Be Vietnam Pro', 'Roboto', sans-serif

- **Font Sizes:**
  - H1: 32px (Page titles)
  - H2: 24px (Section titles)
  - H3: 20px (Subsection titles)
  - Body: 16px (Default text)
  - Caption: 14px (Secondary text)
  - Small: 12px (Footnotes)

#### 3.1.5. Màn hình chính (Screens Overview)

**Web Admin - Các màn hình chính:**

1. **Dashboard:** Tổng quan số liệu, biểu đồ, sự kiện sắp tới
2. **Event Management:**

   - Event List: Danh sách sự kiện (filter, search, sort)
   - Event Detail: Chi tiết sự kiện
   - Event Create/Edit: Form tạo/sửa sự kiện
   - Attendance Management: Quản lý điểm danh
   - Document Management: Quản lý tài liệu

3. **User Management:**

   - User List: Danh sách người dùng
   - User Detail/Edit: Chi tiết người dùng
   - Role Assignment: Phân quyền

4. **Registration Management:**

   - Registration List: Danh sách đăng ký
   - Waitlist Management: Quản lý danh sách chờ
   - Mandatory Assignment: Chỉ định bắt buộc

5. **Reports:**

   - Report Dashboard: Dashboard báo cáo
   - Custom Reports: Tạo báo cáo tùy chỉnh
   - Export Center: Xuất báo cáo

6. **Settings:**
   - System Configuration: Cấu hình hệ thống
   - Template Management: Quản lý template
   - Master Data: Quản lý dữ liệu master

**Mobile App - Các màn hình chính:**

1. **Home:** Feed sự kiện, suggestions, banners
2. **Events:**

   - Event Discovery: Browse/search events
   - Event Detail: Chi tiết sự kiện
   - My Events: Sự kiện của tôi

3. **QR/Check-in:** Camera để check-in
4. **Profile:**

   - My Profile: Thông tin cá nhân
   - Participation History: Lịch sử tham gia
   - Documents: Tài liệu đã lưu
   - Settings: Cài đặt

5. **Notifications:** Danh sách thông báo
6. **Chatbot:** Chat với AI assistant

### 3.2. Giao diện Phần cứng (Hardware Interface)

#### 3.2.1. Camera Interface

**REQ-HW-CAM-01: Kết nối Camera**

- Hệ thống phải hỗ trợ kết nối với camera IP qua giao thức RTSP/HTTP
- Hỗ trợ USB webcam cho setup đơn giản
- API chuẩn: OpenCV VideoCapture hoặc FFmpeg

**REQ-HW-CAM-02: Frame Capture**

- Capture frame với frequency: 5-10 FPS (tiết kiệm tài nguyên)
- Tự động điều chỉnh exposure và white balance
- Crop và resize về kích thước chuẩn (640x480 hoặc tương tự)

**REQ-HW-CAM-03: Xử lý Edge**

- Tiền xử lý trên edge device (face detection)
- Chỉ gửi cropped face region về server để recognition
- Giảm băng thông và tăng tốc độ

#### 3.2.2. Printer Interface (Optional)

**REQ-HW-PRT-01: In Badge**

- Hỗ trợ in badge/thẻ tham gia sự kiện (nếu cần)
- Kết nối qua USB hoặc Network printer
- Format: PDF với QR code và thông tin người tham gia

### 3.3. Giao diện Phần mềm (Software Interface)

#### 3.3.1. API Backend

**REQ-SW-API-01: RESTful API**

- Tuân thủ chuẩn REST
- Authentication: JWT (JSON Web Token)
- Response format: JSON
- API versioning: /api/v1/...

**REQ-SW-API-02: Rate Limiting**

- Giới hạn số request/phút để tránh abuse
- Trả về HTTP 429 khi vượt giới hạn

**REQ-SW-API-03: Error Handling**

- HTTP status codes chuẩn
- Error response format:

```json
{
  "error": {
    "code": "ERR_CODE",
    "message": "Human readable message",
    "details": {...}
  }
}
```

#### 3.3.2. Database Interface

**REQ-SW-DB-01: ORM/Query Builder**

- Sử dụng ORM (Sequelize, TypeORM, SQLAlchemy...) cho maintainability
- Hỗ trợ migration và seeding

**REQ-SW-DB-02: Connection Pool**

- Quản lý connection pool để tối ưu performance
- Auto-reconnect khi mất kết nối

#### 3.3.3. External Services

**REQ-SW-EXT-01: Email Service**

- SMTP hoặc API-based (SendGrid, Mailgun)
- Template engine cho email
- Tracking: sent, opened, clicked (nếu có)

**REQ-SW-EXT-02: SMS Service**

- API-based (Twilio, Nexmo, hoặc local provider)
- SMS queue để xử lý batch

**REQ-SW-EXT-03: Push Notification**

- Firebase Cloud Messaging (FCM) cho Android
- Apple Push Notification Service (APNS) cho iOS

**REQ-SW-EXT-04: Storage Service**

- S3-compatible object storage
- Public/private bucket
- Signed URL cho download bảo mật

**REQ-SW-EXT-05: AI/ML Services**

- Face Recognition API (tự host hoặc third-party)
- NLP/LLM API cho Chatbot
- Model versioning và AB testing support

### 3.4. Giao diện Truyền thông (Communication Interface)

#### 3.4.1. Network Protocols

**REQ-COM-NET-01: HTTP/HTTPS**

- Tất cả traffic qua HTTPS (TLS 1.2 trở lên)
- HTTP/2 support cho performance

**REQ-COM-NET-02: WebSocket**

- Real-time updates cho check-in status
- Notification push
- Chatbot real-time response

**REQ-COM-NET-03: MQTT (Optional)**

- Cho IoT devices nếu có
- Lightweight protocol cho camera/sensors

#### 3.4.2. Data Format

**REQ-COM-FMT-01: JSON**

- Default format cho API request/response
- UTF-8 encoding

**REQ-COM-FMT-02: Multipart Form**

- Cho file upload (images, documents)
- Max file size: 50MB/file

**REQ-COM-FMT-03: CSV/Excel**

- Cho import/export dữ liệu bulk
- UTF-8 with BOM cho tiếng Việt

#### 3.4.3. Authentication & Authorization

**REQ-COM-AUTH-01: JWT Token**

- Access token: Short-lived (15-30 phút)
- Refresh token: Long-lived (7-30 ngày)
- Token trong header: `Authorization: Bearer <token>`

**REQ-COM-AUTH-02: OAuth 2.0 (Future)**

- Cho tích hợp SSO với hệ thống trường

---

## 4. PHÂN QUYỀN & KIỂM SOÁT TRUY CẬP

### 4.1. Tổng quan Hệ thống Phân quyền

Hệ thống SEMS sử dụng mô hình phân quyền **hai lớp** (Two-tier RBAC) để đảm bảo tính linh hoạt và bảo mật:

1. **System Role (Vai trò Hệ thống):** Vai trò cố định, gắn với tài khoản người dùng, quyết định quyền truy cập tổng thể vào hệ thống
2. **Event Role (Vai trò Sự kiện):** Vai trò động, gắn với từng sự kiện cụ thể, quyết định quyền hạn của người dùng trong bối cảnh sự kiện đó

### 4.2. System Role (Phân quyền Hệ thống)

#### FR-AUTH-01: Hỗ trợ Phân quyền Hai lớp

**Mô tả:** Hệ thống PHẢI hỗ trợ đồng thời cả System Role và Event Role

**Acceptance Criteria:**

- Mỗi user account có đúng 1 System Role
- Mỗi user có thể có 0 hoặc nhiều Event Role trên các sự kiện khác nhau
- Quyền thực tế = System Role ∪ Event Role (union của cả hai)

#### 4.2.1. System Admin

**Mã vai trò:** `SYSTEM_ADMIN`

**Mô tả:** Quản trị viên hệ thống, có toàn quyền

**Quyền hạn:**

| Chức năng              | Quyền     |
| ---------------------- | --------- |
| **Quản lý Người dùng** |           |
| Tạo/Sửa/Xóa tài khoản  | ✅ Tất cả |
| Phân System Role       | ✅        |
| Reset password         | ✅        |
| Khóa/Mở khóa tài khoản | ✅        |
| **Quản lý Sự kiện**    |           |
| Xem tất cả sự kiện     | ✅        |
| Tạo/Sửa/Xóa sự kiện    | ✅        |
| Phân Event Role        | ✅        |
| **Quản lý Hệ thống**   |           |
| Cấu hình hệ thống      | ✅        |
| Quản lý master data    | ✅        |
| Xem system logs        | ✅        |
| Backup/Restore         | ✅        |
| **Báo cáo**            |           |
| Xem tất cả báo cáo     | ✅        |

**Ràng buộc:**

- Số lượng System Admin nên giới hạn (1-3 người)
- Cần approval của Admin hiện tại để tạo Admin mới
- Audit log cho mọi action của Admin

#### 4.2.2. CTSV/Đào tạo

**Mã vai trò:** `CTSV_STAFF` hoặc `TRAINING_STAFF`

**Mô tả:** Cán bộ phòng CTSV hoặc Đào tạo, quản lý sự kiện cấp trường

**Quyền hạn:**

| Chức năng                      | Quyền                     |
| ------------------------------ | ------------------------- |
| **Quản lý Người dùng**         |                           |
| Xem danh sách sinh viên/BTC    | ✅                        |
| Tạo/Sửa tài khoản BTC          | ✅ (Có approval workflow) |
| **Quản lý Sự kiện**            |                           |
| Xem tất cả sự kiện             | ✅                        |
| Tạo sự kiện                    | ✅                        |
| Sửa/Xóa sự kiện của phòng mình | ✅                        |
| Phê duyệt sự kiện              | ✅                        |
| Phân Event Role                | ✅                        |
| Quản lý sự kiện bắt buộc       | ✅                        |
| Chỉ định danh sách SV bắt buộc | ✅                        |
| **Quản lý Đăng ký**            |                           |
| Xem đăng ký tất cả sự kiện     | ✅                        |
| Xác nhận/Hủy đăng ký           | ✅ (Với lý do)            |
| **Điểm danh**                  |                           |
| Xem dữ liệu điểm danh          | ✅                        |
| Chỉnh sửa điểm danh            | ✅ (Với lý do)            |
| **Báo cáo**                    |                           |
| Xem báo cáo tổng hợp           | ✅                        |
| Xuất báo cáo                   | ✅                        |

**Ràng buộc:**

- Chỉ quản lý sự kiện thuộc phạm vi phòng ban mình
- Các thay đổi quan trọng cần approval

#### 4.2.3. Sinh viên

**Mã vai trò:** `STUDENT`

**Mô tả:** Sinh viên, người tham gia sự kiện

**Quyền hạn:**

| Chức năng                       | Quyền                                   |
| ------------------------------- | --------------------------------------- |
| **Profile**                     |                                         |
| Xem profile cá nhân             | ✅                                      |
| Cập nhật thông tin cá nhân      | ✅ (Giới hạn)                           |
| Upload ảnh cho face recognition | ✅                                      |
| **Sự kiện**                     |                                         |
| Xem sự kiện công khai           | ✅                                      |
| Đăng ký sự kiện tự chọn         | ✅                                      |
| Hủy đăng ký (trước deadline)    | ✅ (Không áp dụng cho sự kiện bắt buộc) |
| Xem sự kiện của mình            | ✅                                      |
| **Check-in**                    |                                         |
| Check-in bằng face recognition  | ✅                                      |
| Check-in bằng QR code           | ✅ (Fallback)                           |
| **Tài liệu**                    |                                         |
| Xem/Tải tài liệu được phép      | ✅                                      |
| **Chatbot**                     |                                         |
| Sử dụng chatbot                 | ✅                                      |
| **Lịch sử**                     |                                         |
| Xem lịch sử tham gia            | ✅                                      |
| Xem điểm rèn luyện              | ✅                                      |

**Ràng buộc:**

- Không được truy cập dữ liệu của sinh viên khác
- Không được chỉnh sửa điểm danh của mình

#### 4.2.4. Lãnh đạo

**Mã vai trò:** `LEADER`

**Mô tả:** Ban lãnh đạo nhà trường, chỉ xem báo cáo

**Quyền hạn:**

| Chức năng               | Quyền          |
| ----------------------- | -------------- |
| **Dashboard**           |                |
| Xem dashboard tổng quan | ✅             |
| **Báo cáo**             |                |
| Xem báo cáo tổng hợp    | ✅             |
| Xuất báo cáo PDF/Excel  | ✅             |
| **Sự kiện**             |                |
| Xem danh sách sự kiện   | ✅ (Read-only) |

**Ràng buộc:**

- Chỉ có quyền xem, không có quyền sửa/xóa
- Dashboard được customize riêng

### 4.3. Event Role (Phân quyền Sự kiện)

#### FR-AUTH-02: Đa Event Role

**Mô tả:** Một người dùng CÓ THỂ có nhiều Event Role trên các sự kiện khác nhau

**Ví dụ:**

- User A là Event Owner của "Sự kiện Hội thảo AI"
- User A là Event Organizer của "Ngày hội việc làm"
- User A là Event Staff của "Olympic Toán học"

**Acceptance Criteria:**

- Event Role độc lập với System Role
- Có thể gán/thu hồi Event Role động
- Lịch sử thay đổi Event Role được log

#### 4.3.1. Event Owner (Chủ sự kiện)

**Mã vai trò:** `EVENT_OWNER`

**Mô tả:** Người chịu trách nhiệm chính cho sự kiện

**Quyền hạn đối với sự kiện:**

| Chức năng                      | Quyền             |
| ------------------------------ | ----------------- |
| Xem thông tin sự kiện          | ✅                |
| Sửa thông tin sự kiện          | ✅ (Toàn bộ)      |
| Xóa/Hủy sự kiện                | ✅ (Với xác nhận) |
| Phân Event Role cho người khác | ✅                |
| Quản lý đăng ký                | ✅                |
| Chỉ định SV bắt buộc           | ✅                |
| Quản lý điểm danh              | ✅                |
| Chỉnh sửa điểm danh            | ✅                |
| Quản lý tài liệu               | ✅                |
| Quản lý giám khảo/khách mời    | ✅                |
| Gửi thông báo                  | ✅                |
| Xem báo cáo sự kiện            | ✅                |
| Xuất báo cáo                   | ✅                |

**Ai có thể là Event Owner?**

- Cán bộ CTSV/Đào tạo (khi tạo sự kiện)
- Giảng viên được chỉ định
- Cán bộ lớp/khoa (cho sự kiện của khoa/lớp)

#### 4.3.2. Event Organizer (Điều phối viên)

**Mã vai trò:** `EVENT_ORGANIZER`

**Mô tả:** Người hỗ trợ tổ chức, không có quyền cao nhất

**Quyền hạn đối với sự kiện:**

| Chức năng             | Quyền                                              |
| --------------------- | -------------------------------------------------- |
| Xem thông tin sự kiện | ✅                                                 |
| Sửa mô tả, lịch trình | ✅ (Giới hạn, không sửa được thông tin quan trọng) |
| Xóa/Hủy sự kiện       | ❌                                                 |
| Quản lý đăng ký       | ✅                                                 |
| Quản lý điểm danh     | ✅                                                 |
| Chỉnh sửa điểm danh   | ✅ (Với log)                                       |
| Upload tài liệu       | ✅                                                 |
| Sửa/Xóa tài liệu      | ✅ (Do mình upload)                                |
| Gửi thông báo         | ✅ (Cần approval nếu gửi toàn bộ)                  |
| Xem báo cáo sự kiện   | ✅                                                 |

#### 4.3.3. Event Staff (Nhân viên hỗ trợ)

**Mã vai trò:** `EVENT_STAFF`

**Mô tả:** Người hỗ trợ tại sự kiện, chủ yếu là check-in

**Quyền hạn đối với sự kiện:**

| Chức năng                         | Quyền                        |
| --------------------------------- | ---------------------------- |
| Xem thông tin sự kiện             | ✅ (Chỉ thông tin cần thiết) |
| Xem danh sách tham gia            | ✅                           |
| Check-in thủ công                 | ✅                           |
| Xem trạng thái check-in real-time | ✅                           |
| Sửa thông tin sự kiện             | ❌                           |
| Chỉnh sửa điểm danh               | ❌                           |

#### 4.3.4. Judge/Guest (Giám khảo/Khách mời)

**Mã vai trò:** `JUDGE` hoặc `GUEST`

**Mô tả:** Người tham gia với vai trò đặc biệt

**Quyền hạn đối với sự kiện:**

| Chức năng                       | Quyền             |
| ------------------------------- | ----------------- |
| Xem thông tin sự kiện           | ✅                |
| Xem tài liệu dành cho giám khảo | ✅                |
| Download tài liệu               | ✅                |
| Check-in                        | ✅                |
| Chấm điểm (nếu là cuộc thi)     | ✅ (Future scope) |

### 4.4. Ma trận Phân quyền Chi tiết

#### 4.4.1. Ma trận System Role

| Chức năng            | Admin | CTSV/ĐT       | SV              | Lãnh đạo |
| -------------------- | ----- | ------------- | --------------- | -------- |
| **User Management**  |
| Create User          | ✅    | ✅ (BTC only) | ❌              | ❌       |
| Edit User            | ✅    | ✅ (Limited)  | ✅ (Self only)  | ❌       |
| Delete User          | ✅    | ❌            | ❌              | ❌       |
| Assign System Role   | ✅    | ❌            | ❌              | ❌       |
| **Event Management** |
| View All Events      | ✅    | ✅            | ✅ (Public)     | ✅       |
| Create Event         | ✅    | ✅            | ❌              | ❌       |
| Edit Event           | ✅    | ✅ (Own)      | ❌              | ❌       |
| Delete Event         | ✅    | ✅ (Own)      | ❌              | ❌       |
| Approve Event        | ✅    | ✅            | ❌              | ❌       |
| **Registration**     |
| Register for Event   | N/A   | N/A           | ✅              | N/A      |
| Cancel Registration  | N/A   | N/A           | ✅              | N/A      |
| Manage Registrations | ✅    | ✅            | ❌              | ❌       |
| **Attendance**       |
| Check-in (Self)      | N/A   | N/A           | ✅              | N/A      |
| View Attendance      | ✅    | ✅            | ✅ (Self)       | ✅       |
| Edit Attendance      | ✅    | ✅            | ❌              | ❌       |
| **Documents**        |
| Upload Documents     | ✅    | ✅            | ❌              | ❌       |
| View Documents       | ✅    | ✅            | ✅ (If allowed) | ✅       |
| **Reports**          |
| View All Reports     | ✅    | ✅            | ❌              | ✅       |
| Export Reports       | ✅    | ✅            | ❌              | ✅       |
| **System Config**    |
| System Settings      | ✅    | ❌            | ❌              | ❌       |
| Master Data          | ✅    | ✅ (Limited)  | ❌              | ❌       |

#### 4.4.2. Ma trận Event Role

| Chức năng            | Event Owner | Event Organizer    | Event Staff | Judge/Guest |
| -------------------- | ----------- | ------------------ | ----------- | ----------- |
| Edit Event Info      | ✅ (Full)   | ✅ (Limited)       | ❌          | ❌          |
| Delete Event         | ✅          | ❌                 | ❌          | ❌          |
| Assign Event Roles   | ✅          | ❌                 | ❌          | ❌          |
| Manage Registrations | ✅          | ✅                 | ❌          | ❌          |
| Manual Check-in      | ✅          | ✅                 | ✅          | ❌          |
| Edit Attendance      | ✅          | ✅ (With log)      | ❌          | ❌          |
| Manage Documents     | ✅          | ✅ (Own docs)      | ❌          | ❌          |
| View Reports         | ✅          | ✅                 | ❌          | ❌          |
| Send Notifications   | ✅          | ✅ (With approval) | ❌          | ❌          |

### 4.5. Authentication & Session Management

#### FR-AUTH-03: Xác thực Đa yếu tố (Optional cho Admin)

**Mô tả:** Hệ thống NÊN hỗ trợ xác thực đa yếu tố (MFA) cho tài khoản Admin

**Phương thức:**

- TOTP (Time-based One-Time Password) qua app như Google Authenticator
- SMS OTP (nếu có SMS gateway)

#### FR-AUTH-04: Quản lý Session

**Mô tả:** Hệ thống PHẢI quản lý session an toàn

**Requirements:**

- Session timeout: 30 phút không hoạt động (web), 7 ngày (mobile với refresh token)
- Logout từ tất cả thiết bị
- Giới hạn số session đồng thời: 3 sessions/user
- Revoke token khi đổi password

#### FR-AUTH-05: Password Policy

**Mô tả:** Hệ thống PHẢI thực thi chính sách mật khẩu mạnh

**Requirements:**

- Độ dài tối thiểu: 8 ký tự
- Bao gồm: Chữ hoa, chữ thường, số, ký tự đặc biệt
- Không được trùng 3 password gần nhất
- Đổi password bắt buộc sau 90 ngày (cho Admin)
- Lock account sau 5 lần đăng nhập sai

---

## 5. YÊU CẦU CHỨC NĂNG CHI TIẾT

### 5.1. Module Quản lý Người dùng

#### FR-USER-00: Quản lý Cấu trúc Tổ chức

**Mô tả:** Hệ thống PHẢI cho phép quản lý cấu trúc tổ chức của trường (Khoa, Phòng ban, Lớp)

**Use Case UC-USER-00.1: Quản lý Khoa**

**Actor:** System Admin

**Main Flow:**

1. Admin vào "Quản lý tổ chức" → "Khoa"
2. Hệ thống hiển thị danh sách các khoa hiện có
3. Admin có thể:
   - **Thêm khoa mới:** Nhập mã khoa, tên, tên tiếng Anh, thông tin liên hệ
   - **Sửa thông tin khoa:** Cập nhật thông tin, trưởng khoa, contact
   - **Vô hiệu hóa khoa:** Đánh dấu khoa không còn hoạt động (không xóa - giữ lại historical data)
   - **Xem thống kê:** Số lớp, số sinh viên, số sự kiện của khoa

**Use Case UC-USER-00.2: Quản lý Phòng ban**

Similar to UC-USER-00.1, áp dụng cho các phòng ban hành chính

**Use Case UC-USER-00.3: Quản lý Lớp**

**Actor:** CTSV/Đào tạo Staff, System Admin

**Main Flow:**

1. Actor vào "Quản lý lớp"
2. Filter theo khoa, khóa
3. Actor có thể:
   - **Thêm lớp mới:**
     - Chọn khoa
     - Nhập mã lớp (auto-suggest format: 24SE1)
     - Nhập tên lớp
     - Chọn năm nhập học
     - Nhập chuyên ngành
     - Chọn GVCN (giảng viên chủ nhiệm)
   - **Sửa thông tin lớp**
   - **Import sinh viên hàng loạt vào lớp:** Upload Excel với mã SV, tên
   - **Chuyển sinh viên giữa các lớp:** Trong trường hợp đặc biệt
   - **Xem danh sách sinh viên:** Của lớp

**Acceptance Criteria:**

- [ ] CRUD operations cho Faculties, Departments, Classes
- [ ] Soft delete (giữ historical data)
- [ ] Validation: Mã khoa/lớp unique
- [ ] Import sinh viên bulk vào lớp
- [ ] Statistics và reporting

#### FR-USER-01: Tạo, Chỉnh sửa, Vô hiệu hóa Tài khoản

**Mô tả:** Hệ thống cho phép quản lý vòng đời tài khoản người dùng

**Use Case UC-USER-01: Tạo Tài khoản Mới**

**Actor:** System Admin, CTSV/Đào tạo (cho BTC)

**Precondition:**

- Actor đã đăng nhập với quyền tương ứng
- Có thông tin cần thiết về người dùng mới

**Main Flow:**

1. Actor chọn "Tạo người dùng mới"
2. Hệ thống hiển thị form tạo tài khoản
3. Actor chọn loại tài khoản:
   - **Sinh viên**
   - **Cán bộ/Giảng viên**
   - **Admin**

**3a. Nếu chọn Sinh viên:**

- Email (bắt buộc, format: @student.school.edu.vn)
- Họ tên (bắt buộc)
- Mã sinh viên (bắt buộc, unique, format: 10 chữ số)
- Ngày sinh
- Giới tính
- Số điện thoại
- **Chọn lớp** (dropdown, group by khoa và năm):
  ```
  Khoa CNTT:
    K22:
      ├─ 22SE1 - Lớp Kỹ thuật phần mềm 1 (45/50)
      ├─ 22SE2 - Lớp Kỹ thuật phần mềm 2 (43/50)
      └─ 22CS1 - Lớp Khoa học máy tính 1 (40/45)
    K23:
      ├─ 23SE1 - Lớp Kỹ thuật phần mềm 1 (50/55)
      └─ ...
  Khoa KTMT-DT:
    K22:
      ├─ 22CE1 - Lớp Kỹ thuật máy tính 1 (35/40)
      └─ ...
  ```
- Khi chọn lớp → Khoa và Năm nhập học tự động được xác định

**3b. Nếu chọn Cán bộ/Giảng viên:**

- Email (bắt buộc, format: @school.edu.vn)
- Họ tên (bắt buộc)
- Mã cán bộ (bắt buộc, unique)
- Ngày sinh
- Giới tính
- Số điện thoại
- **Thuộc về:**
  - ☐ Giảng viên khoa → Chọn khoa (Khoa CNTT, KTMT-DT...)
  - ☐ Cán bộ phòng ban → Chọn phòng ban (CTSV, Đào tạo...)
- Chức vụ (Giảng viên, Chuyên viên, Trưởng khoa, Trưởng phòng...)
- **System Role:**
  - CTSV_STAFF (nếu thuộc phòng CTSV)
  - TRAINING_STAFF (nếu thuộc phòng Đào tạo)
  - LEADER (nếu là lãnh đạo)

**3c. Nếu chọn Admin:**

- Email
- Họ tên
- Mã admin
- System Role = SYSTEM_ADMIN

4. Hệ thống validate dữ liệu:
   - Email format đúng và unique
   - Mã SV/CB đúng format và unique
   - Lớp được chọn (nếu là SV)
   - Khoa hoặc Phòng ban được chọn (nếu là cán bộ/GV)
5. Actor xác nhận tạo
6. Hệ thống:
   - Tạo tài khoản với password tạm
   - Gửi email kích hoạt với link đặt password
   - Hiển thị thông báo thành công
7. Use case kết thúc

**Alternative Flow:**

**3a. Import hàng loạt sinh viên từ Excel**

1. Actor chọn "Import sinh viên từ Excel"
2. Actor chọn **lớp đích** (bắt buộc chọn trước khi import)
   - Ví dụ: Chọn "22SE1 - Lớp Kỹ thuật phần mềm 1"
3. Hệ thống hiển thị template Excel mẫu với columns:
   ```
   | Mã SV      | Họ tên         | Email                           | Ngày sinh  | Giới tính | SĐT        |
   |------------|----------------|---------------------------------|------------|-----------|------------|
   | 2251050001 | Nguyễn Văn A   | 2251050001@student.school.edu.vn| 15/01/2004 | Nam       | 0901234567 |
   | 2251050002 | Trần Thị B     | 2251050002@student.school.edu.vn| 20/03/2004 | Nữ        | 0901234568 |
   ```
4. Actor download template, điền dữ liệu hoặc paste từ nguồn khác
5. Actor upload file Excel
6. Hệ thống validate từng dòng:
   - **Validation rules:**
     - Mã SV: 10 chữ số, unique trong DB
     - Email: Format đúng, unique trong DB
     - Ngày sinh: Format DD/MM/YYYY, hợp lý (18-30 tuổi)
     - Giới tính: Nam/Nữ/Khác
     - SĐT: 10 chữ số, bắt đầu bằng 0
   - **Auto-fill:**
     - class_id: Tự động điền từ lớp đã chọn
     - faculty_id: Tự động từ lớp
     - admission_year: Tự động từ lớp
     - system_role: Tự động = 'STUDENT'
     - status: Tự động = 'Active'
7. Hệ thống hiển thị kết quả validation:

   ```
   ✓ Hợp lệ: 45/50 dòng
   ❌ Lỗi: 5 dòng

   Chi tiết lỗi:
   • Dòng 3: Mã SV "2251050003" đã tồn tại
   • Dòng 15: Email format không đúng
   • Dòng 23: Ngày sinh không hợp lệ
   • Dòng 34: Mã SV phải là 10 chữ số
   • Dòng 47: Dòng trống
   ```

8. Hệ thống tạo file Excel đánh dấu lỗi (với cột "Status" và "Error Message")
9. Actor có thể:
   - Download file lỗi để sửa
   - Chọn "Import các dòng hợp lệ" (45 dòng)
   - Cancel và upload lại
10. Nếu actor chọn import dòng hợp lệ:
    - Hệ thống tạo 45 tài khoản với password tạm
    - Gửi email kích hoạt cho 45 sinh viên
    - Cập nhật student_count của lớp
    - Hiển thị progress bar
11. Hiển thị summary:

    ```
    ✅ Đã tạo thành công 45 tài khoản cho lớp 22SE1
    📧 Email kích hoạt đã được gửi
    📊 Lớp 22SE1 hiện có 45/50 sinh viên

    [Download báo cáo chi tiết] [Đóng]
    ```

12. Use case kết thúc

**Alternative Flow 3b: Import nhiều lớp cùng lúc**

1. Actor chọn "Import đa lớp"
2. Template Excel có thêm cột "Mã lớp":
   ```
   | Mã SV      | Họ tên       | Mã lớp | Email                     | ... |
   |------------|--------------|--------|---------------------------|-----|
   | 2251050001 | Nguyễn Văn A | 22SE1  | 2251050001@student....    | ... |
   | 2251050002 | Trần Thị B   | 22SE1  | 2251050002@student....    | ... |
   | 2251050003 | Lê Văn C     | 22SE2  | 2251050003@student....    | ... |
   ```
3. Hệ thống validate cột "Mã lớp" xem có tồn tại không
4. Group students theo lớp và import

**Exception Flow:**

**4a. Dữ liệu không hợp lệ**

1. Hệ thống hiển thị thông báo lỗi cụ thể
2. Actor sửa lại
3. Quay lại bước 4

**4b. Email/Mã đã tồn tại**

1. Hệ thống thông báo "Email/Mã đã được sử dụng"
2. Hệ thống suggest tìm kiếm user hiện tại
3. Actor nhập thông tin khác hoặc hủy

**Postcondition:**

- Tài khoản mới được tạo với trạng thái "Chờ kích hoạt"
- Email kích hoạt được gửi
- Log được ghi nhận

**Acceptance Criteria:**

- [ ] Form validation đầy đủ (required fields, format)
- [ ] Email unique check
- [ ] Mã SV/CB unique check
- [ ] Password tạm theo policy
- [ ] Email gửi thành công trong vòng 1 phút
- [ ] Import Excel hỗ trợ tối thiểu 1000 dòng
- [ ] Hiển thị progress bar khi import
- [ ] Log đầy đủ thông tin: Who, When, What

**Use Case UC-USER-02: Chỉnh sửa Tài khoản**

**Actor:** System Admin, CTSV/Đào tạo (limited), Chính user đó (self-edit)

**Main Flow:**

1. Actor tìm kiếm user cần sửa
2. Hệ thống hiển thị thông tin hiện tại
3. Actor chỉnh sửa các trường được phép
4. Hệ thống validate
5. Actor xác nhận
6. Hệ thống lưu thay đổi và log
7. Nếu thay đổi quan trọng (role, email), gửi email thông báo cho user

**Editable Fields by Role:**

| Field        | Admin | CTSV/ĐT | Self            |
| ------------ | ----- | ------- | --------------- |
| Họ tên       | ✅    | ✅      | ✅              |
| Email        | ✅    | ❌      | ❌ (Cần verify) |
| Số ĐT        | ✅    | ✅      | ✅              |
| Khoa/Lớp     | ✅    | ✅      | ❌              |
| System Role  | ✅    | ❌      | ❌              |
| Ảnh đại diện | ✅    | ✅      | ✅              |
| Bio          | ✅    | ✅      | ✅              |

**Use Case UC-USER-03: Vô hiệu hóa/Khôi phục Tài khoản**

**Actor:** System Admin

**Main Flow (Vô hiệu hóa):**

1. Admin tìm user cần vô hiệu hóa
2. Admin chọn "Vô hiệu hóa tài khoản"
3. Hệ thống yêu cầu nhập lý do
4. Admin nhập lý do và xác nhận
5. Hệ thống:
   - Đánh dấu tài khoản là "Disabled"
   - Revoke tất cả session/token hiện tại
   - Gửi email thông báo cho user
   - Log action
6. Use case kết thúc

**Main Flow (Khôi phục):** Tương tự, chuyển trạng thái từ "Disabled" về "Active"

**Exception:**

- Không thể vô hiệu hóa tài khoản Admin duy nhất
- Không thể vô hiệu hóa chính mình

#### FR-USER-02: Tích hợp Hệ thống Tài khoản Sinh viên

**Mô tả:** Hệ thống cho phép mở rộng tích hợp với hệ thống quản lý sinh viên hiện tại của trường trong tương lai

**Requirements:**

**FR-USER-02.1: API Integration**

- Hệ thống PHẢI thiết kế với khả năng tích hợp API từ hệ thống SIS (Student Information System)
- Hỗ trợ sync dữ liệu: danh sách sinh viên, thông tin khoa/lớp/khóa
- Sync schedule: Real-time hoặc batch (daily/weekly)

**FR-USER-02.2: SSO Integration (Future)**

- Thiết kế sẵn cho OAuth 2.0 / SAML 2.0
- Single Sign-On với tài khoản email trường
- Auto-provision user khi đăng nhập lần đầu

**FR-USER-02.3: Data Mapping**

- Mapping giữa fields của SEMS và SIS
- Conflict resolution rules (ví dụ: Nếu tên khác nhau, lấy từ nguồn nào?)

**Acceptance Criteria:**

- [ ] Database schema có trường để lưu external_id (link với SIS)
- [ ] API endpoint sẵn sàng để nhận webhook/sync từ SIS
- [ ] Document rõ ràng về integration requirements

#### FR-USER-04: Quản lý Ảnh Khuôn mặt cho Face Recognition

**Use Case UC-USER-04: Upload Ảnh Khuôn mặt**

**Actor:** Sinh viên

**Precondition:**

- SV đã đăng nhập
- Chưa có ảnh khuôn mặt hoặc muốn cập nhật

**Main Flow:**

1. SV vào "Profile" → "Quản lý khuôn mặt"
2. Hệ thống hiển thị:
   - Hướng dẫn chụp ảnh (ánh sáng tốt, nhìn thẳng, không đeo khẩu trang/kính râm...)
   - Nút "Chụp ảnh mới" hoặc "Upload ảnh"
3. SV chọn một trong hai option:
   - **Option A: Chụp bằng camera**
     - Hệ thống mở camera
     - Hiển thị khung hình oval để SV đặt khuôn mặt vào
     - Tự động detect face
     - Chụp 3-5 ảnh liên tiếp
   - **Option B: Upload ảnh từ thư viện**
     - SV chọn ảnh từ thư viện
     - Hệ thống crop face từ ảnh
4. Hệ thống xử lý:
   - Face detection: Xác định có khuôn mặt không
   - Face quality check: Kiểm tra chất lượng (độ nét, ánh sáng, góc nghiêng)
   - Liveness detection (optional): Đảm bảo không phải ảnh của ảnh
   - Extract face embedding (vector 128/512 chiều)
5. Hệ thống hiển thị preview và chất lượng ảnh
6. SV xác nhận
7. Hệ thống:
   - Lưu face vector vào database (KHÔNG lưu ảnh gốc)
   - Xóa ảnh gốc sau khi extract vector
   - Update trạng thái "Đã có dữ liệu khuôn mặt"
   - Log action
8. Hiển thị thông báo thành công
9. Use case kết thúc

**Alternative Flow:**

**4a. Không phát hiện khuôn mặt**

1. Hệ thống thông báo "Không phát hiện khuôn mặt trong ảnh"
2. Yêu cầu chụp/upload lại

**4b. Chất lượng ảnh kém**

1. Hệ thống thông báo cụ thể vấn đề: "Ảnh quá tối", "Khuôn mặt bị che", "Góc nghiêng quá nhiều"
2. Yêu cầu chụp/upload lại

**4c. Detect nhiều khuôn mặt**

1. Hệ thống thông báo "Phát hiện nhiều khuôn mặt, vui lòng chụp ảnh chỉ có một người"
2. Yêu cầu chụp/upload lại

**Postcondition:**

- Face vector được lưu trong database
- SV có thể sử dụng face recognition để check-in

**Acceptance Criteria:**

- [ ] Support cả camera và upload ảnh
- [ ] Face detection accuracy ≥ 95%
- [ ] Face quality check: brightness, sharpness, pose
- [ ] Extract face vector trong < 2 giây
- [ ] Chỉ lưu vector, không lưu ảnh gốc (tuân thủ privacy)
- [ ] Có thể update/xóa vector bất kỳ lúc nào
- [ ] UI/UX thân thiện với hướng dẫn rõ ràng

---

### 5.2. Module Quản lý Sự kiện

#### FR-EVT-01: Tạo, Chỉnh sửa, Hủy Sự kiện

**Use Case UC-EVT-01: Tạo Sự kiện Mới**

**Actor:** CTSV/Đào tạo, Event Owner (được phân quyền)

**Precondition:**

- Actor đã đăng nhập với quyền tương ứng

**Main Flow:**

1. Actor chọn "Tạo sự kiện mới"
2. Hệ thống hiển thị wizard tạo sự kiện (multi-step)

**Step 1: Thông tin cơ bản**

- Tên sự kiện (bắt buộc)
- Loại sự kiện (dropdown): Hội thảo, Seminar, Workshop, Thi đấu, Văn nghệ, Khác
- Đối tượng: Toàn trường, Theo khoa, Theo lớp, Theo khóa
- Tags: #AI, #Career, #Sports... (để search/filter)

**Step 2: Mô tả & Nội dung**

- Mô tả ngắn (bắt buộc, 200 ký tự)
- Mô tả chi tiết (rich text editor)
- Banner image (upload hoặc chọn từ library)
- Nút "AI hỗ trợ viết mô tả" (gọi FR-AI-01)

**Step 3: Thời gian & Địa điểm**

- Ngày bắt đầu (bắt buộc)
- Ngày kết thúc (có thể cùng ngày)
- Giờ bắt đầu (bắt buộc)
- Giờ kết thúc (bắt buộc)
- Địa điểm (text hoặc chọn từ danh sách địa điểm có sẵn)
- Bản đồ/Link Google Maps (optional)

**Step 4: Cấu hình đăng ký**

- Loại sự kiện:
  - ☐ Sự kiện bắt buộc (Mandatory)
  - ☐ Sự kiện tự chọn (Optional)
- Số lượng tối đa (0 = không giới hạn)
- Thời gian mở đăng ký (từ ngày - đến ngày)
- Thời gian hủy đăng ký (trước sự kiện X giờ)
- Yêu cầu phê duyệt đăng ký: Có/Không
- Cho phép waitlist: Có/Không

**Step 5: Điểm danh**

- Phương thức điểm danh:
  - ☑ Face Recognition (mặc định)
  - ☑ QR Code (fallback)
  - ☐ Thủ công (chỉ staff)
- Thời gian cho phép check-in: Trước sự kiện X phút đến sau khi bắt đầu Y phút
- Yêu cầu check-out: Có/Không
- Tính điểm rèn luyện: [Nhập số điểm]

**Step 6: Phân quyền**

- Thêm Event Owner, Organizer, Staff (search và add)

3. Actor điền đầy đủ thông tin
4. Actor chọn "Tạo sự kiện" hoặc "Lưu nháp"
5. Hệ thống validate
6. Hệ thống:
   - Tạo sự kiện với trạng thái "Nháp" hoặc "Mở đăng ký"
   - Gửi thông báo cho các thành viên BTC được add
   - Log action
7. Hiển thị màn hình sự kiện vừa tạo
8. Use case kết thúc

**Alternative Flow:**

**4a. Lưu nháp**

- Hệ thống lưu sự kiện với trạng thái "Nháp"
- Sự kiện không hiển thị với sinh viên
- Có thể quay lại chỉnh sửa sau

**Exception Flow:**

**5a. Dữ liệu không hợp lệ**

- Hệ thống highlight field lỗi
- Hiển thị message cụ thể
- Actor sửa và submit lại

**5b. Thời gian không hợp lệ**

- Ngày kết thúc < ngày bắt đầu
- Thời gian mở đăng ký sau thời gian sự kiện
  → Hệ thống thông báo lỗi logic

**Postcondition:**

- Sự kiện mới được tạo
- BTC được notify
- Sự kiện xuất hiện trong danh sách (nếu không phải nháp)

**Acceptance Criteria:**

- [ ] Wizard UX flow mượt mà, có thể next/back
- [ ] Validate đầy đủ ở mỗi step
- [ ] Auto-save nháp mỗi 30 giây
- [ ] Rich text editor hỗ trợ: bold, italic, list, link, image
- [ ] Upload banner: max 5MB, format JPG/PNG
- [ ] Location dropdown có search/autocomplete
- [ ] Tag system với suggestion
- [ ] Permission assignment với role explanation

#### FR-EVT-02: Thông tin Sự kiện

**Mô tả:** Sự kiện phải chứa đầy đủ các thông tin cần thiết

**Data Model: Event**

```json
{
  "id": "uuid",
  "title": "string (max 200)",
  "slug": "string (unique, for URL)",
  "description_short": "string (max 500)",
  "description_full": "html/markdown",
  "event_type": "enum: Seminar, Workshop, Competition, Cultural, Other",
  "category": "string",
  "tags": ["array of strings"],
  "banner_image_url": "string",
  "thumbnail_url": "string",

  "start_datetime": "datetime",
  "end_datetime": "datetime",
  "timezone": "string (default: Asia/Ho_Chi_Minh)",

  "location": {
    "name": "string",
    "address": "string",
    "room": "string",
    "building": "string",
    "map_url": "string (Google Maps link)"
  },

  "registration": {
    "type": "enum: Mandatory, Optional",
    "max_participants": "integer (0 = unlimited)",
    "registration_start": "datetime",
    "registration_end": "datetime",
    "cancel_deadline": "datetime",
    "require_approval": "boolean",
    "allow_waitlist": "boolean",
    "waitlist_limit": "integer"
  },

  "attendance": {
    "methods": ["FaceRecognition", "QRCode", "Manual"],
    "checkin_window_before": "integer (minutes)",
    "checkin_window_after": "integer (minutes)",
    "require_checkout": "boolean",
    "training_points": "integer"
  },

  "target_audience": {
    "scope": "enum: AllStudents, ByFaculty, ByClass, ByYear, ByDepartment, Custom",
    "faculties": ["array of faculty IDs"], // Chọn theo khoa
    "classes": ["array of class IDs"], // Chọn theo lớp cụ thể
    "years": ["array of integers"], // Chọn theo khóa: [2022, 2023, 2024]
    "departments": ["array of department IDs"], // Nếu sự kiện dành cho cán bộ
    "student_list": ["array of student IDs"], // For mandatory events - danh sách cụ thể
    "exclude_list": ["array of student IDs"] // Danh sách loại trừ (nếu có)
  },

  "organizers": {
    "organizing_unit": "string (e.g., Phòng CTSV)",
    "contact_person": "string",
    "contact_email": "string",
    "contact_phone": "string"
  },

  "status": "enum: Draft, RegistrationOpen, RegistrationClosed, Ongoing, Completed, Cancelled",
  "visibility": "enum: Public, Internal, Private",

  "metadata": {
    "created_by": "user_id",
    "created_at": "datetime",
    "updated_by": "user_id",
    "updated_at": "datetime",
    "published_at": "datetime",
    "version": "integer"
  }
}
```

#### FR-EVT-03: Trạng thái Sự kiện & State Machine

**Mô tả:** Sự kiện có các trạng thái và chuyển đổi giữa các trạng thái theo quy tắc

**State Diagram:**

```
                  create
┌──────────────┐ ────────────> ┌──────────────┐
│              │               │              │
│   (None)     │               │    Draft     │
│              │               │    (Nháp)    │
└──────────────┘               └──────┬───────┘
                                      │ publish
                                      │
                                      ▼
                               ┌─────────────────────┐
                               │  Registration Open  │
                          ┌────│  (Mở đăng ký)       │────┐
                          │    └─────────┬───────────┘    │
                          │              │ auto (time)    │
                          │              │                │
                          │  edit        ▼   edit         │ cancel
                          │    ┌──────────────────────┐   │
                          └───>│  Registration Closed │<──┘
                               │  (Đóng đăng ký)      │
                               └─────────┬────────────┘
                                         │ auto/manual
                                         │ (event starts)
                                         ▼
                               ┌──────────────────────┐
                               │      Ongoing         │
                               │   (Đang diễn ra)     │
                               └─────────┬────────────┘
                                         │ auto
                                         │ (event ends)
                                         ▼
                               ┌──────────────────────┐
                               │     Completed        │
                               │    (Kết thúc)        │
                               └──────────────────────┘

                               ┌──────────────────────┐
                               │     Cancelled        │
                               │      (Hủy)           │
                               └──────────────────────┘
                                  (from any state)
```

**State Transition Rules:**

| From State             | To State            | Trigger                        | Permission Required | Actions                                         |
| ---------------------- | ------------------- | ------------------------------ | ------------------- | ----------------------------------------------- |
| None                   | Draft               | Create event                   | CTSV/Admin          | Create record                                   |
| Draft                  | Registration Open   | Publish                        | Event Owner         | Send notifications, Make visible                |
| Draft                  | Cancelled           | Cancel                         | Event Owner         | Notify BTC                                      |
| Registration Open      | Draft               | Unpublish                      | Event Owner (rare)  | Make invisible                                  |
| Registration Open      | Registration Closed | Auto (time) or Manual          | System / Owner      | Stop accepting new registrations                |
| Registration Closed    | Ongoing             | Auto (event start time)        | System              | Enable check-in                                 |
| Ongoing                | Completed           | Auto (event end time + buffer) | System              | Close check-in, Calculate attendance            |
| Any (except Completed) | Cancelled           | Cancel event                   | Owner/Admin         | Notify all participants, Refund (if applicable) |

**FR-EVT-03.1: Auto State Transition**

- Hệ thống PHẢI tự động chuyển trạng thái dựa trên thời gian
- Cron job chạy mỗi 5 phút để check
- Gửi webhook/notification khi state thay đổi

**FR-EVT-03.2: Manual State Control**

- Event Owner CÓ THỂ chuyển trạng thái thủ công (với một số hạn chế)
- Không thể chuyển ngược lại Completed → Ongoing

#### FR-AI-01: AI Hỗ trợ Viết Mô tả Sự kiện

**Use Case UC-AI-01: Sử dụng AI Hỗ trợ Viết Mô tả**

**Actor:** CTSV/Đào tạo, Event Owner

**Precondition:**

- Actor đang ở màn hình tạo/sửa sự kiện
- Đã điền một số thông tin cơ bản (tên, loại sự kiện)

**Main Flow:**

1. Actor click nút "AI hỗ trợ viết mô tả" tại field "Mô tả chi tiết"
2. Hệ thống hiển thị popup/modal:
   - Hiển thị thông tin đã điền (tên sự kiện, loại, thời gian...)
   - Cho phép nhập thêm keywords/gợi ý
   - Slider chọn độ dài: Ngắn gọn / Trung bình / Chi tiết
   - Slider chọn tone: Formal / Friendly / Exciting
3. Actor điều chỉnh tùy chọn và click "Tạo nội dung"
4. Hệ thống:
   - Gọi AI API (GPT hoặc tương tự) với prompt kỹ thuật
   - Hiển thị loading indicator
   - Nhận response từ AI
5. Hệ thống hiển thị nội dung gợi ý với 2-3 variations
6. Actor chọn một variation hoặc "Tạo lại"
7. Actor có thể edit nội dung trước khi chấp nhận
8. Actor click "Sử dụng nội dung này"
9. Hệ thống điền nội dung vào field mô tả
10. Use case kết thúc

**Alternative Flow:**

**6a. Không hài lòng với nội dung**

- Actor click "Tạo lại"
- Quay lại bước 4 với prompt hơi khác (add randomness)

**6b. Chỉnh sửa trước khi chấp nhận**

- Actor edit trực tiếp trong preview box
- Click "Sử dụng"

**Exception Flow:**

**4a. AI API timeout hoặc lỗi**

- Hiển thị thông báo lỗi thân thiện
- Offer retry hoặc skip

**Postcondition:**

- Mô tả sự kiện được điền bằng AI-generated content
- Actor có thể tiếp tục edit

**Technical Requirements:**

- AI model: GPT-4 Turbo hoặc tương đương (Claude, Gemini)
- Max tokens: 1000-1500
- Timeout: 30 seconds
- Rate limit: 10 requests/user/hour (tránh abuse)

**Prompt Template Example:**

```
You are an event description writer for a university event management system.

Event Information:
- Title: {event_title}
- Type: {event_type}
- Date: {date}
- Target audience: {audience}

Additional context: {user_input_keywords}

Write a {length} event description in {tone} tone.
The description should:
1. Hook the reader in the first sentence
2. Clearly state what the event is about
3. Mention key benefits for students
4. Include a call-to-action
5. Use Vietnamese language naturally

Output only the description, no preamble.
```

**Acceptance Criteria:**

- [ ] AI response time < 10 seconds (p95)
- [ ] Generated content phù hợp với context
- [ ] Có ít nhất 2 variations để chọn
- [ ] Có thể regenerate unlimited (trong rate limit)
- [ ] Preview box có rich text editor
- [ ] Copy-paste vào field mượt mà
- [ ] Track usage metrics: usage count, acceptance rate

---

### 5.3. Module Đăng ký Sự kiện

#### FR-REG-01: Đăng ký/Hủy Đăng ký Sự kiện Tự chọn

**Use Case UC-REG-01: Sinh viên Đăng ký Sự kiện**

**Actor:** Sinh viên

**Precondition:**

- SV đã đăng nhập
- Sự kiện ở trạng thái "Registration Open"
- Sự kiện là loại "Optional" (tự chọn)
- SV chưa đăng ký sự kiện này

**Main Flow:**

1. SV browse/search sự kiện và chọn một sự kiện quan tâm
2. Hệ thống hiển thị chi tiết sự kiện và nút "Đăng ký"
3. Hệ thống check điều kiện:
   - Còn slot không? (nếu có giới hạn)
   - Thời gian đăng ký còn không?
   - SV có thuộc đối tượng không? (nếu sự kiện có giới hạn đối tượng)
   - Có conflict với sự kiện khác đã đăng ký không?
4. Nếu OK, hiển thị nút "Đăng ký" enable
5. SV click "Đăng ký"
6. Nếu sự kiện require approval:
   - Hệ thống hiển thị form yêu cầu lý do/motivation
   - SV nhập và submit
7. Hệ thống:
   - Tạo registration record với status "Pending" (nếu require approval) hoặc "Confirmed"
   - Giảm available slot (nếu confirmed)
   - Gửi email xác nhận cho SV
   - Gửi notification cho BTC (nếu require approval)
8. Hiển thị thông báo thành công và hướng dẫn tiếp theo
9. Use case kết thúc

**Alternative Flow:**

**3a. Hết slot, nhưng cho phép waitlist**

1. Hiển thị nút "Đăng ký waitlist"
2. SV click
3. Hệ thống add SV vào waitlist với position number
4. Thông báo "Bạn ở vị trí #X trong waitlist"

**3b. Có conflict thời gian với sự kiện khác**

1. Hệ thống cảnh báo "Bạn đã đăng ký sự kiện [Tên] cùng thời gian"
2. Hỏi SV có muốn tiếp tục không?
3. Nếu có, cho phép đăng ký nhưng ghi chú conflict

**Exception Flow:**

**3a. Không đủ điều kiện**

- Hiển thị message rõ ràng tại sao không đăng ký được
- Ví dụ: "Sự kiện này chỉ dành cho sinh viên khoa CNTT"

**4a. Slot vừa hết (race condition)**

- Hiển thị "Rất tiếc, slot vừa hết. Bạn có muốn vào waitlist không?"

**Postcondition:**

- Registration record được tạo
- SV nhận email xác nhận
- Sự kiện xuất hiện trong "Sự kiện của tôi"

**Acceptance Criteria:**

- [ ] Check real-time slot availability
- [ ] Race condition handled (optimistic locking or queue)
- [ ] Email sent within 1 minute
- [ ] Conflict detection chính xác
- [ ] Waitlist auto-promotion khi có slot trống
- [ ] Clear error messages

**Use Case UC-REG-02: Sinh viên Hủy Đăng ký**

**Actor:** Sinh viên

**Precondition:**

- SV đã đăng ký sự kiện
- Chưa quá cancel deadline
- Sự kiện không phải bắt buộc với SV này

**Main Flow:**

1. SV vào "Sự kiện của tôi" và chọn sự kiện muốn hủy
2. SV click "Hủy đăng ký"
3. Hệ thống hỏi xác nhận và yêu cầu lý do (optional)
4. SV xác nhận
5. Hệ thống:
   - Update registration status → "Cancelled"
   - Tăng available slot
   - Promote người đầu tiên trong waitlist (nếu có) → gửi notification
   - Gửi email xác nhận hủy cho SV
   - Log action
6. Hiển thị thông báo thành công
7. Use case kết thúc

**Exception Flow:**

**Precondition fail: Quá cancel deadline**

- Nút "Hủy đăng ký" bị disable
- Hiển thị message: "Đã quá thời hạn hủy đăng ký. Vui lòng liên hệ BTC."
- Offer liên hệ BTC (email/chat)

**Precondition fail: Sự kiện bắt buộc**

- Nút "Hủy đăng ký" không hiển thị
- Hiển thị badge "Bắt buộc"

**Postcondition:**

- Registration cancelled
- Slot được free
- Waitlist person được notify (nếu có)

#### FR-REG-02: Kiểm soát Số lượng Đăng ký

**Requirements:**

**FR-REG-02.1: Max Participants Limit**

- Hệ thống PHẢI enforce giới hạn số lượng tham gia
- Atomic operation để tránh over-booking

**FR-REG-02.2: Real-time Availability Display**

- Hiển thị "X/Y slots còn trống" real-time
- Update qua WebSocket hoặc polling

**FR-REG-02.3: Waitlist Management**

- Nếu hết slot và allow_waitlist = true, cho phép đăng ký waitlist
- Waitlist có thể có giới hạn riêng
- Auto-promote từ waitlist khi có cancellation
- Notify waitlist person khi được promote (email + push notification + in-app)
- Waitlist person có time window (ví dụ: 24h) để confirm, nếu không sẽ skip sang người tiếp theo

**FR-REG-02.4: Overbooking Strategy (Optional)**

- Cho phép admin config overbooking ratio (ví dụ: 110% capacity)
- Dựa trên historical no-show rate

**Acceptance Criteria:**

- [ ] Không có over-booking (test với concurrent requests)
- [ ] Waitlist auto-promotion work correctly
- [ ] Notification timing < 1 minute
- [ ] Time window for confirmation enforced
- [ ] Analytics: show rate, cancellation rate

---

### 5.4. Module Sự kiện Bắt buộc

#### FR-MAND-01: Đánh dấu Sự kiện Bắt buộc

**Mô tả:** Hệ thống cho phép đánh dấu sự kiện là bắt buộc đối với một nhóm sinh viên

**Use Case UC-MAND-01: Tạo Sự kiện Bắt buộc**

**Actor:** CTSV/Đào tạo

**Main Flow:**

1. Khi tạo/sửa sự kiện, tại Step 4 (Cấu hình đăng ký)
2. Actor check vào "☑ Sự kiện bắt buộc (Mandatory)"
3. Hệ thống hiển thị thêm section "Chỉ định sinh viên bắt buộc"
4. Proceed với FR-MAND-02 để chọn sinh viên

**Technical:**

- Field `registration.type = "Mandatory"`
- Mandatory events không có max_participants (hoặc rất cao)
- Không có cancel_deadline cho SV mandatory

#### FR-MAND-02: Chỉ định Danh sách Sinh viên Bắt buộc

**Use Case UC-MAND-02: Chỉ định Sinh viên Bắt buộc**

**Actor:** CTSV/Đào tạo, Event Owner

**Main Flow:**

1. Actor vào sự kiện bắt buộc → tab "Danh sách bắt buộc"
2. Hệ thống hiển thị các options chọn sinh viên:

**Option 1: Chọn theo Khoa**

- Dropdown chọn một hoặc nhiều khoa
- Hiển thị preview số lượng SV

**Option 2: Chọn theo Lớp**

- Dropdown khoa → danh sách lớp
- Multi-select lớp
- Preview số lượng

**Option 3: Chọn theo Khóa**

- Select khóa (2020, 2021, 2022, 2023...)
- Preview số lượng

#### FR-MAND-02: Chỉ định Danh sách Sinh viên Bắt buộc

**Use Case UC-MAND-02: Chỉ định Sinh viên Bắt buộc**

**Actor:** CTSV/Đào tạo, Event Owner

**Main Flow:**

1. Actor vào sự kiện bắt buộc → tab "Danh sách bắt buộc"
2. Hệ thống hiển thị các options chọn sinh viên:

**Option 1: Chọn theo Khoa**

- Multi-select dropdown chọn một hoặc nhiều khoa:
  - ☐ Khoa Công Nghệ Thông Tin (CNTT)
  - ☐ Khoa Kỹ Thuật Máy Tính & Điện Tử (KTMT-DT)
  - ☐ Khoa Kinh Tế Số & Thương Mại Điện Tử (KTSD-TMDT)
- Khi chọn khoa, hiển thị preview:
  - "Khoa CNTT: 1,234 sinh viên"
  - "Khoa KTMT-DT: 856 sinh viên"
- Tổng: X sinh viên sẽ được chỉ định

**Option 2: Chọn theo Lớp**

- Step 1: Chọn khoa (hoặc "Tất cả các khoa")
- Step 2: Hiển thị danh sách lớp của khoa đó:
  ```
  Khoa CNTT:
  ☐ 22SE1 - Kỹ thuật phần mềm 1 (45 SV)
  ☐ 22SE2 - Kỹ thuật phần mềm 2 (43 SV)
  ☐ 23SE1 - Kỹ thuật phần mềm 1 (50 SV)
  ☐ 22CS1 - Khoa học máy tính 1 (40 SV)
  ...
  ```
- Multi-select lớp
- Preview tổng số sinh viên

**Option 3: Chọn theo Khóa (Năm nhập học)**

- Select year hoặc multi-select:
  - ☐ Khóa 2022 (K22)
  - ☐ Khóa 2023 (K23)
  - ☐ Khóa 2024 (K24)
- Có thể kết hợp với khoa:
  - "Khóa 2022 - Chỉ khoa CNTT"
  - "Khóa 2023 - Tất cả các khoa"
- Preview: "Khóa 2022: 2,145 sinh viên"

**Option 4: Import từ Excel**

- Click "Download template"
- Hệ thống generate file Excel với headers:
  ```
  | Mã sinh viên | Họ tên (optional) | Ghi chú (optional) |
  |--------------|-------------------|-------------------|
  | 2251050001   | Nguyễn Văn A      |                   |
  | 2251050002   | Trần Thị B        |                   |
  ```
- Actor điền dữ liệu hoặc paste từ nguồn khác
- Upload file Excel
- Hệ thống validate từng dòng:
  - ✓ Mã SV 2251050001: Nguyễn Văn A - Lớp 22SE1 - Khoa CNTT
  - ✗ Mã SV 2299999999: Không tìm thấy
  - ⚠ Mã SV 2251050002: Trùng lặp (đã có trong danh sách)
- Hiển thị summary:
  - Hợp lệ: 156/160 dòng
  - Lỗi: 4 dòng
- Download file lỗi để sửa
- Actor xác nhận import dòng hợp lệ

**Option 5: Chọn cá nhân (Search & Add)**

- Search box: Tìm theo mã SV, tên, lớp
- Kết quả hiển thị:
  ```
  [+] 2251050001 - Nguyễn Văn A - 22SE1 - CNTT
  [+] 2251050002 - Trần Thị B - 22SE2 - CNTT
  ```
- Click [+] để thêm từng sinh viên
- Thích hợp cho số lượng nhỏ

**Option 6: Kết hợp nhiều cách**

- Ví dụ: Chọn toàn bộ Khoa CNTT + Thêm một số lớp từ Khoa KTMT-DT + Import thêm danh sách từ Excel
- Hệ thống tự động loại trùng lặp

3. Actor xem preview danh sách cuối cùng:

```
┌─────────────────────────────────────────────────────────────┐
│ Tổng quan danh sách bắt buộc                                │
├─────────────────────────────────────────────────────────────┤
│ Tổng số sinh viên: 1,234                                    │
│                                                              │
│ Phân bố theo khoa:                                          │
│ • Khoa CNTT: 856 SV (69.4%)                                 │
│ • Khoa KTMT-DT: 234 SV (19.0%)                              │
│ • Khoa KTSD-TMDT: 144 SV (11.7%)                            │
│                                                              │
│ Phân bố theo khóa:                                          │
│ • K22: 456 SV (37.0%)                                       │
│ • K23: 523 SV (42.4%)                                       │
│ • K24: 255 SV (20.7%)                                       │
│                                                              │
│ [Xuất danh sách Excel] [Xem chi tiết] [Xác nhận]           │
└─────────────────────────────────────────────────────────────┘
```

4. Actor click "Xác nhận"
5. Hệ thống hiển thị confirmation dialog:
   - "Bạn chắc chắn muốn chỉ định 1,234 sinh viên tham gia bắt buộc?"
   - "Các sinh viên này sẽ nhận được thông báo qua email và app"
   - Checkbox: ☐ Gửi email ngay lập tức
   - Checkbox: ☑ Gửi push notification
6. Actor confirm
7. Hệ thống:
   - Tạo registration records với status = "Required"
   - Insert batch vào bảng registrations:
   ```sql
   INSERT INTO registrations (event_id, user_id, status, registration_type, registered_at)
   SELECT
     '{event_id}',
     u.id,
     'Required',
     'Mandatory',
     NOW()
   FROM users u
   WHERE u.id IN (selected_student_ids)
   AND NOT EXISTS (
     SELECT 1 FROM registrations r
     WHERE r.event_id = '{event_id}' AND r.user_id = u.id
   );
   ```
   - Queue email/notification jobs
   - Generate báo cáo: "Mandatory*Assignment_Report*{event*id}*{timestamp}.xlsx"
8. Hệ thống hiển thị kết quả:
   - "✓ Đã chỉ định thành công 1,234 sinh viên"
   - "📧 Đang gửi email thông báo..."
   - "📊 Báo cáo đã sẵn sàng để tải xuống"
9. Use case kết thúc

**Alternative Flow:**

**4a. Import Excel có nhiều lỗi**

1. Hệ thống hiển thị chi tiết:

   ```
   ❌ Lỗi validation:
   • Dòng 15: Mã SV "ABC123" không hợp lệ (phải là số)
   • Dòng 23: Mã SV "2299999999" không tồn tại
   • Dòng 45: Mã SV "2251050001" bị trùng lặp trong file
   • Dòng 67: Dòng trống hoặc thiếu dữ liệu

   ✓ Hợp lệ: 156/160 dòng
   ❌ Lỗi: 4 dòng
   ```

2. Hệ thống tạo file Excel với cột "Status" đánh dấu lỗi
3. Actor download file lỗi
4. Actor sửa và upload lại hoặc bỏ qua các dòng lỗi

**6a. Có sinh viên đã đăng ký tự nguyện**

1. Hệ thống detect conflict:

   ```
   ⚠️ Cảnh báo:
   23 sinh viên đã tự đăng ký sự kiện này trước đó:
   • 2251050001 - Nguyễn Văn A (Đã đăng ký: 15/12/2025)
   • 2251050045 - Trần Thị B (Đã đăng ký: 16/12/2025)
   • ...

   Các sinh viên này sẽ được chuyển từ "Optional" → "Mandatory"

   [Xem danh sách đầy đủ] [Tiếp tục] [Hủy]
   ```

2. Actor xác nhận "Tiếp tục"
3. Hệ thống update registration_type từ "Optional" → "Mandatory"

**7a. Một số sinh viên không có email hợp lệ**

1. Hệ thống ghi log:
   ```
   ⚠️ Không gửi được email cho 5 sinh viên:
   • 2251050123 - Email chưa xác thực
   • 2251050456 - Email không tồn tại
   ```
2. Tạo báo cáo sinh viên cần follow-up
3. Suggest BTC liên hệ thủ công

**Business Rules:**

**Duplicate Handling:**

- Nếu sinh viên đã có trong danh sách bắt buộc → Skip
- Nếu đã đăng ký tự nguyện → Update sang bắt buộc
- Log tất cả actions

**Validation Rules:**

- Mã sinh viên format: 10 chữ số, bắt đầu bằng 22/23/24/... (năm nhập học)
- Sinh viên phải ở trạng thái "Active"
- Lớp phải là lớp đang hoạt động (is_active = true)

**Performance:**

- Batch insert cho hiệu quả (1000 records/batch)
- Transaction để đảm bảo consistency
- Background job cho email/notification

**Acceptance Criteria:**

- [ ] Support tất cả 6 options chọn sinh viên
- [ ] Real-time preview số lượng khi chọn khoa/lớp/khóa
- [ ] Excel import validate đầy đủ với error report chi tiết
- [ ] Duplicate detection và resolution
- [ ] Batch operation efficient: < 5 giây cho 1000 sinh viên
- [ ] Email/notification queue hoạt động đúng
- [ ] Báo cáo assignment được generate tự động
- [ ] UI/UX mượt mà với loading states
- [ ] Transaction rollback nếu có lỗi
- [ ] Audit log đầy đủ (who, when, what, how many)

#### FR-MAND-03: Sinh viên Không được Hủy Tham gia

**Mô tả:** Sinh viên trong danh sách bắt buộc KHÔNG được phép hủy đăng ký

**Implementation:**

- UI: Nút "Hủy đăng ký" bị ẩn hoặc disable
- API: Endpoint cancel registration trả về 403 Forbidden nếu là mandatory
- Display badge "Bắt buộc" rõ ràng

**Exception:**

- BTC có thể remove SV khỏi danh sách bắt buộc (với lý do hợp lý)
- Admin có thể override

#### FR-MAND-04: Ghi nhận Vắng mặt

**Mô tả:** Sinh viên không check-in được tự động đánh dấu vắng mặt

**Use Case UC-MAND-04: Tự động Ghi nhận Vắng**

**Actor:** System (Cron job)

**Trigger:**

- Sau khi sự kiện kết thúc + buffer time (ví dụ: 1 giờ)

**Main Flow:**

1. System query tất cả mandatory events đã kết thúc trong buffer time
2. Với mỗi event:
   - Lấy danh sách SV required attend
   - Check attendance records
   - Nếu SV không có check-in record hoặc check-in late quá nhiều:
     - Mark attendance status = "Absent"
     - Trừ điểm rèn luyện (nếu có quy định)
     - Log reason: "Did not check-in"
3. Generate báo cáo SV vắng
4. Gửi email cho CTSV/Đào tạo
5. (Optional) Gửi email nhắc nhở SV vắng

**Acceptance Criteria:**

- [ ] Cron job chạy đúng schedule
- [ ] Xử lý bulk efficient
- [ ] Có grace period hợp lý
- [ ] Báo cáo đầy đủ thông tin
- [ ] Email template professional

---

### 5.5. Module Điểm danh AI

#### FR-ATT-01: Điểm danh bằng Nhận diện Khuôn mặt

**Use Case UC-ATT-01: Check-in bằng Face Recognition**

**Actor:** Sinh viên

**Precondition:**

- Sự kiện đang trong check-in window
- SV đã upload face vector
- SV đã đăng ký hoặc là mandatory participant

**Main Flow:**

1. SV đến điểm check-in (có camera station)
2. SV đứng trước camera trong khoảng cách hợp lý (0.5-1.5m)
3. Camera system:
   - Capture frame liên tục
   - Detect faces trong frame
   - Với mỗi face detected:
     - Extract face vector
     - So sánh với database vectors (cosine similarity hoặc Euclidean distance)
     - Nếu similarity > threshold (ví dụ: 0.85):
       - Tìm thấy match với SV_ID
       - Check SV_ID có trong registration list không
       - Nếu có → Proceed check-in
4. Hệ thống hiển thị trên màn hình:
   - Ảnh/avatar của SV
   - Họ tên
   - "Check-in thành công" với icon ✓
   - Thời gian check-in
5. Hệ thống:
   - Create attendance record:
     - event_id
     - student_id
     - checkin_time
     - checkin_method = "FaceRecognition"
     - status = "Present"
   - Play sound effect (beep)
   - Gửi push notification cho SV (optional)
6. SV đi vào sự kiện
7. Use case kết thúc

**Alternative Flow:**

**3a. Low confidence match (0.7 < similarity < 0.85)**

1. Hệ thống hiển thị "Xác nhận danh tính"
2. Show matched face + tên
3. Hỏi staff xác nhận: "Có phải [Tên] không?"
4. Staff xác nhận → check-in với flag "ManualConfirmed"

**3b. No match found**

1. Hiển thị "Không nhận diện được"
2. Suggest:
   - Thử lại (đứng gần hơn, tháo khẩu trang/kính...)
   - Sử dụng QR code
   - Check-in thủ công với staff

**Exception Flow:**

**3a. Nhiều faces trong frame**

- Hệ thống hiển thị "Vui lòng một người một lần"
- Highlight các faces detected

**3b. Face quality kém**

- "Ánh sáng không đủ"
- "Khuôn mặt bị che"
  → Hướng dẫn SV điều chỉnh

**3c. SV chưa đăng ký**

- Hiển thị "Bạn chưa đăng ký sự kiện này"
- Offer đăng ký on-site (nếu còn slot)

**3d. SV đã check-in rồi**

- Hiển thị "Bạn đã check-in lúc [time]"
- Không duplicate record

**Postcondition:**

- Attendance record created
- Real-time dashboard updated
- SV receive confirmation

**Technical Requirements:**

**Face Recognition Engine:**

- Model: FaceNet, ArcFace, hoặc tương đương
- Embedding size: 128 or 512 dimensions
- Matching algorithm: Cosine similarity hoặc Euclidean distance
- Threshold: Configurable (default 0.85)
- Inference time: < 500ms

**Camera Requirements:**

- Resolution: ≥ 1080p
- Frame rate: 30 FPS
- Auto-exposure, auto-focus
- Good performance in low light

**Performance:**

- Process time per person: < 1 second (from face detect to check-in complete)
- Concurrent check-ins: ≥ 5 stations simultaneously
- False Accept Rate (FAR): < 1%
- False Reject Rate (FRR): < 5%

**Acceptance Criteria:**

- [ ] Recognition accuracy ≥ 95%
- [ ] Check-in time < 1 second
- [ ] Smooth UX trên màn hình station
- [ ] Clear feedback (visual + audio)
- [ ] Graceful fallback khi fail
- [ ] Anti-spoofing: Reject printed photos (Liveness detection - optional)

#### FR-ATT-02: Lưu trữ Dữ liệu Khuôn mặt

**Mô tả:** Hệ thống CHỈ lưu vector đặc trưng, KHÔNG lưu ảnh gốc

**Data Privacy Design:**

**What to store:**

```json
{
  "student_id": "SV12345",
  "face_embeddings": [0.123, -0.456, 0.789, ...], // 128 hoặc 512 floats
  "embedding_version": "v1.0", // Model version
  "created_at": "2025-01-15T10:00:00Z",
  "updated_at": "2025-03-20T14:30:00Z",
  "quality_score": 0.92
}
```

**What NOT to store:**

- ❌ Original image files
- ❌ Cropped face images
- ❌ Any image data

**Process Flow:**

```
Upload Image → Face Detection → Extract Embedding → Store Vector → Delete Image
     ↓                                   ↓
  (Temp)                            (Permanent)
  ↓ (After processing)
Delete immediately
```

**Compliance:**

- Tuân thủ Nghị định 13/2023/NĐ-CP
- Có consent form khi SV upload lần đầu
- SV có quyền xóa face data bất kỳ lúc nào
- Export dữ liệu theo yêu cầu (Data Portability)

**Acceptance Criteria:**

- [ ] Zero image storage (audit trong code và DB)
- [ ] Consent form displayed và logged
- [ ] "Delete my face data" function works
- [ ] Data export includes face embedding với explanation
- [ ] Encryption at rest cho face vectors

#### FR-ATT-03: Ghi nhận Thời gian và Trạng thái

**Attendance Data Model:**

```json
{
  "id": "uuid",
  "event_id": "uuid",
  "student_id": "uuid",
  "registration_id": "uuid",

  "checkin_time": "datetime",
  "checkin_method": "enum: FaceRecognition, QRCode, Manual",
  "checkin_location": "string (station ID)",
  "checkin_confidence": "float (0-1, for FaceRecognition)",

  "checkout_time": "datetime or null",
  "checkout_method": "enum or null",

  "status": "enum: Present, Late, Absent, Excused",
  "duration": "integer (minutes, auto-calculated)",

  "notes": "string",
  "verified_by": "user_id or null (for manual checkin)",

  "created_at": "datetime",
  "updated_at": "datetime"
}
```

**Status Rules:**

- **Present:** Check-in within allowed window, stayed for minimum duration
- **Late:** Check-in after start time + grace period
- **Absent:** No check-in record
- **Excused:** Manually marked by BTC with reason

**Auto-calculate Duration:**

- If event requires check-out: duration = checkout_time - checkin_time
- If no check-out required: duration = event.end_time - checkin_time (max)

#### FR-ATT-04: Chỉnh sửa Điểm danh với Audit Log

**Use Case UC-ATT-04: BTC Chỉnh sửa Điểm danh**

**Actor:** Event Organizer, Event Owner

**Precondition:**

- Actor có quyền edit attendance cho sự kiện

**Main Flow:**

1. Actor vào "Quản lý điểm danh" của sự kiện
2. Tìm sinh viên cần chỉnh sửa (search/filter)
3. Click vào attendance record
4. Actor có thể:
   - Change status (Present ↔ Absent ↔ Late ↔ Excused)
   - Adjust check-in time
   - Add/edit notes
5. Hệ thống yêu cầu nhập lý do thay đổi (bắt buộc)
6. Actor nhập lý do và confirm
7. Hệ thống:
   - Update attendance record
   - Create audit log entry:
     ```json
     {
       "attendance_id": "...",
       "changed_by": "user_id",
       "changed_at": "datetime",
       "field_changed": "status",
       "old_value": "Absent",
       "new_value": "Present",
       "reason": "SV có giấy xin phép hợp lệ"
     }
     ```
   - Gửi notification cho SV (optional)
8. Hiển thị thông báo thành công
9. Use case kết thúc

**Business Rules:**

- Chỉ được edit sau khi sự kiện kết thúc hoặc đang diễn ra
- Không được edit sau X ngày (ví dụ: 7 ngày) kể từ khi event kết thúc (configurable)
- Mọi thay đổi phải có lý do

**Acceptance Criteria:**

- [ ] Audit log immutable (append-only)
- [ ] Hiển thị history của attendance record
- [ ] Filter/search logs by date, user, event
- [ ] Export audit logs
- [ ] Notification sent khi status change

---

### 5.6. Module Giám khảo/Khách mời

#### FR-GUEST-01: Khai báo Giám khảo/Khách mời

**Use Case UC-GUEST-01: Thêm Giám khảo**

**Actor:** Event Owner, Event Organizer

**Main Flow:**

1. Actor vào event detail → tab "Giám khảo/Khách mời"
2. Click "Thêm giám khảo mới"
3. Form nhập thông tin:
   - Họ tên (required)
   - Email (required, unique per event)
   - Số điện thoại
   - Chức danh/Công ty
   - Bio (optional)
   - Ảnh (optional)
   - Vai trò: Giám khảo chính / Giám khảo phụ / Khách mời danh dự / Diễn giả
4. Actor submit
5. Hệ thống:
   - Validate data
   - Create judge/guest record
   - Gán ID unique
6. Hiển thị trong danh sách
7. Use case kết thúc

**Alternative Flow:**

**4a. Chọn từ danh sách có sẵn**

- Nếu giám khảo đã từng tham gia sự kiện khác
- Search và select
- Auto-fill thông tin
- Có thể override cho sự kiện này

#### FR-GUEST-02: Kiểm soát Số lượng Giám khảo

**Business Rule:**

- Mỗi sự kiện có max judges config (ví dụ: 10 người)
- Enforce khi add new judge

#### FR-GUEST-03: Gán Vị trí Ngồi

**Use Case UC-GUEST-03: Phân bổ Vị trí**

**Actor:** Event Owner

**Main Flow:**

1. Actor vào "Quản lý vị trí ngồi"
2. Hệ thống hiển thị:
   - Sơ đồ chỗ ngồi (nếu có upload)
   - Hoặc danh sách positions (A1, A2, B1, B2...)
3. Actor drag-and-drop giám khảo vào vị trí
4. Hệ thống update assignment
5. Hiển thị layout preview

**Acceptance Criteria:**

- [ ] Visual seat map (nếu upload floor plan)
- [ ] Drag-and-drop UX
- [ ] Auto-save
- [ ] Print seat map với tên

#### FR-GUEST-04: Không trùng Vị trí

**Validation:**

- Mỗi vị trí chỉ có 1 người
- Conflict detection khi assign
- Highlight conflicts

#### FR-GUEST-05: Check-in Giám khảo

**Similar to student check-in:**

- Face recognition (nếu đã có face data)
- QR code (send QR via email)
- Manual check-in by staff

**Tracking:**

- Arrival time
- Departure time (if needed)

---

### 5.7. Module Tài liệu Sự kiện

#### FR-DOC-01: Upload và Quản lý Tài liệu

**Use Case UC-DOC-01: Upload Tài liệu**

**Actor:** Event Owner, Event Organizer

**Supported File Types:**

- Documents: PDF, DOCX, PPTX, XLSX
- Images: JPG, PNG, GIF
- Videos: Link YouTube, Google Drive (không upload trực tiếp)
- Links: Arbitrary URLs

**Main Flow:**

1. Actor vào event → tab "Tài liệu"
2. Click "Upload tài liệu mới"
3. Choose upload method:
   - Upload file
   - Paste link
4. Fill metadata:
   - Tiêu đề (required)
   - Mô tả (optional)
   - Loại: Slide thuyết trình / Tài liệu tham khảo / Hình ảnh / Video
   - Quyền truy cập: Public / Registered / Private
5. Upload/Submit
6. Hệ thống:
   - Validate file size, type
   - Scan virus (if upload file)
   - Store in object storage
   - Create document record
   - Generate thumbnail (for PDF, images)
7. Hiển thị trong danh sách

**Acceptance Criteria:**

- [ ] Max file size: 50MB/file
- [ ] Progress bar khi upload
- [ ] Virus scanning (ClamAV hoặc tương tự)
- [ ] Thumbnail generation
- [ ] Support batch upload (multiple files)

#### FR-DOC-02: Hỗ trợ Định dạng

**Requirements:**

- PDF: Viewable in browser, downloadable
- PPT/DOCX: Download only (hoặc preview qua Google Docs Viewer)
- Images: Gallery view
- Video links: Embed player

#### FR-DOC-03: Phân quyền Truy cập

**Access Levels:**

1. **Public:** Mọi người đều xem được (kể cả chưa đăng nhập)
2. **Registered:** Chỉ SV đã đăng ký sự kiện
3. **Private:** Chỉ BTC và giám khảo/khách mời

**Implementation:**

- Check quyền khi access document URL
- Sử dụng signed URLs (expiring tokens) cho download

#### FR-DOC-04: Theo dõi Lượt xem

**Tracking:**

```json
{
  "document_id": "uuid",
  "viewed_by": "user_id",
  "viewed_at": "datetime",
  "view_duration": "integer (seconds)",
  "device": "string",
  "ip_address": "string (hashed)"
}
```

**Analytics:**

- Total views
- Unique viewers
- Most viewed documents
- View timeline (chart)

**Privacy:**

- Anonymous cho public documents
- Tracked cho registered users (với consent)

---

### 5.8. Module Chatbot AI

#### FR-CHAT-01: Chatbot AI Assistant

**Architecture:**

- Frontend: Chat widget (web + mobile)
- Backend: Chat API + LLM integration
- Vector DB: For RAG (documents embedding)

#### FR-CHAT-02: Chatbot cho Sinh viên

**Capabilities:**

**1. Tra cứu Sự kiện**

- Query: "Có sự kiện nào về AI trong tháng này?"
- Chatbot search events và trả về list với links

**2. Trạng thái Tham gia**

- Query: "Tôi đã đăng ký sự kiện nào?"
- Chatbot fetch user's registrations và hiển thị

**3. Hướng dẫn Check-in**

- Query: "Làm sao để check-in?"
- Chatbot: Step-by-step guide + link đến FAQ

**4. FAQ**

- Pre-trained với common questions
- Context: Policies, rules, procedures

**Implementation:**

```
User Query → Intent Classification → Action
            → Event Search
            → User Data Lookup
            → Knowledge Base Search
            → LLM Generation
```

**Tech Stack:**

- LLM: GPT-4, Claude, hoặc open-source (Llama, Mistral)
- Intent Classification: Fine-tuned classifier
- RAG: Pinecone / Weaviate + Event data + Documents

#### FR-CHAT-03: Chatbot cho BTC

**Capabilities:**

**1. Hỗ trợ Viết Nội dung**

- Tương tự FR-AI-01 nhưng qua chat interface
- Generate email templates, announcements

**2. Trả lời Dựa trên Tài liệu**

- BTC upload documents vào event
- Documents được index vào vector DB
- Chatbot có thể answer questions based on documents
- Example: "Lịch trình của sự kiện như thế nào?" → Chatbot extract từ PDF đã upload

**3. Data Query**

- "Có bao nhiêu SV đã check-in?"
- Chatbot query DB và format response

**RAG Implementation:**

```python
# Pseudo-code
def answer_question(query, event_id):
    # 1. Retrieve relevant documents
    docs = vector_db.search(
        query_embedding=embed(query),
        filter={"event_id": event_id},
        top_k=5
    )

    # 2. Construct prompt
    context = "\n".join([doc.content for doc in docs])
    prompt = f"""
    Context: {context}

    Question: {query}

    Answer based on the context above. If not found, say so.
    """

    # 3. Generate answer
    answer = llm.generate(prompt)

    # 4. Add citations
    return {
        "answer": answer,
        "sources": [doc.title for doc in docs]
    }
```

**Acceptance Criteria:**

- [ ] Response time < 3 seconds
- [ ] Accurate intent classification ≥ 90%
- [ ] RAG retrieval precision ≥ 80%
- [ ] Conversational, friendly tone
- [ ] Fallback to human support if confused
- [ ] Multi-turn conversation support
- [ ] Context awareness (remember previous messages)

---

### 5.9. Module Báo cáo & Thống kê

#### FR-RPT-01: Báo cáo Đa chiều

**Report Types:**

**1. Event-level Reports**

- Event summary: Basic info, stats
- Registration report: Who registered, when, status
- Attendance report: Who attended, check-in time, duration
- No-show analysis: Who didn't show up
- Waitlist report

**2. Faculty/Department Reports**

- Participation by faculty
- Top events by faculty
- Average attendance rate

**3. Class/Year Reports**

- Participation by class
- Training points earned

**4. Student-level Reports**

- Individual participation history
- Training points accumulation
- Attendance rate
- Event preferences (based on registration pattern)

**5. System-wide Reports**

- Total events by period
- Total participants
- Peak times
- Popular event types
- Trend analysis

**Dashboard Widgets:**

- KPI cards: Total events, Total check-ins, Avg attendance rate, Active users
- Charts: Events over time (line), Participants by faculty (bar), Event types distribution (pie)
- Calendar heatmap: Event density
- Recent activities feed

#### FR-RPT-02: Export Báo cáo

**Export Formats:**

**Excel (.xlsx)**

- Multiple sheets cho different tables
- Formatted headers, colors
- Charts embedded
- Generated by library: ExcelJS, openpyxl

**PDF**

- Professional layout
- Charts and tables
- Cover page với logo
- Generated by library: PDFKit, ReportLab, puppeteer

**CSV**

- Simple tabular data
- UTF-8 with BOM (for Vietnamese)

**Implementation:**

```javascript
// Endpoint
POST /api/reports/generate
{
  "report_type": "event_attendance",
  "event_id": "uuid",
  "format": "excel",
  "filters": {...},
  "date_range": {"from": "...", "to": "..."}
}

// Response
{
  "report_id": "uuid",
  "status": "processing",
  "estimated_time": 30 // seconds
}

// Download
GET /api/reports/{report_id}/download
→ File download
```

**Async Processing:**

- Long reports (>1000 rows) processed asynchronously
- Queue system (BullMQ, Celery)
- Email notification khi ready
- Download link (expires after 24h)

**Acceptance Criteria:**

- [ ] Excel: Formatted, multiple sheets
- [ ] PDF: Professional layout
- [ ] CSV: Proper encoding
- [ ] Large reports (10K+ rows) processed async
- [ ] Download link expires properly
- [ ] Caching cho repeated queries

---

## 6. YÊU CẦU PHI CHỨC NĂNG

### 6.1. Hiệu năng (Performance)

#### NFR-PERF-01: Response Time

| Operation                    | Target  | Max Acceptable |
| ---------------------------- | ------- | -------------- |
| Page load (Web)              | < 2s    | < 5s           |
| API response (simple)        | < 200ms | < 500ms        |
| API response (complex query) | < 1s    | < 3s           |
| Face recognition             | < 1s    | < 2s           |
| Report generation (sync)     | < 5s    | < 10s          |
| Chatbot response             | < 3s    | < 5s           |

#### NFR-PERF-02: Throughput

- Concurrent users: 500-1000 peak
- Check-in rate: ≥ 1000 check-ins/day, peak 100/minute
- API requests: ≥ 10,000 requests/hour
- Database queries: ≥ 1000 QPS

#### NFR-PERF-03: Resource Usage

- CPU utilization: < 70% average, < 90% peak
- Memory utilization: < 80% average
- Database connections: < 80% of pool size
- Disk I/O: < 70% capacity

#### NFR-PERF-04: Scalability

- Horizontal scaling: Support adding more app servers
- Database: Read replicas cho reporting queries
- Caching: Redis cho frequently accessed data
- CDN: Static assets (images, JS, CSS)
- Load balancer: Nginx/HAProxy

**Test Scenarios:**

- Load test: 1000 concurrent users
- Stress test: Ramp up to failure point
- Spike test: Sudden traffic increase
- Endurance test: 24h sustained load

### 6.2. Khả dụng (Availability)

#### NFR-AVAIL-01: Uptime

- Target: 99.5% uptime (≈ 3.6 hours downtime/month)
- Scheduled maintenance: 1st Sunday of month, 2-4 AM
- Unplanned downtime: < 1 hour/month

#### NFR-AVAIL-02: Recovery

- RTO (Recovery Time Objective): < 1 hour
- RPO (Recovery Point Objective): < 15 minutes
- Backup frequency: Daily full + hourly incremental
- Backup retention: 30 days

#### NFR-AVAIL-03: Failover

- Database: Master-slave replication, auto-failover
- Application: Multiple instances behind load balancer
- Monitoring: 24/7 monitoring, alerts

### 6.3. Bảo mật (Security)

(Xem thêm phần 7)

#### NFR-SEC-01: Data Encryption

- In transit: TLS 1.2+
- At rest: AES-256 encryption cho sensitive data (face vectors, personal info)

#### NFR-SEC-02: Authentication

- Password hashing: bcrypt, scrypt, or Argon2
- Session management: Secure, httpOnly cookies
- Token expiration: 30 minutes (access), 7 days (refresh)

#### NFR-SEC-03: Authorization

- RBAC enforced at API level
- Principle of least privilege
- Regular audit of permissions

### 6.4. Khả năng bảo trì (Maintainability)

#### NFR-MAINT-01: Code Quality

- Code coverage: ≥ 70%
- Code review: All PRs reviewed
- Linting: ESLint, Pylint, etc.
- Static analysis: SonarQube

#### NFR-MAINT-02: Documentation

- API documentation: OpenAPI/Swagger
- Code documentation: JSDoc, Docstrings
- Architecture diagrams: Up-to-date
- Runbooks: For operations

#### NFR-MAINT-03: Logging

- Structured logging: JSON format
- Log levels: DEBUG, INFO, WARN, ERROR
- Centralized logging: ELK stack hoặc tương tự
- Log retention: 90 days

#### NFR-MAINT-04: Monitoring

- APM: Application Performance Monitoring
- Infrastructure monitoring: Server metrics
- Business metrics: Custom dashboards
- Alerting: PagerDuty, Opsgenie

### 6.5. Khả năng sử dụng (Usability)

#### NFR-USE-01: Learnability

- New user onboarding: < 10 minutes
- Intuitive UI: No training required for basic tasks
- Contextual help: Tooltips, guided tours

#### NFR-USE-02: Accessibility

- WCAG 2.1 Level AA compliance
- Keyboard navigation
- Screen reader support
- Color contrast ratios

#### NFR-USE-03: Internationalization (Future)

- Multi-language support ready
- Currency và date format localization
- RTL support (if needed)

### 6.6. Tương thích (Compatibility)

#### NFR-COMP-01: Browser Support

**Web:**

- Chrome/Edge: Last 2 versions
- Firefox: Last 2 versions
- Safari: Last 2 versions on macOS/iOS

**Mobile:**

- Android: 8.0+ (API 26+)
- iOS: 13.0+

#### NFR-COMP-02: Device Support

- Desktop: 1366x768 minimum
- Tablet: 768x1024 minimum
- Mobile: 375x667 minimum (iPhone SE)

### 6.7. Legal & Compliance

#### NFR-LEGAL-01: GDPR/PDPA Compliance

- Consent management
- Right to access data
- Right to be forgotten
- Data portability
- Data retention policies

#### NFR-LEGAL-02: Audit Trail

- All sensitive operations logged
- Immutable audit logs
- Queryable for compliance reporting

---

## 7. YÊU CẦU BẢO MẬT

### 7.1. Authentication Security

#### SEC-AUTH-01: Password Security

**Requirements:**

- Minimum length: 8 characters
- Complexity: Mix of uppercase, lowercase, numbers, special chars
- Password history: Cannot reuse last 3 passwords
- Hashing: bcrypt with cost factor 12+

#### SEC-AUTH-02: Multi-Factor Authentication (MFA)

- Optional for regular users
- Mandatory for Admin accounts
- TOTP-based (Google Authenticator compatible)

#### SEC-AUTH-03: Session Security

- Secure, httpOnly, sameSite cookies
- CSRF tokens
- Session timeout: 30 minutes inactive
- Concurrent session limit: 3

### 7.2. Authorization Security

#### SEC-AUTHZ-01: Role-Based Access Control

- Centralized permission checks
- Deny by default
- Explicit grants only

#### SEC-AUTHZ-02: API Security

- API keys for integrations
- Rate limiting per key
- IP whitelisting (optional)

### 7.3. Data Security

#### SEC-DATA-01: Sensitive Data Protection

**PII (Personally Identifiable Information):**

- Full name, email, phone: Encrypted at rest
- Student ID: Partially masked in logs
- Face vectors: Encrypted at rest

**Encryption Standards:**

- Algorithm: AES-256-GCM
- Key management: AWS KMS, HashiCorp Vault, or similar
- Key rotation: Every 90 days

#### SEC-DATA-02: Data Transmission

- All API calls over HTTPS only
- HTTP Strict Transport Security (HSTS) enabled
- Certificate pinning (mobile apps)

### 7.4. Application Security

#### SEC-APP-01: Input Validation

- Whitelist approach
- Sanitize all user inputs
- Parameterized queries (prevent SQL injection)
- Content Security Policy (CSP) headers

#### SEC-APP-02: Output Encoding

- HTML encoding for user-generated content
- Prevent XSS attacks

#### SEC-APP-03: File Upload Security

- Validate file types (whitelist)
- Virus scanning
- Store in isolated location (not web-accessible directly)
- Serve via signed URLs

#### SEC-APP-04: Dependency Security

- Regular dependency updates
- Vulnerability scanning (Snyk, Dependabot)
- Patch critical vulnerabilities within 7 days

### 7.5. Infrastructure Security

#### SEC-INFRA-01: Network Security

- Firewall rules: Only necessary ports open
- VPC/Private networks
- WAF (Web Application Firewall)
- DDoS protection

#### SEC-INFRA-02: Server Hardening

- Regular OS updates
- Disable unnecessary services
- Principle of least privilege for service accounts
- SSH key-based authentication only

### 7.6. Incident Response

#### SEC-IR-01: Security Monitoring

- Intrusion detection system (IDS)
- Log analysis for suspicious activities
- Real-time alerts for security events

#### SEC-IR-02: Incident Response Plan

- Defined roles and responsibilities
- Communication plan
- Forensics and recovery procedures
- Post-mortem and lessons learned

### 7.7. Privacy Protection

#### SEC-PRIV-01: Data Minimization

- Collect only necessary data
- Delete data when no longer needed
- Anonymize data for analytics where possible

#### SEC-PRIV-02: Consent Management

- Clear consent forms
- Granular consent options
- Easy withdrawal of consent

#### SEC-PRIV-03: Face Data Protection

- Only store face vectors, not images
- Vectors encrypted at rest
- Secure deletion when user requests
- Access logs for face data

---

## 8. YÊU CẦU DỮ LIỆU

### 8.1. Data Model Overview

**Core Entities:**

1. Users (Admin, Staff, Students, Leaders)
2. Events
3. Registrations
4. Attendance
5. Documents
6. Judges/Guests
7. Notifications
8. Audit Logs

\*\*(Simplified ERD sẽ được cung cấp trong phụ lục)
