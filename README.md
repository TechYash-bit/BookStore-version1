# 📚 Online Book Store Application

An Online Book Store web application developed using Spring Boot that allows users to manage books and orders through RESTful APIs. The project follows a layered architecture with proper separation of concerns and database integration.

📷 All project screenshots and API testing images are hosted in this GitHub repository:
https://github.com/TechYash-bit/imagesbookstore

---

## 🚀 Features
- User registration and login
- Role-based access (Admin/User)
- Add, update, delete, and view books
- Search books by title or author
- Place and manage book orders
- View order history

---

## 🛠️ Tech Stack
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- REST API
- Postman

---

## 📂 Project Structure
BookStore-version1
├── controller
├── service
├── repository
├── entity
├── dto
├── exception
└── application.properties

---

## 🧩 Entity Details

### 📘 BookEntity
- id (Primary Key)
- title
- author
- category
- price
- stockQuantity
- description
- createdAt
- updatedAt

### 👤 UserEntity
- id (Primary Key)
- name
- email
- password
- role (ADMIN / USER)
- createdAt
- updatedAt

### 🛒 OrderEntity
- id (Primary Key)
- orderDate
- totalAmount
- orderStatus
- user (Many-to-One)
- createdAt
- updatedAt

### 📦 OrderItemEntity
- id (Primary Key)
- quantity
- price
- book (Many-to-One)
- order (Many-to-One)

### 💳 PaymentEntity (Optional / Future)
- id (Primary Key)
- paymentMethod
- paymentStatus
- transactionId
- order (One-to-One)

---

## ⚙️ Installation & Setup
1. Clone the repository
   git clone https://github.com/TechYash-bit/BookStore-version1.git

2. Configure MySQL in application.properties
   spring.datasource.url=jdbc:mysql://localhost:3306/bookstore_db
   spring.datasource.username=root
   spring.datasource.password=your_password

3. Run the application
   mvn spring-boot:run

4. Access the application
   http://localhost:8080

---

## 🔍 API Endpoints
- GET /books
- POST /books
- PUT /books/{id}
- DELETE /books/{id}
- POST /orders

---

## 📌 Future Enhancements
- JWT authentication
- Swagger documentation
- Frontend integration
- Payment gateway
- Pagination & sorting

---

## 👨‍💻 Author
Yash Harne
GitHub: https://github.com/TechYash-bit
