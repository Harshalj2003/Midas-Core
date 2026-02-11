# Midas Core

Project repo for the JPMC Advanced Software Engineering Forage program

## Project Description

Midas Core is a robust Spring Boot microservice application designed for transaction processing and event-driven architecture. This project demonstrates enterprise-grade software engineering practices including REST API development, event streaming with Kafka, database management with JPA/Hibernate, and comprehensive testing strategies.

### Purpose

The core purpose of this application is to:
- Process and manage transactions through a scalable REST API
- Implement event-driven architecture using Apache Kafka for asynchronous transaction handling
- Provide secure endpoints with Spring Security integration
- Maintain data persistence with H2 database and JPA/Hibernate ORM
- Enable comprehensive logging and monitoring capabilities

## Technology Stack

- **Framework**: Spring Boot 3.2.5
- **Language**: Java 17
- **Build Tool**: Maven
- **Database**: H2 (In-memory/File-based)
- **ORM**: Spring Data JPA with Hibernate
- **Message Broker**: Apache Kafka 3.1.4
- **Security**: Spring Security
- **Testing**: JUnit 5, Spring Boot Test, Testcontainers
- **Logging**: SLF4J with Spring Boot Logging
- **Additional Tools**: Lombok, DevTools, Docker Compose Support

## Setup Instructions

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Apache Kafka 3.x (for running locally)
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Harshalj2003/Midas-Core.git
   cd Midas-Core
