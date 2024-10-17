package com.university.courseservice.application.service;

import com.university.courseservice.domain.model.Course;
import com.university.courseservice.domain.model.CourseAnalytics;

import java.util.List;

public interface CourseService {
    Course createCourse(Course course);
    Course getCourseById(Long id);
    Course updateCourse(Long id, Course courseDetails);
    void deleteCourse(Long id);
    List<Course> getAllCourses();
    Course getCourseByCourseCode(String courseCode);

    CourseAnalytics getCourseAnalytics(Long courseId);
    void updateEnrollmentCount(Long courseId);
    void updateAverageGrade(Long courseId, String grade);
}