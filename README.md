# University Management System

## Overview

The University Management System is a comprehensive platform designed to manage various aspects of university operations, including student records, course management, enrollment, faculty assignments, and academic records. The system is built using microservices architecture, ensuring scalability, maintainability, and flexibility.

## Architecture

The system is designed with a microservices architecture, where each service is responsible for a specific domain, such as student management, course management, enrollment, faculty management, and academic records. These services communicate with each other through REST APIs and message queues, ensuring loose coupling and high cohesion.

## Technology Stack

- **Spring Boot**
- **Spring Cloud Stream**
- **Apache Kafka**
- **H2 Database**
- **Maven**
- **Lombok**

### Implementation Framework

**Spring Boot** is used as the primary framework for building the microservices. It provides a robust and efficient way to develop production-ready applications quickly. **Spring Cloud Stream** is utilized for integrating the microservices with Apache Kafka, enabling seamless communication between services.

### Messaging System

**Apache Kafka** is chosen as the messaging system for its high throughput, fault tolerance, and scalability. It allows the system to handle real-time data feeds and ensures reliable communication between microservices.

## Services

1. **Student Service**
   - Manages student information, including personal details, enrollment status, and academic records.
2. **Course Service**
   - Handles course creation, management, and analytics.
3. **Enrollment Service**
   - Manages student enrollments in courses.
4. **Faculty Management Service**
   - Handles faculty information and course assignments.
5. **Academic Records Service**
   - Manages academic records, including grades, transcripts, and GPA calculations.

## Getting Started

### Prerequisites

- Java 21
- Maven
- Apache Kafka

### Build and Install

1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/university-management-system.git
   cd university-management-system
   ```
2. Build the project using Maven:
   ```sh
   mvn clean install
   ```

### Apache Kafka Setup

1. Download and install Apache Kafka from the official website.
2. Start the Kafka server:
   ```sh
   bin/zookeeper-server-start.sh config/zookeeper.properties
   bin/kafka-server-start.sh config/server.properties
   ```

### Run the Application

1. Navigate to each service directory and run the following command:
   ```sh
   mvn spring-boot:run
   ```
2. The services will start on the following ports:
   - Student Service: 8081
   - Course Service: 8082
   - Enrollment Service: 8083
   - Faculty Management Service: 8084
   - Academic Records Service: 8085

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
