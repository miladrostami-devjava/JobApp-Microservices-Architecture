# JobApp Microservices Architecture 🚀

**JobApp-Microservices** is a robust, distributed job application management system built with **Spring Boot 3** and **Java 21**. It demonstrates a production-grade microservices architecture featuring inter-service communication, event-driven consistency, fault tolerance, and centralized configuration.

This project has been refactored to support **WSL2 (Windows Subsystem for Linux)** environments and includes specific configurations for **Resilience4j** and **RabbitMQ** security.



## 🇬🇧 English Version (Business Overview)
Business Overview

JobApp is a comprehensive platform designed to streamline the connection between job seekers and employers. 
The system manages three core business domains: Companies, Job Postings, and Reviews. 
It allows organizations to list job opportunities while enabling candidates to provide feedback on their interview or work experiences.
A key business logic of this platform is its real-time, event-driven reputation system; whenever a review is submitted or modified, 
the respective company's aggregate rating is instantly recalculated and updated. This ensures transparency and trust within the hiring ecosystem,
helping job seekers make informed decisions based on up-to-date company metrics.



## 🇮🇷 نسخه فارسی (بررسی بیزینس)
بررسی اجمالی کسب‌ و کار

پروژه JobApp یک پلتفرم جامع برای مدیریت فرآیندهای کاریابی و استخدام است که به عنوان پل ارتباطی میان کارفرمایان
و کارجویان عمل می‌کند. هسته اصلی این کسب‌ و کار بر سه محور مدیریت شرکت‌ها، آگهی‌های شغلی و نقد و بررسی‌ها استوار است. در این سیستم،
کارفرمایان می‌توانند فرصت‌های شغلی خود را منتشر کنند و کارجویان نیز تجربیات مصاحبه یا همکاری خود را در قالب نقد و امتیاز ثبت می‌نمایند.
ویژگی متمایز و هوشمند این پروژه، مکانیزم محاسبه بلادرنگ اعتبار شرکت‌هاست؛ به طوری که با ثبت هر بازخورد جدید،
میانگین امتیاز شرکت از طریق یک معماری رویداد-محور به‌روزرسانی می‌شود تا شفافیت و اعتماد در اکوسیستم استخدام تضمین گردد.








---

## 🏗 System Architecture & Features

### Core Services
| Service | Port | Description |
| :--- | :--- | :--- |
| **Service Registry (Eureka)** | `8761` | Service discovery and registration server. |
| **Config Server** | `8080` | Centralized external configuration for all services. |
| **Company Service** | `8081` | Manages companies and aggregates data from Job/Review services. |
| **Job Service** | `8082` | Handles CRUD operations for job postings. |
| **Review Service** | `8083` | Manages reviews and ratings. |
| **API Gateway** | `8084` | Unified entry point for routing and filtering. |

### Key Features
* **Synchronous Communication:** Uses `OpenFeign` for RESTful calls between microservices.
* **Event-Driven Architecture:** Uses **RabbitMQ** for asynchronous tasks (e.g., updating company ratings when a review is added/deleted).
* **Fault Tolerance:** Implements **Resilience4j** (Circuit Breaker, Rate Limiter, Retry) to handle downstream failures gracefully.
* **Distributed Tracing:** Integrated with **Zipkin** and **Micrometer** for request tracking.
* **Database:** H2 In-Memory database for development (Dev profile).

---

## 🛠 Tech Stack

* **Language:** Java 21 ☕
* **Framework:** Spring Boot 3.x, Spring Cloud
* **Messaging:** RabbitMQ (Dockerized)
* **Resilience:** Resilience4j (Circuit Breaker, Retry, Rate Limiter)
* **Discovery:** Netflix Eureka
* **Tracing:** Zipkin, Micrometer Tracing
* **Build Tool:** Maven

---

## ⚙️ Setup & Installation

### Prerequisites
* **Java JDK 21** installed.
* **Docker Desktop** (running on Windows/Linux/Mac).
* **Maven** installed.

### 1. Infrastructure Setup (Docker) 🐳
Before running the Java applications, you must start the infrastructure services. This project uses specific credentials (`user`/`password`) for RabbitMQ security.

Run the following commands in your terminal:

```bash
# 1. Start RabbitMQ (Messaging Broker)
# Note: Custom credentials are required as configured in application.properties
docker run -d --name rabbitmq \
  -p 5672:5672 -p 15672:15672 \
  -e RABBITMQ_DEFAULT_USER=user \
  -e RABBITMQ_DEFAULT_PASS=password \
  rabbitmq:3-management

# 2. Start Zipkin (Distributed Tracing)
docker run -d --name zipkin -p 9411:9411 openzipkin/zipkin

### 2. Running the Microservices (Local Dev)
The services must be started in the specific order below to ensure dependencies (Config/Eureka) are available.

**Active Profile:** `dev`

1.  **Service Registry**
    ```bash
    cd service_registry && mvn spring-boot:run -Dspring-boot.run.profiles=dev
    ```

2.  **Config Server**
    ```bash
    cd config_server && mvn spring-boot:run -Dspring-boot.run.profiles=dev
    ```

3.  **Company Microservice** (Dependent on RabbitMQ & Config Server)
    ```bash
    cd companyms && mvn spring-boot:run -Dspring-boot.run.profiles=dev
    ```

4.  **Job Microservice**
    ```bash
    cd jobms && mvn spring-boot:run -Dspring-boot.run.profiles=dev
    ```

5.  **Review Microservice**
    ```bash
    cd reviewms && mvn spring-boot:run -Dspring-boot.run.profiles=dev
    ```

6.  **API Gateway**
    ```bash
    cd api_gateway && mvn spring-boot:run -Dspring-boot.run.profiles=dev
    ```

---

## 🔧 Important Configuration Notes

### RabbitMQ Credentials
The application is configured to connect to RabbitMQ using secure credentials, not the default guest/guest.

* **Username:** `user`
* **Password:** `password`
* **Config file:** `application.properties`

### Bean Circular Dependency Fix
The project uses `@Lazy` injection in `CompanyServiceImpl` to resolve circular dependencies between beans involved in Resilience4j proxies (Circuit Breaker) and the service layer.

### WSL2 Port Conflicts (Troubleshooting)
If you encounter `Bind for 0.0.0.0:5672 failed: port is already allocated` on Windows/WSL2:

1.  Check for zombie processes in Windows PowerShell:
    ```powershell
    Get-NetTCPConnection -LocalPort 5672
    ```
2.  Restart Docker Desktop.
3.  In extreme cases, restart WSL: `wsl --shutdown`.

---

## 🧪 Testing endpoints

Once all services are up, you can access the **Eureka Dashboard** to verify service health:
👉 [http://localhost:8761](http://localhost:8761)

### Sample API Requests (via Gateway Port 8084)

* **Create Company:** `POST /companies`
* **Get All Companies:** `GET /companies`
* **Create Job:** `POST /jobs`
* **Get Jobs for Company:** `GET /jobs?companyId={id}`

---

## 📝 License
This project is open-source and available under the MIT License.