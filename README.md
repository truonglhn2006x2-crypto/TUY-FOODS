# TUY-FOODS

Ứng dụng Android hỗ trợ đặt món ăn trực tuyến, được xây dựng bằng **Kotlin + Android Studio**, kết nối với **Spring Boot REST API** và **MySQL**.

## 1. Công nghệ sử dụng

### Android

* Android Studio
* Kotlin
* Gradle
* Retrofit
* Gson
* OkHttp

### Backend

* Java
* Spring Boot
* Maven
* Spring Data JPA
* MySQL

### Database

* MySQL 8.x
* Database: `food_order`

### AI

* Gemini API

---

# 2. Cấu trúc project

```text
TUY-FOODS/
│
├── android/                 # Android application
│
├── api/
│   └── demo/                # Spring Boot backend
│
├── database/                # Database scripts
│
├── README.md
│
└── .gitignore
```

---

# 3. Yêu cầu môi trường

Trước khi chạy project, cần cài:

* **Android Studio**
* **JDK 17**
* **MySQL 8.x**
* Git

> Khuyến nghị sử dụng **JDK 17** cho project backend.

Kiểm tra Java:

```bash
java -version
```

Kết quả nên hiển thị Java 17.

Kiểm tra Git:

```bash
git --version
```

---

# 4. Clone project

Mở Terminal hoặc PowerShell:

```bash
git clone https://github.com/truonglhn2006x2-crypto/TUY-FOODS.git
```

Di chuyển vào project:

```bash
cd TUY-FOODS
```

---

# 5. Cài đặt MySQL

Cài **MySQL 8.x** và đảm bảo MySQL Server đang chạy.

Tạo database:

```sql
CREATE DATABASE food_order;
```

Sau khi tạo database, kiểm tra:

```sql
SHOW DATABASES;
```

Phải thấy:

```text
food_order
```

---

# 6. Cấu hình Backend

Backend nằm tại:

```text
api/demo/
```

Mở file:

```text
api/demo/src/main/resources/application.properties
```

File mặc định trên GitHub sử dụng placeholder để không lưu mật khẩu database hoặc API key thật:

```properties
spring.application.name=tuyfoods

spring.datasource.url=jdbc:mysql://localhost:3306/food_order
spring.datasource.username=root
spring.datasource.password=YOUR_DB_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration

server.port=8081
server.address=0.0.0.0

gemini.api.key=YOUR_GEMINI_API_KEY
```

### Thay mật khẩu MySQL

Ví dụ MySQL của bạn sử dụng:

```text
Username: root
Password: 12345678
```

thì sửa:

```properties
spring.datasource.password=12345678
```

### Cấu hình Gemini API

Thay:

```properties
gemini.api.key=YOUR_GEMINI_API_KEY
```

bằng API key Gemini của bạn:

```properties
gemini.api.key=YOUR_API_KEY
```

**Không commit API key thật lên GitHub.**

---

# 7. Chạy Backend

Mở Terminal tại thư mục:

```text
TUY-FOODS/api/demo
```

Có thể chạy bằng Maven Wrapper:

### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

Hoặc nếu máy đã cài Maven:

```powershell
mvn spring-boot:run
```

Backend sử dụng port:

```text
8081
```

Do đó server chạy tại:

```text
http://localhost:8081
```

Nếu chạy thành công, giữ cửa sổ Terminal này mở trong suốt quá trình chạy app Android.

---

# 8. Kiểm tra Backend

Sau khi backend khởi động, có thể kiểm tra server bằng trình duyệt hoặc API client.

Ví dụ:

```text
http://localhost:8081
```

Nếu API không phản hồi, kiểm tra:

1. MySQL đã chạy chưa.
2. Database `food_order` đã được tạo chưa.
3. Username/password MySQL trong `application.properties` có đúng không.
4. Backend có khởi động thành công không.
5. Port `8081` có bị ứng dụng khác sử dụng không.

---

# 9. Cấu hình Android Studio

Mở Android Studio.

Chọn:

```text
Open
```

Sau đó mở thư mục:

```text
TUY-FOODS/android
```

**Không mở riêng thư mục `android/app`.**

Chờ Android Studio hoàn thành:

* Gradle Sync
* Download dependencies
* Indexing

Sau khi Gradle Sync hoàn tất, chọn module:

```text
app
```

---

# 10. Cấu hình kết nối Android → Backend

Android sử dụng Retrofit để kết nối tới Spring Boot backend.

File cấu hình:

```text
android/app/src/main/java/com/example/tuyfood/ApiService.kt
```

Trong file này có:

```kotlin
private const val BASE_URL =
    "http://192.168.1.165:8081/"
```

Địa chỉ này cần được thay đổi tùy theo cách chạy ứng dụng.

---

## 10.1. Chạy bằng Android Emulator

Nếu sử dụng **Android Emulator**, backend chạy trên chính máy tính thì sử dụng:

```text
http://10.0.2.2:8081/
```

Sửa:

```kotlin
private const val BASE_URL =
    "http://10.0.2.2:8081/"
```

