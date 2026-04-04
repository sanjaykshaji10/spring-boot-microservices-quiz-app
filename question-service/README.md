# 🧩 Question Service (Microservice)

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)

The **Question Service** is a standalone microservice responsible for managing the entire lifecycle of quiz questions. It serves as the primary data provider for the Quiz Ecosystem, handling question storage, filtered retrieval, and secure score validation.

---

## 📑 Table of Contents
- [Architecture](#-architecture)
- [Tech Stack](#-tech-stack)
- [Endpoints](#-api-endpoints)
- [Data Models](#-data-models)
- [Setup & Installation](#-setup--installation)

---

## 🏛 Architecture
This service follows a decoupled **Microservice Architecture**. It is designed to work in tandem with a `Quiz-Service` (via FeignClient/RestTemplate). 

- **Domain Isolation:** Manages its own database schema for `Questions`.
- **DTO Projection:** Uses `QuestionWrapper` to prevent leaking correct answers to the frontend.
- **Stateless Logic:** Can be scaled horizontally across multiple ports.

---

## 🛠 Tech Stack
- **Framework:** Spring Boot 4.0.5
- **Language:** Java 17
- **Persistence:** Spring Data JPA (Jakarta EE 11)
- **Database:** PostgreSQL / MySQL (Configurable)
- **Utilities:** Lombok, Maven Wrapper

---

## 🛣 API Endpoints

### **1. Public / Admin Endpoints**
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/questions/all` | Returns a list of all questions (including answers). |
| `POST` | `/questions/add` | Adds a new question to the database. |
| `GET` | `/questions/category/{cat}` | Filter questions by category. |
| `GET` | `/questions/category/{cat}/{diff}` | Filter by category and difficulty. |

### **2. Microservice Internal Endpoints**
*Used for inter-service communication between Question and Quiz services.*

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/questions/generate` | Generates a list of random IDs based on category/count. |
| `POST` | `/questions/getQuestion` | Accepts `List<Integer>`, returns `List<QuestionWrapper>`. |
| `POST` | `/questions/getScore` | Accepts `List<Response>`, returns the final integer score. |

---

## 📦 Data Models

### **Response Object**
Used when a user submits their answers:
```json
{
    "questionId": 10,
    "selectedOption": "ArrayList"
}
