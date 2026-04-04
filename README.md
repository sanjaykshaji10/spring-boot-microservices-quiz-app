# 🎓 Quiz Microservices Ecosystem

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)

A distributed, scalable Quiz Application built with **Spring Boot 4.0.5**. This project demonstrates a production-grade microservices architecture, featuring centralized service discovery, reactive API routing, and secure inter-service communication.

---

## 🏗️ System Architecture

The ecosystem is composed of four specialized services designed for high availability and domain isolation:

* **Eureka Server**: The "Phonebook" of the system. Handles Service Discovery and Registration.
* **API Gateway**: The "Entry Point." Provides reactive routing, load balancing, and security.
* **Question Service**: The "Content Manager." Handles the question bank and scoring logic.
* **Quiz Service**: The "Orchestrator." Manages quiz sessions and coordinates with other services via Feign.

---

## 🚦 Service Registry & Ports

| Service Name | Default Port | Primary Responsibility |
| :--- | :---: | :--- |
| **eureka-server** | `8761` | Naming Server / Service Registry |
| **api-gateway** | `8765` | API Routing & Discovery Locator |
| **question-service** | `8080` | Question CRUD & Grading Logic |
| **quiz-service** | `8090` | Quiz Session Orchestration |

---

## 🛣️ API Endpoints (Via Gateway)

Since the **API Gateway** acts as a proxy, all requests should be directed to port `8765`.

### 🔹 Question Service (`/question-service/questions/`)
| Method | Path | Description |
| :--- | :--- | :--- |
| `GET` | `/all` | Retrieve all questions in the database. |
| `GET` | `/category/{cat}` | Filter questions by category. |
| `POST` | `/add` | Add a new question to the ecosystem. |
| `GET` | `/generate` | **Internal:** Generates a list of random IDs. |
| `POST` | `/getScore` | **Internal:** Securely calculates results. |

### 🔹 Quiz Service (`/quiz-service/quiz/`)
| Method | Path | Description |
| :--- | :--- | :--- |
| `POST` | `/createQuiz` | Creates a new quiz session (requires `QuizDto`). |
| `GET` | `/getQuiz/{id}` | Fetches questions for a student (without answers). |
| `POST` | `/submit/{id}` | Submits answers and returns the final score. |

---

## 🛠️ Technical Implementation

* **Service Discovery**: Services dynamically register with **Eureka**, allowing for horizontal scaling without hardcoding IPs.
* **Reactive Routing**: **Spring Cloud Gateway** uses a non-blocking engine to manage high-concurrency traffic.
* **Declarative Clients**: **OpenFeign** simplifies inter-service calls, making them look like local method calls.
* **Domain Isolation**: Each service manages its own database schema, ensuring **Single Responsibility**.

---

## 🚀 Getting Started

### **Order of Execution**
To ensure the services find each other correctly, start them in this specific order:

1.  **Eureka Server** (Wait for Dashboard at `localhost:8761`)
2.  **Question Service**
3.  **Quiz Service**
4.  **API Gateway**

### **Installation**
```bash
# Clone the repository
git clone [https://github.com/sanjaykshaji10/spring-boot-microservices-quiz-app.git](https://github.com/sanjaykshaji10/spring-boot-microservices-quiz-app.git)

# Navigate to a service folder
cd question-service

# Build and Run
./mvnw spring-boot:run