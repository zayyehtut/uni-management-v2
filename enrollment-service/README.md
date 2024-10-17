# Enrollment Service

## Overview

The Enrollment Service is responsible for managing student enrollments in courses. It provides APIs to create, retrieve, and delete enrollments, as well as manage available courses.

## Architecture Layers

- **Domain Layer**: Contains the core business logic and domain models, such as `Enrollment` and `AvailableCourse`.
- **Application Layer**: Contains the service interfaces and implementations, such as `EnrollmentService` and `EnrollmentServiceImpl`.
- **Infrastructure Layer**: Contains the repositories, clients, and messaging components, such as `EnrollmentRepository`, `AvailableCourseRepository`, `StudentClient`, and `EnrollmentConsumer`.
- **Presentation Layer**: Contains the REST controllers, such as `EnrollmentController` and `AvailableCourseController`, which expose the service's functionality via REST APIs.

## Real-time Analysis Implementation

The service implements real-time analysis by consuming Kafka events for course creation. When a new course is created, the service adds the course to the list of available courses.

```java
@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final AvailableCourseRepository availableCourseRepository;
    private final StudentClient studentClient;
    private final CourseClient courseClient;
    private final StreamBridge streamBridge;

    @Override
    public void addNewCourseToAvailableCourses(CourseDTO courseDTO) {
        AvailableCourse availableCourse = new AvailableCourse();
        availableCourse.setCourseId(courseDTO.getId());
        availableCourse.setCourseCode(courseDTO.getCourseCode());
        availableCourse.setCourseName(courseDTO.getName());

        availableCourseRepository.save(availableCourse);
        log.info("Added new course to available courses: {} (ID: {})", courseDTO.getName(), courseDTO.getId());
    }
}
```

## REST API Endpoints

### Create a New Enrollment

- **URL**: `POST /api/enrollments`
- **Linux `curl` command**:
  ```sh
  curl -X POST http://localhost:8083/api/enrollments -H "Content-Type: application/json" -d '{
      "studentId": 1,
      "courseId": 1,
      "enrollmentDate": "2023-10-01",
      "status": "ACTIVE"
  }'
  ```
- **Windows `curl` command**:
  ```sh
  curl -X POST http://localhost:8083/api/enrollments -H "Content-Type: application/json" -d "{\"studentId\":1,\"courseId\":1,\"enrollmentDate\":\"2023-10-01\",\"status\":\"ACTIVE\"}"
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "studentId": 1,
    "courseId": 1,
    "enrollmentDate": "2023-10-01",
    "status": "ACTIVE"
  }
  ```

### Retrieve Enrollment Details by ID

- **URL**: `GET /api/enrollments/{id}`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8083/api/enrollments/1
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8083/api/enrollments/1
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "studentId": 1,
    "courseId": 1,
    "enrollmentDate": "2023-10-01",
    "status": "ACTIVE"
  }
  ```

### Retrieve Enrollments for a Student by Student ID

- **URL**: `GET /api/enrollments/student/{studentId}`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8083/api/enrollments/student/1
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8083/api/enrollments/student/1
  ```
- **Sample Response**:
  ```json
  [
    {
      "id": 1,
      "studentId": 1,
      "courseId": 1,
      "enrollmentDate": "2023-10-01",
      "status": "ACTIVE"
    }
  ]
  ```

### Retrieve Enrollments for a Course by Course ID

- **URL**: `GET /api/enrollments/course/{courseId}`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8083/api/enrollments/course/1
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8083/api/enrollments/course/1
  ```
- **Sample Response**:
  ```json
  [
    {
      "id": 1,
      "studentId": 1,
      "courseId": 1,
      "enrollmentDate": "2023-10-01",
      "status": "ACTIVE"
    }
  ]
  ```

### Delete an Enrollment by ID

- **URL**: `DELETE /api/enrollments/{id}`
- **Linux `curl` command**:
  ```sh
  curl -X DELETE http://localhost:8083/api/enrollments/1
  ```
- **Windows `curl` command**:
  ```sh
  curl -X DELETE http://localhost:8083/api/enrollments/1
  ```
- **Sample Response**:
  ```json
  {}
  ```

### Retrieve All Enrollments

- **URL**: `GET /api/enrollments`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8083/api/enrollments
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8083/api/enrollments
  ```
- **Sample Response**:
  ```json
  [
    {
      "id": 1,
      "studentId": 1,
      "courseId": 1,
      "enrollmentDate": "2023-10-01",
      "status": "ACTIVE"
    }
  ]
  ```

### Retrieve All Available Courses

- **URL**: `GET /api/available-courses`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8083/api/available-courses
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8083/api/available-courses
  ```
- **Sample Response**:
  ```json
  [
    {
      "id": 1,
      "courseId": 1,
      "courseCode": "CS101",
      "courseName": "Introduction to Computer Science"
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
   cd enrollment-service
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

The service will start on port 8083.
