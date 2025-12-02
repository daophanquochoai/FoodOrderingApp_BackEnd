# FoodOrderingApp_BackEnd

## 🍽️ Giới thiệu dự án  
**FoodOrderingApp_BackEnd** là hệ thống backend cho ứng dụng đặt món ăn trực tuyến.  
Dự án được xây dựng theo mô hình micro-services nhằm mô phỏng một hệ thống thương mại điện tử trong thực tế, bao gồm đăng nhập – quản lý người dùng – menu món ăn – đặt hàng – quản lý kho – phối hợp giữa các service thông qua API Gateway và Eureka Discovery.

Ứng dụng hướng đến mục tiêu:
- Rèn luyện kiến trúc Microservices trong Spring Cloud  
- Tối ưu hóa khả năng mở rộng và deploy  
- Xây dựng môi trường học tập, demo phỏng sát quy trình đặt món hoàn chỉnh

---

## 🎯 Các tính năng nổi bật  

✔ Authentication & Authorization (JWT/OAuth tuỳ cấu hình)  
✔ User Profile / Customer Management  
✔ CRUD menu, danh mục món ăn  
✔ Order Processing – thanh toán cơ bản / xác nhận đơn  
✔ Inventory tracking (theo món hoặc nguyên liệu)  
✔ API Gateway cho routing và phân luồng request  
✔ Service Discovery giúp micro-service tự tìm nhau  

Ngoài ra có thể mở rộng:
- Payment service (VNPay / Stripe / MoMo)  
- Notification service (email / SMS / Firebase)  
- Review / rating service  
- Recommendation service  

---

## 📦 Kiến trúc hệ thống  

Hệ thống triển khai theo Spring Cloud Microservices:

- **authservice** → xác thực & token  
- **userservice** → thông tin người dùng  
- **foodservice** → menu, món ăn  
- **orderservice** → xử lý đơn hàng  
- **inventoryservice** → kho nguyên liệu  
- **apigateway** → entrypoint duy nhất cho client  
- **eureka** → service registry  
- **configserver** → quản lý config tập trung  

Hỗ trợ Docker Compose & Kubernetes để deploy

```
FoodOrderingApp_BackEnd/
│── authservice/
│── userservice/
│── foodservice/
│── orderservice/
│── inventoryservice/
│── apigateway/
│── eureka/
│── configserver/
│── docker-compose.yml
│── k8s/
└── docs/
```

---

## 🛠 Công nghệ sử dụng  

🔹 Java + Spring Boot  
🔹 Spring Cloud (Eureka, Config Server, Gateway)  
🔹 RESTful API  
🔹 Docker / Kubernetes  
🔹 (Có thể mở rộng CI/CD)  

---

## ⚙️ Yêu cầu hệ thống  

- Java JDK phù hợp  
- Maven hoặc Gradle  
- Docker + Docker Compose  
- kubectl (nếu deploy K8s)

---

## 🚀 Hướng dẫn chạy dự án  

### Chạy nhanh với Docker Compose
```bash
git clone https://github.com/daophanquochoai/FoodOrderingApp_BackEnd.git
cd FoodOrderingApp_BackEnd
docker-compose up --build
```

### Chạy mỗi service nếu develop local
```bash
cd userservice
mvn clean install
```

---

## 📌 Use Case tổng quát  

1️⃣ Người dùng đăng ký tài khoản  
2️⃣ Đăng nhập và nhận token  
3️⃣ Xem menu món ăn  
4️⃣ Chọn món → tạo đơn hàng  
5️⃣ Backend check kho / inventory  
6️⃣ Order xác nhận / lưu lịch sử đơn  

---

## 🧩 Mục tiêu học thuật / thực tế  

Dự án phù hợp cho:
- Tự học micro-services  
- Làm đồ án sinh viên  
- Demo thực hành DevOps, CI/CD  
- Showcase portfolio khi xin việc  

---

## 👨‍💻 Đóng góp  

Nếu bạn muốn đóng góp:  

1. Fork repo  
2. Tạo nhánh mới: `feature/ten-chuc-nang`  
3. Commit thay đổi  
4. Tạo Pull Request  

---

## 📜 License  
Thêm license theo tuỳ chọn (MIT / Apache, v.v.)

---  

