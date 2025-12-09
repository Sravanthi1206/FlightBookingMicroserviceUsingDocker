
# README - FlightApp Microservices (Kafka + Spring Cloud + Docker)

## FlightApp – Microservices-based Flight Booking Platform

FlightApp is a distributed microservices system built using Spring Boot, Spring Cloud, Eureka, Kafka, Docker, and JWT-based authentication. It supports functionalities such as authentication, flight search, flight management, ticket booking, and asynchronous email notifications.

---

## System Architecture

The architecture consists of the following services:

- Config-Service – Centralized configuration server
- Service-Registry (Eureka) – Service discovery for all microservices
- API-Gateway – Request routing, JWT validation, role-based authorization
- Auth-Service – User signup, login, JWT token generation
- Flight-Service – Flight CRUD and search operations
- Booking-Service – Ticket booking, lookup, and cancellation
- Email-Service – Kafka consumer that sends booking confirmation emails
- Kafka Cluster – Responsible for asynchronous event communication


---

## Security

### Authentication
JWT authentication is implemented in Auth-Service.

Header format:
Authorization: Bearer <token>

### Authorization
- USER role: Can perform booking operations
- ADMIN role: Can manage flights (create/update)

Unauthorized access:
- 401 Unauthorized – Missing or invalid token
- 403 Forbidden – Role not allowed

---

## Microservice Endpoints

### Auth-Service (Public)
POST /auth/signup – Register new user  
POST /auth/login – Authenticate user and return JWT  
POST /auth/logout – Stateless logout (token discarded on client side)

---

### Flight-Service
Public:
- GET /flights
- GET /flights/{id}
- POST /flights/search

Admin Only:
- POST /flights

---

### Booking-Service (User Only)
POST /bookings – Create booking  
GET /bookings/email/{email} – Get bookings by email  
GET /bookings/id/{id} – Get booking by ID  
GET /bookings/pnr/{pnr} – Get booking by PNR  
DELETE /bookings/{id} – Cancel booking

---

## Kafka Event Flow

1. Booking-Service publishes a "booking.created" event to Kafka.
2. Email-Service consumes the event from Kafka.
3. Email-Service sends confirmation emails.

---

## Docker Deployment

All services are containerized using Docker.  
A Docker Compose file orchestrates:
- Zookeeper
- Kafka
- Config-Service
- Eureka
- API-Gateway
- Auth-Service
- Flight-Service
- Booking-Service
- Email-Service

To run:
docker compose up --build

Screenshots included in Architecture Diagram.pdf.

---

## Performance Testing (JMeter)

Included tests:
- 20 request load test results
- 50 request load test results

Charts show performance metrics such as latency and throughput.

---

## Code Coverage (Jacoco)

Jacoco coverage reports included for:
- Flight-Service
- Booking-Service

Coverage exceeds 90%.

---

## Code Quality (SonarQube)

SonarQube reports included for:
- Flight-Service
- Booking-Service

Reports show reliability, maintainability, and security analysis.

---

## Technologies Used

- Spring Boot
- Spring Cloud Eureka
- Spring Cloud Gateway
- Spring Security (JWT)
- Apache Kafka
- Docker & Docker Compose
- MySQL/PostgreSQL
- JPA/Hibernate
- JMeter
- Jacoco
- SonarQube

---

## Running the System Manually (Without Docker)

1. Start Config-Service
mvn spring-boot:run

2. Start Eureka Server
mvn spring-boot:run

3. Start Kafka (if not using Docker)
Ensure Zookeeper and Kafka are running

4. Start remaining microservices:
API-Gateway  
Auth-Service  
Flight-Service  
Booking-Service  
Email-Service  

---

## Contributor
Sravanthi Gurram