`10.0.2.2` là địa chỉ đặc biệt để Android Emulator truy cập `localhost` của máy tính.

---

## 10.2. Chạy bằng điện thoại Android thật

Nếu chạy app trên điện thoại thật:

### Bước 1 — Kết nối máy tính và điện thoại cùng Wi-Fi

Máy tính và điện thoại phải sử dụng **cùng một mạng Wi-Fi**.

### Bước 2 — Kiểm tra IPv4 của máy tính

Mở PowerShell:

```powershell
ipconfig
```

Tìm:

```text
IPv4 Address
```

Ví dụ:

```text
192.168.1.165
```

### Bước 3 — Sửa BASE_URL

Trong:

```text
android/app/src/main/java/com/example/tuyfood/ApiService.kt
```

sửa thành:

```kotlin
private const val BASE_URL =
    "http://192.168.1.165:8081/"
```

Thay `192.168.1.165` bằng IPv4 thực tế của máy tính.

### Bước 4 — Đảm bảo Backend đang chạy

Backend phải chạy tại:

```text
0.0.0.0:8081
```

Project đã cấu hình:

```properties
server.port=8081
server.address=0.0.0.0
```

---

# 11. Chạy Android App

Sau khi:

* MySQL đang chạy
* Database `food_order` đã tồn tại
* Backend Spring Boot đang chạy
* `BASE_URL` đã được cấu hình đúng

Quay lại Android Studio.

Chọn:

```text
app
```

Sau đó chọn:

* Android Emulator

hoặc:

* Điện thoại Android đã kết nối USB

Nhấn:

```text
Run ▶
```

Android Studio sẽ build và cài ứng dụng lên thiết bị.

---

# 12. Thứ tự chạy project

Để tránh lỗi kết nối, nên chạy theo đúng thứ tự:

```text
1. MySQL
   ↓
2. Database food_order
   ↓
3. Spring Boot Backend
   ↓
4. Kiểm tra Backend port 8081
   ↓
5. Cấu hình BASE_URL trong Android
   ↓
6. Android Studio
   ↓
7. Run app
```

---

# 13. Các lỗi thường gặp

## Lỗi: Cannot connect to database

Kiểm tra:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/food_order
spring.datasource.username=root
spring.datasource.password=YOUR_DB_PASSWORD
```

Đảm bảo MySQL đang chạy và database `food_order` tồn tại.

---

## Lỗi: App báo Server Error

Kiểm tra:

### Backend có đang chạy không?

Backend phải chạy ở:

```text
8081
```

### Kiểm tra BASE_URL

Android Emulator:

```text
http://10.0.2.2:8081/
```

Điện thoại thật:

```text
http://IP_MAY_TINH:8081/
```

Ví dụ:

```text
http://192.168.1.165:8081/
```

---

## Lỗi: Điện thoại thật không kết nối được Backend

Kiểm tra:

* Điện thoại và máy tính cùng Wi-Fi.
* IPv4 của máy tính đúng.
* Backend đang chạy.
* Port `8081` không bị Firewall chặn.
* `BASE_URL` sử dụng đúng IPv4 của máy tính.

---

## Lỗi Gradle

Trong Android Studio:

```text
File → Sync Project with Gradle Files
```

Sau đó:

```text
Build → Clean Project
Build → Rebuild Project
```

Project có sẵn Gradle Wrapper nên có thể sử dụng file:

```text
android/gradlew
android/gradlew.bat
```

---

# 14. Chạy nhanh — Checklist

Trước khi Run App, kiểm tra:

```text
[ ] JDK 17 đã cài
[ ] Android Studio đã cài
[ ] MySQL 8.x đã cài
[ ] MySQL Server đang chạy
[ ] Database food_order đã tạo
[ ] application.properties đã nhập password MySQL
[ ] Gemini API key đã được cấu hình nếu sử dụng chức năng AI
[ ] Backend đã chạy tại port 8081
[ ] BASE_URL trong ApiService.kt đúng
[ ] Android Emulator/điện thoại đã kết nối
[ ] Android Studio đã Gradle Sync thành công
```

Sau đó:

```text
Run ▶
```

---

# 15. Lưu ý về API Key và mật khẩu

Không đưa các thông tin sau lên GitHub:

```text
MySQL password
Gemini API key
```

File `application.properties` trong repository chỉ chứa:

```properties
spring.datasource.password=YOUR_DB_PASSWORD
gemini.api.key=YOUR_GEMINI_API_KEY
```

Người chạy project cần tự thay bằng thông tin của môi trường máy mình.

---

# 16. Công nghệ và thành phần chính

```text
Android
├── Kotlin
├── Android Studio
├── Retrofit
├── Gson
└── OkHttp

Backend
├── Java
├── Spring Boot
├── Spring Data JPA
└── Maven

Database
└── MySQL

AI
└── Gemini API
```

---

## 17. Tác giả

**TUY-FOODS — Android Food Ordering Application**

Dự án được thực hiện phục vụ mục đích học tập và phát triển ứng dụng Android kết nối Backend và Database.
