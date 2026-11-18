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

### Authentication
- **GET** `/api/auth/csrf`: Create/read the CSRF token for the current session.
- **POST** `/api/auth/login`: Authenticate using form fields `email` and `password`; establishes a session.
- **POST** `/api/auth/logout`: End the current session.

### User Endpoints
- **POST** `/api/users`: Register a user; public, always assigns `USER`.
- **GET** `/api/users`: List users; `ADMIN` only.
- **GET** `/api/users/{id}`: Get own profile or any profile as `ADMIN`.
- **PUT** `/api/users/{id}`: Update own name/email or any user as `ADMIN`.
- **PATCH** `/api/users/{id}/role`: Change a role; `ADMIN` only.
- **DELETE** `/api/users/{id}`: Delete own profile or any user as `ADMIN`.
- **POST** `/api/users/{id}/change-password`: Change own password after providing the current password.

### Job Endpoints
- **POST** `/api/jobs`: Create a job; currently `ADMIN` only.
- **GET** `/api/jobs`: List all jobs; public.
- **GET** `/api/jobs/{id}`: Retrieve a job by ID.
- **PUT** `/api/jobs/{id}`: Update a job; currently `ADMIN` only.
- **DELETE** `/api/jobs/{id}`: Delete a job; currently `ADMIN` only.
- **GET** `/api/jobs/employer/{employer}`: Find jobs by employer.
- **GET** `/api/jobs/search`: Search for jobs by title.

## 🔒 Authentication
The API uses Spring Security session authentication. Cookies are required for Postman session persistence, and CSRF protection is enabled for state-changing requests.
Import [the Postman collection](./postman/Freelance-Job-Portal.postman_collection.json) and set its `email`, `password`, `name`, and `newPassword` collection variables before use. The collection is organized into Authentication, Users, Jobs, and Negative security checks.

1. Call `GET /api/auth/csrf` and retain the returned `token` and session cookie.
2. Send `POST /api/auth/login` as `application/x-www-form-urlencoded` with `email` and `password`, plus the CSRF header named by the response's `headerName`.
3. Keep the session cookie for subsequent requests. Login returns `204 No Content`; invalid credentials return `401`.
4. Include the current CSRF header and session cookie for POST, PUT, PATCH, and DELETE requests. Logout with `POST /api/auth/logout`.

New registrations always receive the `USER` role. Only an administrator can assign `EMPLOYER` or `ADMIN` using `PATCH /api/users/{id}/role` with `{"role":"EMPLOYER"}`.

To create the initial administrator, configure both `APP_SECURITY_BOOTSTRAP_ADMIN_EMAIL` and `APP_SECURITY_BOOTSTRAP_ADMIN_PASSWORD` in the environment before startup. The password must be 12-72 characters. Leave both unset when bootstrap is not needed. Never commit these values.

Job writes are restricted to `ADMIN` until jobs are linked to an employer account and ownership checks are implemented. The `EMPLOYER` role cannot yet manage job resources.

## 📦 Installation and Setup
1. Clone the repository: `git clone <repository-url>`
2. Navigate to the project directory: `cd Freelance-Job-Portal`
3. Install dependencies: `mvn install`
4. Run the application: `mvn spring-boot:run`

## 🤝 Contributions
Contributions are welcome! Please submit a pull request or open an issue for any suggestions or improvements.

## 👤 Authors
- [Arnava Kumar Sinha](https://github.com/ARNAVSINHA1)

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
