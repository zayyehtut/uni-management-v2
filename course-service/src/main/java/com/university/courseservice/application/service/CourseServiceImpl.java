package com.university.courseservice.application.service;

import com.university.common.exception.ResourceNotFoundException;
import com.university.courseservice.domain.model.Course;
import com.university.courseservice.domain.model.CourseAnalytics;
import com.university.courseservice.domain.repository.CourseAnalyticsRepository;
import com.university.courseservice.domain.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.cloud.stream.function.StreamBridge;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseAnalyticsRepository analyticsRepository;

    private final StreamBridge streamBridge;

    @Override
    public Course createCourse(Course course) {
        Course savedCourse = courseRepository.save(course);
        streamBridge.send("courseCreatedOutput-out-0", savedCourse);
        return savedCourse;
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
    }

    @Override
    public Course updateCourse(Long id, Course courseDetails) {
        Course course = getCourseById(id);
        course.setCourseCode(courseDetails.getCourseCode());
        course.setName(courseDetails.getName());
        course.setDescription(courseDetails.getDescription());
        course.setCreditHours(courseDetails.getCreditHours());
        course.setDepartment(courseDetails.getDepartment());
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseByCourseCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "courseCode", courseCode));
    }

    @Override
    public CourseAnalytics getCourseAnalytics(Long courseId) {
        return analyticsRepository.findByCourseId(courseId)
            .orElseGet(() -> {
                // Verify the course exists
                courseRepository.findById(courseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));
                
                CourseAnalytics newAnalytics = new CourseAnalytics();
                newAnalytics.setCourseId(courseId);
                newAnalytics.setEnrollmentCount(0);
                newAnalytics.setAverageGrade(0.0);
                return analyticsRepository.save(newAnalytics);
            });
    }

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

    private double convertGradeToNumeric(String grade) {
        // Implement grade to numeric conversion
        // For example:
        switch (grade) {
            case "A": return 4.0;
            case "B": return 3.0;
            case "C": return 2.0;
            case "D": return 1.0;
            case "F": return 0.0;
            default: throw new IllegalArgumentException("Invalid grade: " + grade);
        }
    }
}