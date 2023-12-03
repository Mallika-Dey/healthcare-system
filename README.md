
# Healthcare Management System

Healthcare Management System is a comprehensive system designed to streamline patient care, doctor scheduling, consultation, pharmaceutical inventory, and more. Built using Spring Boot, this application leverages resilience4j for fault tolerance, Feign client for declarative REST client, and WebSocket for real-time notifications, among other advanced features.

  

## Technologies

  

### Spring Boot

  

Spring Boot serves as the foundational framework for developing microservices. Its capabilities expedite the creation of robust and scalable backend services.

  

### JPA (Java Persistence API)

  

JPA facilitates data persistence and interaction with a relational database. Entities representing user profiles, health data, recommendations, appointment scheduling, notifications and community interactions are defined and managed using JPA.

  

### Discovery Server

  

The Discovery Server manages service registration and discovery for microservices. It enables dynamic scaling and load balancing, ensuring system reliability.

  

### API Gateway

  

For handling API requests and routing them to appropriate services.

  

### MySQL Database

  

Other microservices utilize MySQL for efficient data storage and retrieval.

  

### WebClient and Auto-trigger Retry Mechanism

  

Microservices communicate with each other using WebClient, enabling efficient microservice-to-microservice communication. An auto-trigger retry mechanism ensures data consistency.

  

### Feign Client

Utilizes Feign for streamlined and efficient inter-service communication.

  

### Circuit Breaker and Fallback Methods

  

The system incorporates a circuit breaker and fallback methods to enhance fault tolerance and resilience.

  

### WebSocket

For real-time user specific notifications.

  

### Security Service

  

The Authentication Service handles user registration, login. Secure user authentication and access token generation are implemented.

  

### Microservices Architecture

  

This system is built on a microservices architecture, ensuring modularity and scalability. Each microservice addresses specific aspects of data management.

![Healthcare System Architecture](https://github.com/MallikaBJIT/images/blob/main/Architecture.png)

  
  

## Getting Started

  

To get started with the Healthcare Management System, follow these steps:

  

1. Clone the repository:

```bash

git clone https://github.com/BJITAcademy2021/YSD_B03_J2EE_FinalProject_Mallika.git

cd YSD_B03_J2EE_FinalProject_Mallika
