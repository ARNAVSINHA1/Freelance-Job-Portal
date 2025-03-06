# Freelance Job Portal API

## 🚀 Introduction
The **Freelance Job Portal API** is a backend system built using **Spring Boot, Hibernate, JPA, and MySQL**. It provides RESTful APIs for managing job postings and user accounts.

## 📚 Table of Contents
- [Technologies Used](#-technologies-used)
- [API Endpoints](#-api-endpoints)
  - [User Endpoints](#user-endpoints)
  - [Job Endpoints](#job-endpoints)
- [Authentication](#-authentication)
- [Installation and Setup](#-installation-and-setup)
- [Contributions](#-contributions)
- [Authors](#-authors)
- [License](#-license)

## 🛠 Technologies Used
- **Spring Boot**: A framework that simplifies the development of Java applications by providing a set of tools and libraries.
- **Hibernate**: An ORM (Object-Relational Mapping) tool for Java that simplifies database interactions.
- **JPA (Java Persistence API)**: A specification for accessing, persisting, and managing data between Java objects and relational databases.
- **MySQL**: A popular relational database management system used for storing application data.
- **HTML, CSS, JavaScript**: Technologies used for the frontend development of the application.

## 📡 API Endpoints

### User Endpoints
- **POST** `/api/users/register`: Register a new user.
- **GET** `/api/users/list`: List all users.
- **GET** `/api/users/email/{email}`: Find a user by email.
- **PUT** `/api/users/update/{id}`: Update user information.
- **DELETE** `/api/users/delete/{id}`: Delete a user.
- **PUT** `/api/users/change-password/{userId}`: Change a user's password.

### Job Endpoints
- **POST** `/api/jobs/register`: Register a new job.
- **GET** `/api/jobs/list`: List all jobs.
- **GET** `/api/jobs/{id}`: Retrieve a job by ID.
- **PUT** `/api/jobs/update/{id}`: Update job information.
- **DELETE** `/api/jobs/delete/{id}`: Delete a job.
- **GET** `/api/jobs/employer/{employer}`: Find jobs by employer.
- **GET** `/api/jobs/search`: Search for jobs by title.
- **POST** `/api/jobs/apply/{jobId}/user/{userId}`: Apply for a job.
- **GET** `/api/jobs/applications/{userId}`: Get job applications for a user.

## 🔒 Authentication
The API requires user authentication for certain endpoints.

## 📦 Installation and Setup
1. Clone the repository: `git clone <repository-url>`
2. Navigate to the project directory: `cd Freelance-Job-Portal`
3. Install dependencies: `mvn install`
4. Run the application: `mvn spring-boot:run`

## 🤝 Contributions
Contributions are welcome! Please submit a pull request or open an issue for any suggestions or improvements.

## 👤 Authors
- [Your Name](https://github.com/yourprofile)

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
