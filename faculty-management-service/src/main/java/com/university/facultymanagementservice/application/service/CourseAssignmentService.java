package com.university.facultymanagementservice.application.service;

import com.university.facultymanagementservice.domain.model.CourseAssignment;

import java.util.List;

public interface CourseAssignmentService {
    CourseAssignment assignCourse(CourseAssignment courseAssignment);
    CourseAssignment getCourseAssignmentById(Long id);
    List<CourseAssignment> getCourseAssignmentsByFacultyId(Long facultyId);
    List<CourseAssignment> getCourseAssignmentsByCourseId(Long courseId);
    void deleteCourseAssignment(Long id);
    List<CourseAssignment> getAllCourseAssignments();
}