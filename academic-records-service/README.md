# Academic Records Service

## Overview

The Academic Records Service is responsible for managing student academic records, including grades, transcripts, and GPA calculations. It provides APIs to add, update, and delete grades, as well as generate transcripts and semester reports.

## Architecture Layers

- **Domain Layer**: Contains the core business logic and domain models, such as `AcademicRecord` and `Grade`.
- **Application Layer**: Contains the service interfaces and implementations, such as `AcademicRecordService` and `AcademicRecordServiceImpl`.
- **Infrastructure Layer**: Contains the repositories, clients, and messaging components, such as `AcademicRecordRepository`, `StudentClient`, and `AcademicRecordsConsumer`.
- **Presentation Layer**: Contains the REST controllers, such as `AcademicRecordController`, which expose the service's functionality via REST APIs.

## Real-time Analysis Implementation

The service implements real-time analysis by consuming Kafka events for student creation and grade submissions. When a new student is created, the service initializes an academic record for the student. When a grade is submitted, the service updates the student's GPA and publishes a grade submitted event.

```java
@Service
@RequiredArgsConstructor
@Slf4j
public class AcademicRecordServiceImpl implements AcademicRecordService {

    private final AcademicRecordRepository academicRecordRepository;
    private final StudentClient studentClient;
    private final CourseClient courseClient;
    private final StreamBridge streamBridge;

    @Override
    @Transactional
    public AcademicRecord addGrade(Long studentId, Grade grade) {
        AcademicRecord academicRecord = getAcademicRecordByStudentId(studentId);
        validateCourse(grade.getCourseId());

        academicRecord.addGrade(grade);
        academicRecord.setGpa(calculateGPA(studentId));
        AcademicRecord savedRecord = academicRecordRepository.save(academicRecord);

        // Publish grade submitted event
        GradeSubmittedEvent event = new GradeSubmittedEvent();
        event.setCourseId(grade.getCourseId());
        event.setGrade(grade.getGrade());
        streamBridge.send("gradeSubmittedOutput-out-0", event);
        log.info("Grade submitted event published for student ID: {}", studentId);
        return savedRecord;
    }
}
```

## REST API Endpoints

### Retrieve Academic Record for a Student

- **URL**: `GET /api/academic-records/{studentId}`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8085/api/academic-records/1
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8085/api/academic-records/1
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "studentId": 1,
    "grades": [
      {
        "id": 1,
        "courseId": 1,
        "grade": "A",
        "semester": "Fall",
        "year": 2023
      }
    ],
    "gpa": 4.0
  }
  ```

### Add a Grade to a Student's Academic Record

- **URL**: `POST /api/academic-records/{studentId}/grades`
- **Linux `curl` command**:
  ```sh
  curl -X POST http://localhost:8085/api/academic-records/1/grades -H "Content-Type: application/json" -d '{
      "courseId": 2,
      "grade": "B",
      "semester": "Fall",
      "year": 2023
  }'
  ```
- **Windows `curl` command**:
  ```sh
  curl -X POST http://localhost:8085/api/academic-records/1/grades -H "Content-Type: application/json" -d "{\"courseId\":2,\"grade\":\"B\",\"semester\":\"Fall\",\"year\":2023}"
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "studentId": 1,
    "grades": [
      {
        "id": 1,
        "courseId": 1,
        "grade": "A",
        "semester": "Fall",
        "year": 2023
      },
      {
        "id": 2,
        "courseId": 2,
        "grade": "B",
        "semester": "Fall",
        "year": 2023
      }
    ],
    "gpa": 3.5
  }
  ```

### Update a Grade in a Student's Academic Record

- **URL**: `PUT /api/academic-records/{studentId}/grades/{gradeId}`
- **Linux `curl` command**:
  ```sh
  curl -X PUT http://localhost:8085/api/academic-records/1/grades/2 -H "Content-Type: application/json" -d '{
      "grade": "A",
      "semester": "Fall",
      "year": 2023
  }'
  ```
- **Windows `curl` command**:
  ```sh
  curl -X PUT http://localhost:8085/api/academic-records/1/grades/2 -H "Content-Type: application/json" -d "{\"grade\":\"A\",\"semester\":\"Fall\",\"year\":2023}"
  ```
- **Sample Response**:
  ```json
  {
    "id": 1,
    "studentId": 1,
    "grades": [
      {
        "id": 1,
        "courseId": 1,
        "grade": "A",
        "semester": "Fall",
        "year": 2023
      },
      {
        "id": 2,
        "courseId": 2,
        "grade": "A",
        "semester": "Fall",
        "year": 2023
      }
    ],
    "gpa": 4.0
  }
  ```

### Delete a Grade from a Student's Academic Record

- **URL**: `DELETE /api/academic-records/{studentId}/grades/{gradeId}`
- **Linux `curl` command**:
  ```sh
  curl -X DELETE http://localhost:8085/api/academic-records/1/grades/2
  ```
- **Windows `curl` command**:
  ```sh
  curl -X DELETE http://localhost:8085/api/academic-records/1/grades/2
  ```
- **Sample Response**:
  ```json
  {}
  ```

### Calculate and Retrieve the GPA for a Student

- **URL**: `GET /api/academic-records/{studentId}/gpa`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8085/api/academic-records/1/gpa
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8085/api/academic-records/1/gpa
  ```
- **Sample Response**:
  ```json
  4.0
  ```

### Generate and Retrieve the Transcript for a Student

- **URL**: `GET /api/academic-records/{studentId}/transcript`
- **Linux `curl` command**:
  ```sh
  curl -X GET http://localhost:8085/api/academic-records/1/transcript
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET http://localhost:8085/api/academic-records/1/transcript
  ```
- **Sample Response**:
  ```json
  {
    "studentId": 1,
    "studentName": "John Doe",
    "studentEmail": "john.doe@example.com",
    "cumulativeGPA": 4.0,
    "semesterReports": [
      {
        "semester": "Fall",
        "year": 2023,
        "semesterGPA": 4.0,
        "courseGrades": [
          {
            "courseId": 1,
            "courseCode": "CS101",
            "courseName": "Introduction to Computer Science",
            "grade": "A",
            "creditHours": 3,
            "department": "Computer Science"
          }
        ]
      }
    ]
  }
  ```

### Generate and Retrieve the Semester Report for a Student

- **URL**: `GET /api/academic-records/{studentId}/semester-report`
- **Linux `curl` command**:
  ```sh
  curl -X GET "http://localhost:8085/api/academic-records/1/semester-report?semester=Fall&year=2023"
  ```
- **Windows `curl` command**:
  ```sh
  curl -X GET "http://localhost:8085/api/academic-records/1/semester-report?semester=Fall&year=2023"
  ```
- **Sample Response**:
  ```json
  {
    "semester": "Fall",
    "year": 2023,
    "semesterGPA": 4.0,
    "courseGrades": [
      {
        "courseId": 1,
        "courseCode": "CS101",
        "courseName": "Introduction to Computer Science",
        "grade": "A",
        "creditHours": 3,
        "department": "Computer Science"
      }
    ]
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
   cd academic-records-service
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

The service will start on port 8085.
