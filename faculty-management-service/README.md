# Faculty Management Service

## Overview

The Faculty Management Service is responsible for managing faculty information and course assignments. It provides APIs to create, update, and delete faculty records, as well as assign courses to faculty members.

## Architecture Layers

- **Domain Layer**: Contains the core business logic and domain models, such as `Faculty` and `CourseAssignment`.
- **Application Layer**: Contains the service interfaces and implementations, such as `FacultyService` and `FacultyServiceImpl`, and `CourseAssignmentService` and `CourseAssignmentServiceImpl`.
- **Infrastructure Layer**: Contains the repositories, clients, and messaging components, such as `FacultyRepository`, `CourseAssignmentRepository`, and `CourseClient`.
- **Presentation Layer**: Contains the REST controllers, such as `FacultyController` and `CourseAssignmentController`, which expose the service's functionality via REST APIs.

## REST API Endpoints

### Create a New Faculty Member

- **URL**: `POST /api/faculty`
- **Linux `curl` command**:
  ```sh
  curl -X POST http://localhost:8084/api/faculty -H "Content-Type: application/json" -d '{
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "department": "Computer Science",
      "position": "Professor",
      "specialization": "Artificial Intelligence"
  }'
  ```
- **Windows `curl` command**:
  ```sh
  curl -X POST http://localhost:8084/api/faculty -H "Content-Type: application/json" -d "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"department\":\"Computer Science\",\"position\":\"Professor\",\"specialization\":\"Artificial Intelligence\"}"
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "department": "Computer Science",
    "position": "Professor",
    "specialization": "Artificial Intelligence"
  }
  ```

### Retrieve Faculty Details by ID

- **URL**: `GET /api/faculty/{id}`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8084/api/faculty/1
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8084/api/faculty/1
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "department": "Computer Science",
    "position": "Professor",
    "specialization": "Artificial Intelligence"
  }
  ```

### Update Faculty Details by ID

- **URL**: `PUT /api/faculty/{id}`
- **Linux `curl` command**:
  ```sh
  curl -X PUT http://localhost:8084/api/faculty/1 -H "Content-Type: application/json" -d '{
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "department": "Computer Science",
      "position": "Associate Professor",
      "specialization": "Machine Learning"
  }'
  ```
- **Windows `curl` command**:
  ```sh
  curl -X PUT http://localhost:8084/api/faculty/1 -H "Content-Type: application/json" -d "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"department\":\"Computer Science\",\"position\":\"Associate Professor\",\"specialization\":\"Machine Learning\"}"
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "department": "Computer Science",
    "position": "Associate Professor",
    "specialization": "Machine Learning"
  }
  ```

### Delete a Faculty Member by ID

- **URL**: `DELETE /api/faculty/{id}`
- **Linux `curl` command**:
  ```sh
  curl -X DELETE http://localhost:8084/api/faculty/1
  ```
- **Windows `curl` command**:
  ```sh
  curl -X DELETE http://localhost:8084/api/faculty/1
  ```
- **Sample Response**:
  ```json
  {}
  ```

### Retrieve All Faculty Members

- **URL**: `GET /api/faculty`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8084/api/faculty
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8084/api/faculty
  ```
- **Sample Response**:
  ```json
  [
    {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "department": "Computer Science",
      "position": "Professor",
      "specialization": "Artificial Intelligence"
    }
  ]
  ```

### Retrieve Faculty Details by Email

- **URL**: `GET /api/faculty/email/{email}`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8084/api/faculty/email/john.doe@example.com
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8084/api/faculty/email/john.doe@example.com
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "department": "Computer Science",
    "position": "Professor",
    "specialization": "Artificial Intelligence"
  }
  ```

### Assign a Course to a Faculty Member

- **URL**: `POST /api/course-assignments`
- **Linux `curl` command**:
  ```sh
  curl -X POST http://localhost:8084/api/course-assignments -H "Content-Type: application/json" -d '{
      "faculty": {
        "id": 1
      },
      "courseId": 1,
      "semester": "Fall",
      "year": 2023
  }'
  ```
- **Windows `curl` command**:
  ```sh
  curl -X POST http://localhost:8084/api/course-assignments -H "Content-Type: application/json" -d "{\"faculty\":{\"id\":1},\"courseId\":1,\"semester\":\"Fall\",\"year\":2023}"
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "faculty": {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "department": "Computer Science",
      "position": "Professor",
      "specialization": "Artificial Intelligence"
    },
    "courseId": 1,
    "semester": "Fall",
    "year": 2023
  }
  ```

### Retrieve Course Assignments by Faculty ID

- **URL**: `GET /api/course-assignments/faculty/{facultyId}`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8084/api/course-assignments/faculty/1
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8084/api/course-assignments/faculty/1
  ```
- **Sample Response**:
  ```json
  [
    {
      "id": 1,
      "faculty": {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "email": "john.doe@example.com",
        "department": "Computer Science",
        "position": "Professor",
        "specialization": "Artificial Intelligence"
      },
      "courseId": 1,
      "semester": "Fall",
      "year": 2023
    }
  ]
  ```

### Retrieve Course Assignments by Course ID

- **URL**: `GET /api/course-assignments/course/{courseId}`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8084/api/course-assignments/course/1
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8084/api/course-assignments/course/1
  ```
- **Sample Response**:
  ```json
  [
    {
      "id": 1,
      "faculty": {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "email": "john.doe@example.com",
        "department": "Computer Science",
        "position": "Professor",
        "specialization": "Artificial Intelligence"
      },
      "courseId": 1,
      "semester": "Fall",
      "year": 2023
    }
  ]
  ```

### Delete a Course Assignment by ID

- **URL**: `DELETE /api/course-assignments/{id}`
- **Linux `curl` command**:
  ```sh
  curl -X DELETE http://localhost:8084/api/course-assignments/1
  ```
- **Windows `curl` command**:
  ```sh
  curl -X DELETE http://localhost:8084/api/course-assignments/1
  ```
- **Sample Response**:
  ```json
  {}
  ```

### Retrieve All Course Assignments

- **URL**: `GET /api/course-assignments`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8084/api/course-assignments
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8084/api/course-assignments
  ```
- **Sample Response**:
  ```json
  [
    {
      "id": 1,
      "faculty": {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "email": "john.doe@example.com",
        "department": "Computer Science",
        "position": "Professor",
        "specialization": "Artificial Intelligence"
      },
      "courseId": 1,
      "semester": "Fall",
      "year": 2023
    }
  ]
  ```

## Building and Running

### Prerequisites

- Java 21
- Maven
- Apache Kafka

### Build

1. Navigate to the service directory:
   ```sh
   cd faculty-management-service
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

The service will start on port 8084.
