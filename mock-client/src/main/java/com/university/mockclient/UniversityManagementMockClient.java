package com.university.mockclient;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.JsonProcessingException;

import static com.university.mockclient.PrettyUtil.*;

public class UniversityManagementMockClient {
    private static final Scanner scanner = new Scanner(System.in);
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    
    private static final String STUDENT_SERVICE_URL = "http://localhost:8081/api/students";
    private static final String COURSE_SERVICE_URL = "http://localhost:8082/api/courses";
    private static final String ENROLLMENT_SERVICE_URL = "http://localhost:8083/api/enrollments";
    private static final String FACULTY_SERVICE_URL = "http://localhost:8084/api/faculty";
    private static final String ACADEMIC_RECORDS_SERVICE_URL = "http://localhost:8085/api/academic-records";

    public static void main(String[] args) {
        while (true) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1: newStudentCreation(); break;
                case 2: updateStudentInformation(); break;
                case 3: deleteStudent(); break;
                case 4: getStudentInformation(); break;
                case 5: newCourseCreation(); break;
                case 6: updateCourseInformation(); break;
                case 7: deleteCourse(); break;
                case 8: getCourseInformation(); break;
                case 9: enrollStudentInCourse(); break;
                case 10: getEnrollmentInformation(); break;
                case 11: newFacultyCreation(); break;
                case 12: updateFacultyInformation(); break;
                case 13: deleteFaculty(); break;
                case 14: getFacultyInformation(); break;
                case 15: assignCourseToFaculty(); break;
                case 16: getCourseAssignmentInformation(); break;
                case 17: addGrade(); break;
                case 18: updateGrade(); break;
                case 19: deleteGrade(); break;
                case 20: getStudentGrades(); break;
                case 21: calculateGPA(); break;
                case 22: generateTranscript(); break;
                case 23: generateSemesterReport(); break;
                case 24: runAutomaticWorkflow(); break;
                case 25: System.out.println("Exiting the program. Goodbye!"); System.exit(0);
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMainMenu() {
        printBox("University Management Mock Client", 40);
        printMenuItem(1, "New Student Creation");
        printMenuItem(2, "Update Student Information");
        printMenuItem(3, "Delete Student");
        printMenuItem(4, "Get Student Information");
        printMenuItem(5, "New Course Creation");
        printMenuItem(6, "Update Course Information");
        printMenuItem(7, "Delete Course");
        printMenuItem(8, "Get Course Information");
        printMenuItem(9, "Enroll Student in Course");
        printMenuItem(10, "Get Enrollment Information");
        printMenuItem(11, "New Faculty Creation");
        printMenuItem(12, "Update Faculty Information");
        printMenuItem(13, "Delete Faculty");
        printMenuItem(14, "Get Faculty Information");
        printMenuItem(15, "Assign Course to Faculty");
        printMenuItem(16, "Get Course Assignment Information");
        printMenuItem(17, "Add Grade");
        printMenuItem(18, "Update Grade");
        printMenuItem(19, "Delete Grade");
        printMenuItem(20, "Get Student Grades");
        printMenuItem(21, "Calculate GPA");
        printMenuItem(22, "Generate Transcript");
        printMenuItem(23, "Generate Semester Report");
        printMenuItem(24, "Run Automatic Workflow");
        printMenuItem(25, "Exit");
        printBoxBottom(40);
    }

    // Student Service Methods
    private static void newStudentCreation() {
        Map<String, Object> studentData = new HashMap<>();
        studentData.put("firstName", getStringInput("Enter first name: "));
        studentData.put("lastName", getStringInput("Enter last name: "));
        studentData.put("email", getStringInput("Enter email: "));
        studentData.put("dateOfBirth", getDateInput("Enter date of birth (YYYY-MM-DD): "));
        studentData.put("address", getStringInput("Enter address: "));
        studentData.put("phoneNumber", getStringInput("Enter phone number: "));

        String response = sendRequest(HttpMethod.POST, STUDENT_SERVICE_URL, studentData);
        printSuccess("Student created successfully:");
        System.out.println(response);
        simulateKafkaEvent("student-created-topic", response);
    }

    private static void updateStudentInformation() {
        Long studentId = getLongInput("Enter student ID to update: ");
        Map<String, Object> studentData = new HashMap<>();
        studentData.put("firstName", getStringInput("Enter new first name: "));
        studentData.put("lastName", getStringInput("Enter new last name: "));
        studentData.put("email", getStringInput("Enter new email: "));
        studentData.put("dateOfBirth", getDateInput("Enter new date of birth (YYYY-MM-DD): "));
        studentData.put("address", getStringInput("Enter new address: "));
        studentData.put("phoneNumber", getStringInput("Enter new phone number: "));

        String response = sendRequest(HttpMethod.PUT, STUDENT_SERVICE_URL + "/" + studentId, studentData);
        printSuccess("Student updated successfully:");
        System.out.println(response);
    }

    private static void deleteStudent() {
        Long studentId = getLongInput("Enter student ID to delete: ");
        String response = sendRequest(HttpMethod.DELETE, STUDENT_SERVICE_URL + "/" + studentId, null);
        printSuccess("Student deleted successfully.");
    }

    private static void getStudentInformation() {
        Long studentId = getLongInput("Enter student ID: ");
        String response = sendRequest(HttpMethod.GET, STUDENT_SERVICE_URL + "/" + studentId, null);
        printSuccess("Student information:");
        System.out.println(response);
    }

    // Course Service Methods
    private static void newCourseCreation() {
        Map<String, Object> courseData = new HashMap<>();
        courseData.put("courseCode", getStringInput("Enter course code: "));
        courseData.put("name", getStringInput("Enter course name: "));
        courseData.put("description", getStringInput("Enter course description: "));
        courseData.put("creditHours", getIntInput("Enter credit hours: "));
        courseData.put("department", getStringInput("Enter department: "));

        String response = sendRequest(HttpMethod.POST, COURSE_SERVICE_URL, courseData);
        printSuccess("Course created successfully:");
        System.out.println(response);
        simulateKafkaEvent("course-created-topic", response);
    }

    private static void updateCourseInformation() {
        Long courseId = getLongInput("Enter course ID to update: ");
        Map<String, Object> courseData = new HashMap<>();
        courseData.put("courseCode", getStringInput("Enter new course code: "));
        courseData.put("name", getStringInput("Enter new course name: "));
        courseData.put("description", getStringInput("Enter new course description: "));
        courseData.put("creditHours", getIntInput("Enter new credit hours: "));
        courseData.put("department", getStringInput("Enter new department: "));

        String response = sendRequest(HttpMethod.PUT, COURSE_SERVICE_URL + "/" + courseId, courseData);
        printSuccess("Course updated successfully:");
        System.out.println(response);
    }

    private static void deleteCourse() {
        Long courseId = getLongInput("Enter course ID to delete: ");
        String response = sendRequest(HttpMethod.DELETE, COURSE_SERVICE_URL + "/" + courseId, null);
        printSuccess("Course deleted successfully.");
    }

    private static void getCourseInformation() {
        Long courseId = getLongInput("Enter course ID: ");
        String response = sendRequest(HttpMethod.GET, COURSE_SERVICE_URL + "/" + courseId, null);
        printSuccess("Course information:");
        System.out.println(response);
    }

    // Enrollment Service Methods
    private static void enrollStudentInCourse() {
        Map<String, Object> enrollmentData = new HashMap<>();
        enrollmentData.put("studentId", getLongInput("Enter student ID: "));
        enrollmentData.put("courseId", getLongInput("Enter course ID: "));
        enrollmentData.put("enrollmentDate", LocalDate.now()); 

        String response = sendRequest(HttpMethod.POST, ENROLLMENT_SERVICE_URL, enrollmentData);
        printSuccess("Enrollment created successfully:");
        System.out.println(response);
    }

    private static void getEnrollmentInformation() {
        Long studentId = getLongInput("Enter student ID: ");
        String response = sendRequest(HttpMethod.GET, ENROLLMENT_SERVICE_URL + "/student/" + studentId, null);
        printSuccess("Enrollment information:");
        System.out.println(response);
    }

    // Faculty Management Service Methods
    private static void newFacultyCreation() {
        Map<String, Object> facultyData = new HashMap<>();
        facultyData.put("firstName", getStringInput("Enter first name: "));
        facultyData.put("lastName", getStringInput("Enter last name: "));
        facultyData.put("email", getStringInput("Enter email: "));
        facultyData.put("department", getStringInput("Enter department: "));
        facultyData.put("position", getStringInput("Enter position: "));
        facultyData.put("specialization", getStringInput("Enter specialization: "));

        String response = sendRequest(HttpMethod.POST, FACULTY_SERVICE_URL, facultyData);
        printSuccess("Faculty created successfully:");
        System.out.println(response);
    }

    private static void updateFacultyInformation() {
        Long facultyId = getLongInput("Enter faculty ID to update: ");
        Map<String, Object> facultyData = new HashMap<>();
        facultyData.put("firstName", getStringInput("Enter new first name: "));
        facultyData.put("lastName", getStringInput("Enter new last name: "));
        facultyData.put("email", getStringInput("Enter new email: "));
        facultyData.put("department", getStringInput("Enter new department: "));
        facultyData.put("position", getStringInput("Enter new position: "));
        facultyData.put("specialization", getStringInput("Enter new specialization: "));

        String response = sendRequest(HttpMethod.PUT, FACULTY_SERVICE_URL + "/" + facultyId, facultyData);
        printSuccess("Faculty updated successfully:");
        System.out.println(response);
    }

    private static void deleteFaculty() {
        Long facultyId = getLongInput("Enter faculty ID to delete: ");
        String response = sendRequest(HttpMethod.DELETE, FACULTY_SERVICE_URL + "/" + facultyId, null);
        printSuccess("Faculty deleted successfully.");
    }

    private static void getFacultyInformation() {
        Long facultyId = getLongInput("Enter faculty ID: ");
        String response = sendRequest(HttpMethod.GET, FACULTY_SERVICE_URL + "/" + facultyId, null);
        printSuccess("Faculty information:");
        System.out.println(response);
    }

    private static void assignCourseToFaculty() {
        Map<String, Object> assignmentData = new HashMap<>();
        assignmentData.put("faculty", Map.of("id", getLongInput("Enter faculty ID: ")));
        assignmentData.put("courseId", getLongInput("Enter course ID: "));
        assignmentData.put("semester", getStringInput("Enter semester: "));
        assignmentData.put("year", getIntInput("Enter year: "));

        String response = sendRequest(HttpMethod.POST, FACULTY_SERVICE_URL + "/course-assignments", assignmentData);
        printSuccess("Course assigned successfully:");
        System.out.println(response);
    }

    private static void getCourseAssignmentInformation() {
        Long facultyId = getLongInput("Enter faculty ID: ");
        String response = sendRequest(HttpMethod.GET, FACULTY_SERVICE_URL + "/course-assignments/faculty/" + facultyId, null);
        printSuccess("Course assignment information:");
        System.out.println(response);
    }

    // Academic Records Service Methods
    private static void addGrade() {
        Long studentId = getLongInput("Enter student ID: ");
        Map<String, Object> gradeData = new HashMap<>();
        gradeData.put("courseId", getLongInput("Enter course ID: "));
        gradeData.put("grade", getStringInput("Enter grade: "));
        gradeData.put("semester", getStringInput("Enter semester: "));
        gradeData.put("year", getIntInput("Enter year: "));

        String response = sendRequest(HttpMethod.POST, ACADEMIC_RECORDS_SERVICE_URL + "/" + studentId + "/grades", gradeData);
        printSuccess("Grade added successfully:");
        System.out.println(response);
    }

    private static void updateGrade() {
        Long studentId = getLongInput("Enter student ID: ");
        Long gradeId = getLongInput("Enter grade ID to update: ");
        Map<String, Object> gradeData = new HashMap<>();
        gradeData.put("grade", getStringInput("Enter new grade: "));
        gradeData.put("semester", getStringInput("Enter new semester: "));
        gradeData.put("year", getIntInput("Enter new year: "));

        String response = sendRequest(HttpMethod.PUT, ACADEMIC_RECORDS_SERVICE_URL + "/" + studentId + "/grades/" + gradeId, gradeData);
        printSuccess("Grade updated successfully:");
        System.out.println(response);
    }

    private static void deleteGrade() {
        Long studentId = getLongInput("Enter student ID: ");
        Long gradeId = getLongInput("Enter grade ID to delete: ");
        String response = sendRequest(HttpMethod.DELETE, ACADEMIC_RECORDS_SERVICE_URL + "/" + studentId + "/grades/" + gradeId, null);
        printSuccess("Grade deleted successfully.");
    }

    private static void getStudentGrades() {
        Long studentId = getLongInput("Enter student ID: ");
        String response = sendRequest(HttpMethod.GET, ACADEMIC_RECORDS_SERVICE_URL + "/" + studentId, null);
        printSuccess("Student grades:");
        System.out.println(response);
    }

    private static void calculateGPA() {
        Long studentId = getLongInput("Enter student ID: ");
        String response = sendRequest(HttpMethod.GET, ACADEMIC_RECORDS_SERVICE_URL + "/" + studentId + "/gpa", null);
        System.out.println("Student GPA:");
        System.out.println(response);
    }

    private static void generateTranscript() {
        Long studentId = getLongInput("Enter student ID: ");
        String response = sendRequest(HttpMethod.GET, ACADEMIC_RECORDS_SERVICE_URL + "/" + studentId + "/transcript", null);
        System.out.println("Student Transcript:");
        System.out.println(response);
    }

    private static void generateSemesterReport() {
        Long studentId = getLongInput("Enter student ID: ");
        String semester = getStringInput("Enter semester: ");
        Integer year = getIntInput("Enter year: ");
        String response = sendRequest(HttpMethod.GET, ACADEMIC_RECORDS_SERVICE_URL + "/" + studentId + "/semester-report?semester=" + semester + "&year=" + year, null);
        System.out.println("Semester Report:");
        System.out.println(response);
    }

    // Helper Methods
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine());
    }

    private static Long getLongInput(String prompt) {
        System.out.print(prompt);
        return Long.parseLong(scanner.nextLine());
    }

    private static LocalDate getDateInput(String prompt) {
        System.out.print(prompt);
        return LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private static String sendRequest(HttpMethod method, String url, Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    
        String requestBody = null;
        try {
            requestBody = body != null ? objectMapper.writeValueAsString(body) : null;
        } catch (JsonProcessingException e) {
            System.err.println("Error converting request body to JSON: " + e.getMessage());
            return null;
        }
    
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, method, requestEntity, String.class);
        String responseBody = responseEntity.getBody();
    
        System.out.println(BLUE + "Response:" + RESET);
        try {
            // Parse the JSON to ensure it's valid, then pretty print it
            Object json = objectMapper.readValue(responseBody, Object.class);
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
        } catch (JsonProcessingException e) {
            System.err.println("Error parsing response JSON: " + e.getMessage());
            System.out.println(responseBody); // Print raw response if parsing fails
        }
    
        return responseBody;
    }

    private static void simulateKafkaEvent(String topic, String message) {
        System.out.println("Broadcasting Kafka event on topic: " + topic);
        System.out.println("Message: " + message);
    }

    // Automatic Workflow
    private static void runAutomaticWorkflow() {
        System.out.println("Running Automatic Workflow...");

        // Step 1: Create a New Student
        System.out.println("Creating a new student...");
        Map<String, Object> studentData = new HashMap<>();
        studentData.put("firstName", "John");
        studentData.put("lastName", "Doe");
        studentData.put("email", "john.doe@example.com");
        studentData.put("dateOfBirth", LocalDate.of(1990, 1, 1));
        studentData.put("address", "123 Main St");
        studentData.put("phoneNumber", "123-456-7890");
        String studentResponse = sendRequest(HttpMethod.POST, STUDENT_SERVICE_URL, studentData);
        printSuccess("Student created successfully:");
        //System.out.println(studentResponse);
        simulateKafkaEvent("student-created-topic", studentResponse);

        // Step 2: Create New Courses
        System.out.println("Creating new courses...");
        Map<String, Object> courseData1 = new HashMap<>();
        courseData1.put("courseCode", "CS101");
        courseData1.put("name", "Introduction to Computer Science");
        courseData1.put("description", "An introductory course to computer science.");
        courseData1.put("creditHours", 3);
        courseData1.put("department", "Computer Science");
        String courseResponse1 = sendRequest(HttpMethod.POST, COURSE_SERVICE_URL, courseData1);
        printSuccess("Course created successfully:");
        //System.out.println(courseResponse1);
        simulateKafkaEvent("course-created-topic", courseResponse1);

        Map<String, Object> courseData2 = new HashMap<>();
        courseData2.put("courseCode", "MATH101");
        courseData2.put("name", "Introduction to Mathematics");
        courseData2.put("description", "An introductory course to mathematics.");
        courseData2.put("creditHours", 4);
        courseData2.put("department", "Mathematics");
        String courseResponse2 = sendRequest(HttpMethod.POST, COURSE_SERVICE_URL, courseData2);
        printSuccess("Course created successfully:");
        //System.out.println(courseResponse2);
        simulateKafkaEvent("course-created-topic", courseResponse2);

        // Step 3: Enroll the Student in Courses
        System.out.println("Enrolling the student in courses...");
        Map<String, Object> enrollmentData1 = new HashMap<>();
        enrollmentData1.put("studentId", extractId(studentResponse));
        enrollmentData1.put("courseId", extractId(courseResponse1));
        enrollmentData1.put("enrollmentDate", LocalDate.now()); 

        String enrollmentResponse1 = sendRequest(HttpMethod.POST, ENROLLMENT_SERVICE_URL, enrollmentData1);
        printSuccess("Enrollment created successfully:");
        //System.out.println(enrollmentResponse1);

        Map<String, Object> enrollmentData2 = new HashMap<>();
        enrollmentData2.put("studentId", extractId(studentResponse));
        enrollmentData2.put("courseId", extractId(courseResponse2));
        enrollmentData2.put("enrollmentDate", LocalDate.now());
        String enrollmentResponse2 = sendRequest(HttpMethod.POST, ENROLLMENT_SERVICE_URL, enrollmentData2);
        printSuccess("Enrollment created successfully:");
        //System.out.println(enrollmentResponse2);

        // Step 4: Assign Grades to the Student
        System.out.println("Assigning grades to the student...");
        Map<String, Object> gradeData1 = new HashMap<>();
        gradeData1.put("courseId", extractId(courseResponse1));
        gradeData1.put("grade", "A");
        gradeData1.put("semester", "Fall");
        gradeData1.put("year", 2023);
        String gradeResponse1 = sendRequest(HttpMethod.POST, ACADEMIC_RECORDS_SERVICE_URL + "/" + extractId(studentResponse) + "/grades", gradeData1);
        printSuccess("Grade added successfully:");
        //System.out.println(gradeResponse1);

        Map<String, Object> gradeData2 = new HashMap<>();
        gradeData2.put("courseId", extractId(courseResponse2));
        gradeData2.put("grade", "B");
        gradeData2.put("semester", "Fall");
        gradeData2.put("year", 2023);
        String gradeResponse2 = sendRequest(HttpMethod.POST, ACADEMIC_RECORDS_SERVICE_URL + "/" + extractId(studentResponse) + "/grades", gradeData2);
        printSuccess("Grade added successfully:");
        //System.out.println(gradeResponse2);

        // Step 5: Calculate GPA
        System.out.println("Calculating GPA...");
        String gpaResponse = sendRequest(HttpMethod.GET, ACADEMIC_RECORDS_SERVICE_URL + "/" + extractId(studentResponse) + "/gpa", null);
        //System.out.println("Student GPA:");
        //System.out.println(gpaResponse);

        // Step 6: Generate Transcript
        System.out.println("Generating transcript...");
        String transcriptResponse = sendRequest(HttpMethod.GET, ACADEMIC_RECORDS_SERVICE_URL + "/" + extractId(studentResponse) + "/transcript", null);
        //System.out.println("Student Transcript:");
        //System.out.println(transcriptResponse);

        // Step 7: Generate Semester Report
        System.out.println("Generating semester report...");
        String semesterReportResponse = sendRequest(HttpMethod.GET, ACADEMIC_RECORDS_SERVICE_URL + "/" + extractId(studentResponse) + "/semester-report?semester=Fall&year=2023", null);
        //System.out.println("Semester Report:");
        //System.out.println(semesterReportResponse);

        printSuccess("Automatic Workflow Completed.");
    }

    private static Long extractId(String response) {
        try {
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
            return Long.parseLong(responseMap.get("id").toString());
        } catch (Exception e) {
            System.err.println("Error extracting ID from response: " + e.getMessage());
            return null;
        }
    }
}