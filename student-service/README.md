# Student Service

## Overview

The Student Service is responsible for managing student information, including personal details, enrollment status, and academic records. It provides APIs to create, update, and delete student records, as well as retrieve student details.

## Architecture Layers

- **Domain Layer**: Contains the core business logic and domain models, such as `Student`.
- **Application Layer**: Contains the service interfaces and implementations, such as `StudentService` and `StudentServiceImpl`.
- **Infrastructure Layer**: Contains the repositories and messaging components, such as `StudentRepository` and `StreamBridge`.
- **Presentation Layer**: Contains the REST controllers, such as `StudentController`, which expose the service's functionality via REST APIs.

## Real-time Analysis Implementation

The service implements real-time analysis by publishing Kafka events for student creation. When a new student is created, the service publishes a student created event.

```java
@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StreamBridge streamBridge;

    @Override
    public Student createStudent(Student student) {
        Student savedStudent = studentRepository.save(student);
        streamBridge.send("studentCreatedOutput-out-0", savedStudent);
        log.info("Student created event sent: {}", savedStudent);
        return savedStudent;
    }
}
```

## REST API Endpoints

### Create a New Student

- **URL**: `POST /api/students`
- **Linux `curl` command**:
  ```sh
  curl -X POST http://localhost:8081/api/students -H "Content-Type: application/json" -d '{
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "dateOfBirth": "1990-01-01",
      "address": "123 Main St",
      "phoneNumber": "123-456-7890"
  }'
  ```
- **Windows `curl` command**:
  ```sh
  curl -X POST http://localhost:8081/api/students -H "Content-Type: application/json" -d "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"dateOfBirth\":\"1990-01-01\",\"address\":\"123 Main St\",\"phoneNumber\":\"123-456-7890\"}"
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "dateOfBirth": "1990-01-01",
    "address": "123 Main St",
    "phoneNumber": "123-456-7890"
  }
  ```

### Retrieve Student Details by ID

- **URL**: `GET /api/students/{id}`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8081/api/students/1
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8081/api/students/1
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "dateOfBirth": "1990-01-01",
    "address": "123 Main St",
    "phoneNumber": "123-456-7890"
  }
  ```

### Update Student Details by ID

- **URL**: `PUT /api/students/{id}`
- **Linux `curl` command**:
  ```sh
  curl -X PUT http://localhost:8081/api/students/1 -H "Content-Type: application/json" -d '{
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "dateOfBirth": "1990-01-01",
      "address": "456 Elm St",
      "phoneNumber": "123-456-7890"
  }'
  ```
- **Windows `curl` command**:
  ```sh
  curl -X PUT http://localhost:8081/api/students/1 -H "Content-Type: application/json" -d "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"dateOfBirth\":\"1990-01-01\",\"address\":\"456 Elm St\",\"phoneNumber\":\"123-456-7890\"}"
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "dateOfBirth": "1990-01-01",
    "address": "456 Elm St",
    "phoneNumber": "123-456-7890"
  }
  ```

### Delete a Student by ID

- **URL**: `DELETE /api/students/{id}`
- **Linux `curl` command**:
  ```sh
  curl -X DELETE http://localhost:8081/api/students/1
  ```
- **Windows `curl` command**:
  ```sh
  curl -X DELETE http://localhost:8081/api/students/1
  ```
- **Sample Response**:
  ```json
  {}
  ```

### Retrieve All Students

- **URL**: `GET /api/students`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8081/api/students
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8081/api/students
  ```
- **Sample Response**:
  ```json
  [
    {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "dateOfBirth": "1990-01-01",
      "address": "123 Main St",
      "phoneNumber": "123-456-7890"
    },
    {
      "id": 2,
      "firstName": "Jane",
      "lastName": "Doe",
      "email": "jane.doe@example.com",
      "dateOfBirth": "1992-02-02",
      "address": "789 Oak St",
      "phoneNumber": "987-654-3210"
    }
  ]
  ```

### Retrieve Student Details by Email

- **URL**: `GET /api/students/email/{email}`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8081/api/students/email/john.doe@example.com
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8081/api/students/email/john.doe@example.com
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "dateOfBirth": "1990-01-01",
    "address": "123 Main St",
    "phoneNumber": "123-456-7890"
  }
  ```

## Building and Running

### Prerequisites

- Java 21
- Maven
- Apache Kafka

### Build

1. Navigate to the service directory:
   ```sh
   cd student-service
   ```
2. Build the project using Maven:
   ```sh
   mvn clean install
   ```

### Run

1. Start the Kafka server:
   ```sh
   bin/zookeeper-server-start.sh config/zookeeper.properties
   bin/kafka-server-start.sh config/server.properties
   ```
2. Run the service:
   ```sh
   mvn spring-boot:run
   ```

The service will start on port 8081.
