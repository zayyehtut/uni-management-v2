# Course Service

## Overview

The Course Service is responsible for managing course information, including course creation, updates, and analytics. It provides APIs to create, update, and delete courses, as well as retrieve course details and analytics.

## Architecture Layers

- **Domain Layer**: Contains the core business logic and domain models, such as `Course` and `CourseAnalytics`.
- **Application Layer**: Contains the service interfaces and implementations, such as `CourseService` and `CourseServiceImpl`.
- **Infrastructure Layer**: Contains the repositories, clients, and messaging components, such as `CourseRepository`, `CourseAnalyticsRepository`, and `CourseAnalyticsConsumer`.
- **Presentation Layer**: Contains the REST controllers, such as `CourseController`, which expose the service's functionality via REST APIs.

## Real-time Analysis Implementation

The service implements real-time analysis by consuming Kafka events for enrollment creation and grade submissions. When a new enrollment is created, the service updates the enrollment count for the course. When a grade is submitted, the service updates the average grade for the course.

```java
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseAnalyticsRepository analyticsRepository;
    private final StreamBridge streamBridge;

    @Override
    @Transactional
    public void updateEnrollmentCount(Long courseId) {
        CourseAnalytics analytics = getCourseAnalytics(courseId);
        analytics.setEnrollmentCount(analytics.getEnrollmentCount() + 1);
        analyticsRepository.save(analytics);
    }

    @Override
    @Transactional
    public void updateAverageGrade(Long courseId, String grade) {
        CourseAnalytics analytics = getCourseAnalytics(courseId);

        double gradeValue = convertGradeToNumeric(grade);
        double newAverage = (analytics.getAverageGrade() * analytics.getEnrollmentCount() + gradeValue)
                            / (analytics.getEnrollmentCount() + 1);
        analytics.setAverageGrade(newAverage);
        analyticsRepository.save(analytics);
    }
}
```

## REST API Endpoints

### Create a New Course

- **URL**: `POST /api/courses`
- **Linux `curl` command**:
  ```sh
  curl -X POST http://localhost:8082/api/courses -H "Content-Type: application/json" -d '{
      "courseCode": "CS101",
      "name": "Introduction to Computer Science",
      "description": "An introductory course to computer science.",
      "creditHours": 3,
      "department": "Computer Science"
  }'
  ```
- **Windows `curl` command**:
  ```sh
  curl -X POST http://localhost:8082/api/courses -H "Content-Type: application/json" -d "{\"courseCode\":\"CS101\",\"name\":\"Introduction to Computer Science\",\"description\":\"An introductory course to computer science.\",\"creditHours\":3,\"department\":\"Computer Science\"}"
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "courseCode": "CS101",
    "name": "Introduction to Computer Science",
    "description": "An introductory course to computer science.",
    "creditHours": 3,
    "department": "Computer Science"
  }
  ```

### Retrieve Course Details by ID

- **URL**: `GET /api/courses/{id}`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8082/api/courses/1
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8082/api/courses/1
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "courseCode": "CS101",
    "name": "Introduction to Computer Science",
    "description": "An introductory course to computer science.",
    "creditHours": 3,
    "department": "Computer Science"
  }
  ```

### Update Course Details by ID

- **URL**: `PUT /api/courses/{id}`
- **Linux `curl` command**:
  ```sh
  curl -X PUT http://localhost:8082/api/courses/1 -H "Content-Type: application/json" -d '{
      "courseCode": "CS101",
      "name": "Introduction to Computer Science",
      "description": "An updated introductory course to computer science.",
      "creditHours": 4,
      "department": "Computer Science"
  }'
  ```
- **Windows `curl` command**:
  ```sh
  curl -X PUT http://localhost:8082/api/courses/1 -H "Content-Type: application/json" -d "{\"courseCode\":\"CS101\",\"name\":\"Introduction to Computer Science\",\"description\":\"An updated introductory course to computer science.\",\"creditHours\":4,\"department\":\"Computer Science\"}"
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "courseCode": "CS101",
    "name": "Introduction to Computer Science",
    "description": "An updated introductory course to computer science.",
    "creditHours": 4,
    "department": "Computer Science"
  }
  ```

### Delete a Course by ID

- **URL**: `DELETE /api/courses/{id}`
- **Linux `curl` command**:
  ```sh
  curl -X DELETE http://localhost:8082/api/courses/1
  ```
- **Windows `curl` command**:
  ```sh
  curl -X DELETE http://localhost:8082/api/courses/1
  ```
- **Sample Response**:
  ```json
  {}
  ```

### Retrieve All Courses

- **URL**: `GET /api/courses`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8082/api/courses
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8082/api/courses
  ```
- **Sample Response**:
  ```json
  [
    {
      "id": 1,
      "courseCode": "CS101",
      "name": "Introduction to Computer Science",
      "description": "An introductory course to computer science.",
      "creditHours": 3,
      "department": "Computer Science"
    },
    {
      "id": 2,
      "courseCode": "MATH101",
      "name": "Introduction to Mathematics",
      "description": "An introductory course to mathematics.",
      "creditHours": 4,
      "department": "Mathematics"
    }
  ]
  ```

### Retrieve Course Details by Course Code

- **URL**: `GET /api/courses/code/{courseCode}`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8082/api/courses/code/CS101
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8082/api/courses/code/CS101
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "courseCode": "CS101",
    "name": "Introduction to Computer Science",
    "description": "An introductory course to computer science.",
    "creditHours": 3,
    "department": "Computer Science"
  }
  ```

### Retrieve Course Analytics by ID

- **URL**: `GET /api/courses/{id}/analytics`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8082/api/courses/1/analytics
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8082/api/courses/1/analytics
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "courseId": 1,
    "enrollmentCount": 10,
    "averageGrade": 3.5
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
   cd course-service
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

The service will start on port 8082.
