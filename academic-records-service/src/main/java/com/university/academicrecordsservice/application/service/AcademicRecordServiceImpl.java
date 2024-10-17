package com.university.academicrecordsservice.application.service;
import org.springframework.transaction.annotation.Transactional;


import com.university.academicrecordsservice.domain.model.AcademicRecord;
import com.university.academicrecordsservice.domain.model.Grade;
import com.university.academicrecordsservice.domain.repository.AcademicRecordRepository;
import com.university.academicrecordsservice.infrastructure.client.StudentClient;
import com.university.academicrecordsservice.infrastructure.event.GradeSubmittedEvent;
import com.university.academicrecordsservice.infrastructure.client.CourseClient;
import com.university.common.exception.ResourceNotFoundException;
import com.university.academicrecordsservice.application.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.cloud.stream.function.StreamBridge;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AcademicRecordServiceImpl implements AcademicRecordService {

    private final AcademicRecordRepository academicRecordRepository;
    private final StudentClient studentClient;
    private final CourseClient courseClient;
    private final StreamBridge streamBridge;


    @Override
    public AcademicRecord getAcademicRecordByStudentId(Long studentId) {
        return academicRecordRepository.findByStudentId(studentId)
                .orElseGet(() -> createNewAcademicRecord(studentId));
    }

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

    @Override
    public AcademicRecord updateGrade(Long studentId, Long gradeId, Grade updatedGrade) {
        AcademicRecord academicRecord = getAcademicRecordByStudentId(studentId);
        Grade grade = academicRecord.getGrades().stream()
                .filter(g -> g.getId().equals(gradeId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Grade", "id", gradeId));

        grade.setGrade(updatedGrade.getGrade());
        grade.setSemester(updatedGrade.getSemester());
        grade.setYear(updatedGrade.getYear());

        academicRecord.setGpa(calculateGPA(studentId));
        AcademicRecord savedRecord = academicRecordRepository.save(academicRecord);

        // Publish grade submitted event
        GradeSubmittedEvent event = new GradeSubmittedEvent();
        event.setCourseId(grade.getCourseId());
        event.setGrade(grade.getGrade());
        streamBridge.send("gradeSubmittedOutput-out-0", event);
        log.info("Grade updated event published for student ID: {}", studentId);
        return savedRecord;
    }

    @Override
    public void deleteGrade(Long studentId, Long gradeId) {
        AcademicRecord academicRecord = getAcademicRecordByStudentId(studentId);
        Grade grade = academicRecord.getGrades().stream()
                .filter(g -> g.getId().equals(gradeId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Grade", "id", gradeId));

        academicRecord.removeGrade(grade);
        academicRecord.setGpa(calculateGPA(studentId));
        academicRecordRepository.save(academicRecord);
    }

    @Override
    public double calculateGPA(Long studentId) {
        AcademicRecord academicRecord = getAcademicRecordByStudentId(studentId);
        if (academicRecord.getGrades().isEmpty()) {
            return 0.0;
        }

        double totalPoints = 0.0;
        int totalCredits = 0;

        for (Grade grade : academicRecord.getGrades()) {
            double gradePoints = convertGradeToPoints(grade.getGrade());
            int credits = getCourseCredits(grade.getCourseId());

            totalPoints += gradePoints * credits;
            totalCredits += credits;
        }

        return totalCredits > 0 ? totalPoints / totalCredits : 0.0;
    }

    private AcademicRecord createNewAcademicRecord(Long studentId) {
        validateStudent(studentId);
        AcademicRecord newRecord = new AcademicRecord();
        newRecord.setStudentId(studentId);
        newRecord.setGpa(0.0);
        return academicRecordRepository.save(newRecord);
    }

    private void validateStudent(Long studentId) {
        studentClient.getStudentById(studentId);
    }

    private void validateCourse(Long courseId) {
        courseClient.getCourseById(courseId);
    }

    private int getCourseCredits(Long courseId) {  
        CourseDTO course = courseClient.getCourseById(courseId);
        return course.getCreditHours();
    }

    private double convertGradeToPoints(String grade) {
        Map<String, Double> gradePoints = new HashMap<>();
        gradePoints.put("A", 4.0);
        gradePoints.put("B", 3.0);
        gradePoints.put("C", 2.0);
        gradePoints.put("D", 1.0);
        gradePoints.put("F", 0.0);

        return gradePoints.getOrDefault(grade.toUpperCase(), 0.0);
    }

    @Override
    public void initializeAcademicRecord(Long studentId) {
        AcademicRecord newRecord = new AcademicRecord();
        newRecord.setStudentId(studentId);
        newRecord.setGpa(0.0);
        academicRecordRepository.save(newRecord);
        log.info("Initialized academic record for student ID: {}", studentId);
    }

    @Override
    public TranscriptDTO generateTranscript(Long studentId) {
        AcademicRecord academicRecord = getAcademicRecordByStudentId(studentId);
        StudentDTO student = studentClient.getStudentById(studentId);
        
        TranscriptDTO transcript = new TranscriptDTO();
        transcript.setStudentId(studentId);
        transcript.setStudentName(student.getFirstName() + " " + student.getLastName());
        transcript.setStudentEmail(student.getEmail());
        transcript.setCumulativeGPA(academicRecord.getGpa());

        Map<String, SemesterGradeReportDTO> semesterReports = new HashMap<>();

        for (Grade grade : academicRecord.getGrades()) {
            String semesterKey = grade.getSemester() + "-" + grade.getYear();
            SemesterGradeReportDTO semesterReport = semesterReports.computeIfAbsent(semesterKey,
                    k -> createSemesterGradeReport(grade.getSemester(), grade.getYear()));

            CourseGradeDTO courseGrade = createCourseGradeDTO(grade);
            semesterReport.getCourseGrades().add(courseGrade);
        }

        for (SemesterGradeReportDTO report : semesterReports.values()) {
            report.setSemesterGPA(calculateSemesterGPA(report.getCourseGrades()));
        }

        transcript.setSemesterReports(new ArrayList<>(semesterReports.values()));
        return transcript;
    }

    @Override
    public SemesterGradeReportDTO generateSemesterGradeReport(Long studentId, String semester, Integer year) {
        AcademicRecord academicRecord = getAcademicRecordByStudentId(studentId);
        SemesterGradeReportDTO report = createSemesterGradeReport(semester, year);

        List<Grade> semesterGrades = academicRecord.getGrades().stream()
                .filter(grade -> grade.getSemester().equals(semester) && grade.getYear().equals(year))
                .collect(Collectors.toList());

        for (Grade grade : semesterGrades) {
            CourseGradeDTO courseGrade = createCourseGradeDTO(grade);
            report.getCourseGrades().add(courseGrade);
        }

        report.setSemesterGPA(calculateSemesterGPA(report.getCourseGrades()));
        return report;
    }

    private SemesterGradeReportDTO createSemesterGradeReport(String semester, Integer year) {
        SemesterGradeReportDTO report = new SemesterGradeReportDTO();
        report.setSemester(semester);
        report.setYear(year);
        report.setCourseGrades(new ArrayList<>());
        return report;
    }

    private CourseGradeDTO createCourseGradeDTO(Grade grade) {
        CourseDTO course = courseClient.getCourseById(grade.getCourseId());
        CourseGradeDTO courseGrade = new CourseGradeDTO();
        courseGrade.setCourseId(grade.getCourseId());
        courseGrade.setCourseCode(course.getCourseCode());
        courseGrade.setCourseName(course.getName());
        courseGrade.setGrade(grade.getGrade());
        courseGrade.setCreditHours(course.getCreditHours());
        courseGrade.setDepartment(course.getDepartment());
        return courseGrade;
    }

    private double calculateSemesterGPA(List<CourseGradeDTO> courseGrades) {
        if (courseGrades.isEmpty()) {
            return 0.0;
        }

        double totalPoints = 0.0;
        int totalCredits = 0;

        for (CourseGradeDTO courseGrade : courseGrades) {
            double gradePoints = convertGradeToPoints(courseGrade.getGrade());
            int credits = courseGrade.getCreditHours();

            totalPoints += gradePoints * credits;
            totalCredits += credits;
        }

        return totalCredits > 0 ? totalPoints / totalCredits : 0.0;
    }
}
