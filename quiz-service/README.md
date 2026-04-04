# 🏆 Quiz Service (Microservice)

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Feign](https://img.shields.io/badge/Spring%20Cloud-OpenFeign-blue)](https://spring.io/projects/spring-cloud-openfeign)
[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)

The **Quiz Service** acts as the orchestrator in the microservice ecosystem. It manages quiz metadata and coordinates complex workflows by communicating with the `Question-Service` via **OpenFeign**. It is responsible for quiz creation, session management, and result processing.

---

## 📑 Table of Contents
- [Architecture Overview](#-architecture-overview)
- [Tech Stack](#-tech-stack)
- [API Endpoints](#-api-endpoints)
- [Inter-Service Communication](#-inter-service-communication)
- [Data Models (DTOs)](#-data-models-dtos)
- [Setup & Installation](#-setup--installation)

---

## 🏛 Architecture Overview

The Quiz Service follows a **Decoupled Persistence** pattern. Instead of storing full question details, it only stores a `List<Integer>` of Question IDs. This ensures that the Question Service remains the "Single Source of Truth" for question content.

[Image of Microservices Orchestration Pattern]

1. **Orchestration**: Combines data from internal DB and external services.
2. **DTO Pattern**: Uses `QuizDto` for clean API inputs.
3. **Fault Tolerance**: Designed to work with Eureka for dynamic service discovery.

---

## 🛠 Tech Stack
- **Framework:** Spring Boot 4.0.5
- **Communication:** Spring Cloud OpenFeign
- **Discovery:** Netflix Eureka Client
- **Data:** Spring Data JPA + PostgreSQL/MySQL
- **Tooling:** Lombok, Jakarta Persistence

---

## 🛣 API Endpoints

### **Quiz Controller (`/quiz`)**

| Method | Endpoint | Request Body | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/createQuiz` | `QuizDto` | Calls Question-Service to generate IDs and saves a new Quiz. |
| `GET` | `/getQuiz/{quizId}` | N/A | Fetches a list of `QuestionWrapper` for a specific quiz. |
| `POST` | `/submit/{quizId}` | `List<Response>` | Submits student answers and returns the final score. |

---

## 🔗 Inter-Service Communication

This service uses the `QuizInterface` (Feign Client) to communicate with the `QUESTION-SERVICE` registered in Eureka.

```java
@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    // Generates a list of random question IDs
    @GetMapping("questions/generate")
    ResponseEntity<List<Integer>> generateQuiz(@RequestParam String category, @RequestParam Integer numQ);
    
    // Fetches question details (without answers) for the UI
    @PostMapping("questions/getQuestion")
    ResponseEntity<List<QuestionWrapper>> getQuestionsById(@RequestBody List<Integer> questionIds);

    // Securely calculates the score on the server side
    @PostMapping("questions/getScore")

}
