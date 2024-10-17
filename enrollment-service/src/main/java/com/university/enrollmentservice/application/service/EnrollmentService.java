package com.university.enrollmentservice.application.service;
import com.university.enrollmentservice.application.dto.CourseDTO;
import com.university.enrollmentservice.domain.model.Enrollment;

import java.util.List;

public interface EnrollmentService {
    Enrollment createEnrollment(Enrollment enrollment);
    Enrollment getEnrollmentById(Long id);
    List<Enrollment> getEnrollmentsByStudentId(Long studentId);
    List<Enrollment> getEnrollmentsByCourseId(Long courseId);
    void deleteEnrollment(Long id);
    List<Enrollment> getAllEnrollments();
    void addNewCourseToAvailableCourses(CourseDTO courseDTO);
}