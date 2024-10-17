package com.university.enrollmentservice.application.service;

import com.university.common.exception.ResourceNotFoundException;
import com.university.enrollmentservice.domain.model.Enrollment;
import com.university.enrollmentservice.domain.repository.EnrollmentRepository;
import com.university.enrollmentservice.application.dto.CourseDTO;
import com.university.enrollmentservice.domain.model.AvailableCourse;
import com.university.enrollmentservice.domain.repository.AvailableCourseRepository;
import com.university.enrollmentservice.infrastructure.client.StudentClient;
import com.university.enrollmentservice.infrastructure.client.CourseClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
    public Enrollment createEnrollment(Enrollment enrollment) {
        // Verify student and course exist
        studentClient.getStudentById(enrollment.getStudentId());
        courseClient.getCourseById(enrollment.getCourseId());
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus("ACTIVE");
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        streamBridge.send("enrollmentCreatedOutput-out-0", savedEnrollment);
        log.info("Enrollment created event sent: {}", savedEnrollment);
        return savedEnrollment;
    }

    @Override
    public Enrollment getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment", "id", id));
    }

    @Override
    public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    @Override
    public List<Enrollment> getEnrollmentsByCourseId(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    @Override
    public void deleteEnrollment(Long id) {
        Enrollment enrollment = getEnrollmentById(id);
        enrollmentRepository.delete(enrollment);
    }

    @Override
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

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