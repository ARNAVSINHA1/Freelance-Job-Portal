# Freelance Job Portal API

## 🚀 Introduction
The **Freelance Job Portal API** is a backend system built using **Spring Boot, Hibernate, JPA, and MySQL**. It provides RESTful APIs for managing job postings, user authentication, and job listings.

## 🛠 Tech Stack
- **Spring Boot** - For backend logic
- **Spring MVC** - For RESTful API development
- **Hibernate & JPA** - For ORM and database interaction
- **MySQL** - For database management
- **Postman** - For API testing

## 📌 Features
- **User Registration & Authentication**
- **Job Posting & Management**
- **Retrieve Job Listings**
- **Secure API Endpoints**
- **Role-Based Access Control**

## 🚀 Installation & Setup
### 1️⃣ Clone the Repository
```bash
https://github.com/ARNAVSINHA1/Freelance-Job-Portal.git
```

### 2️⃣ Configure Database
- Update `application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/freelance_db
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### 3️⃣ Build & Run the Application
```bash
mvn clean install
mvn spring-boot:run
```

## 📡 API Endpoints
### 🔹 Test API
```http
GET /api/jobs/test
```
✅ Response: `Test endpoint works!`

### 🔹 Post a Job
```http
POST /api/jobs/post
```
📥 Request Body (JSON):
```json
{
  "title": "Software Engineer",
  "description": "Looking for an experienced Java developer",
  "salary": 60000
}
```
✅ Response: `Job posted successfully!`

### 🔹 List All Jobs
```http
GET /api/jobs/list
```
✅ Response: List of jobs in JSON format.

## 📝 Contribution
Feel free to **fork** the repository and submit **pull requests** to contribute! 🎯

## 🏆 Author
👤 **Arnava Kumar Sinha**  
🔗 [LinkedIn](https://www.linkedin.com/in/arnavsinha1) | [Portfolio](https://arnavsinha1.github.io/arnava-portfolio/)

## 📜 License
This project is **open-source** under the [MIT License](LICENSE).
