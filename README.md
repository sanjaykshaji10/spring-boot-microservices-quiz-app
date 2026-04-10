# 🎓 Quiz Microservices Ecosystem

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.1-blue)](https://spring.io/projects/spring-cloud)
[![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue)](https://www.postgresql.org/)

A distributed, secure Quiz Application built with **Spring Boot 3.2.5**. This ecosystem demonstrates a hardened microservices architecture featuring centralized identity management, reactive edge routing, and dynamic service discovery.

---

## 🏗️ System Architecture

The ecosystem is composed of five specialized services designed for domain isolation and secure scaling:

* **Eureka Server**: The "Phonebook." Provides centralized Service Discovery and Registration.
* **API Gateway**: The "Bouncer." Handles reactive routing, load balancing, and **JWT Validation**.
* **Auth Service**: The "Identity Provider." Manages user registration, credentials, and JWT issuance.
* **Question Service**: The "Content Manager." Handles the question bank and grading logic.
* **Quiz Service**: The "Orchestrator." Manages quiz sessions and coordinates with other services via OpenFeign.

---

## 🚦 Service Registry & Ports

| Service Name | Default Port | Primary Responsibility |
| :--- | :---: | :--- |
| **eureka-server** | `8761` | Naming Server / Service Registry |
| **api-gateway** | `8765` | **Secure Entry Point** & Discovery Locator |
| **auth-service** | `9898` | User Identity & JWT Generation |
| **question-service** | `8080` | Question CRUD & Grading Logic |
| **quiz-service** | `8090` | Quiz Session Orchestration |

---

## 🔐 Security & Authentication

The system implements a **Stateless JWT (JSON Web Token)** architecture to secure microservices:

1.  **Identity**: Users register or login via the `auth-service` to receive a signed JWT.
2.  **Interception**: The `api-gateway` uses a custom `AuthenticationGatewayFilterFactory` to intercept every incoming request.
3.  **Validation**: The Gateway validates the token signature and expiration before forwarding the request to internal services.
4.  **RBAC (Role-Based Access Control)**: Sensitive operations (e.g., adding questions) are restricted to users with the `ROLE_ADMIN` authority.

---

## 🛣️ API Endpoints (Via Gateway Port 8765)

All external communication must go through the API Gateway.

### 🔹 Auth Service (`/auth/`) - **Public**
| Method | Path | Description |
| :--- | :--- | :--- |
| `POST` | `/register` | Create a new user account. |
| `POST` | `/token` | Authenticate and receive a JWT Bearer Token. |

### 🔹 Question Service (`/questions/`) - **Secure**
| Method | Path | Description |
| :--- | :--- | :--- |
| `GET` | `/all` | Retrieve all questions (Requires Valid Token). |
| `GET` | `/category/{cat}` | Filter questions by category. |
| `POST` | `/add` | Add a new question (**Admin Only**). |

### 🔹 Quiz Service (`/quiz/`) - **Secure**
| Method | Path | Description |
| :--- | :--- | :--- |
| `POST` | `/create` | Create a new quiz session with a specific category. |
| `GET` | `/get/{id}` | Fetch quiz questions for a student (omits answers). |
| `POST` | `/submit/{id}` | Submit responses and receive a calculated score. |

---

## 🛠️ Technical Stack

* **Framework**: Spring Boot 3.2.5 (Stable Release).
* **Cloud Tools**: Spring Cloud 2023.0.1 (Netflix Eureka, Gateway, OpenFeign).
* **Security**: Spring Security & JJWT (0.12.5) for modern token handling.
* **Database**: PostgreSQL with dedicated schemas per service.
* **Build Tool**: Maven with Lombok for boilerplate reduction.

---

## 🚀 Getting Started

### **Order of Execution**
To ensure the services synchronize correctly, start them in this order:
1.  **Eureka Server** (Wait for Dashboard at `localhost:8761`).
2.  **Auth Service** (Must be up for the Gateway to validate secrets).
3.  **Question & Quiz Services**.
4.  **API Gateway**.

### **Installation & Build**
```bash
# Clone the repository
git clone [https://github.com/sanjaykshaji10/spring-boot-microservices-quiz-app.git](https://github.com/sanjaykshaji10/spring-boot-microservices-quiz-app.git)

# Build all modules
mvn clean install

# Run the API Gateway (Example)
cd api-gateway
./mvnw spring-boot:run