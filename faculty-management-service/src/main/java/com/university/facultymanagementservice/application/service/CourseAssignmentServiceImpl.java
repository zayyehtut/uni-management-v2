package com.university.facultymanagementservice.application.service;

import com.university.common.exception.ResourceNotFoundException;
import com.university.facultymanagementservice.domain.model.CourseAssignment;
import com.university.facultymanagementservice.domain.repository.CourseAssignmentRepository;
import com.university.facultymanagementservice.infrastructure.client.CourseClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseAssignmentServiceImpl implements CourseAssignmentService {

    private final CourseAssignmentRepository courseAssignmentRepository;
    private final FacultyService facultyService;
    private final CourseClient courseClient;

    @Override
    public CourseAssignment assignCourse(CourseAssignment courseAssignment) {
        // Verify faculty and course exist
        facultyService.getFacultyById(courseAssignment.getFaculty().getId());
        courseClient.getCourseById(courseAssignment.getCourseId());
        return courseAssignmentRepository.save(courseAssignment);
    }

    @Override
    public CourseAssignment getCourseAssignmentById(Long id) {
        return courseAssignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CourseAssignment", "id", id));
    }

    @Override
    public List<CourseAssignment> getCourseAssignmentsByFacultyId(Long facultyId) {
        return courseAssignmentRepository.findByFacultyId(facultyId);
    }

    @Override
    public List<CourseAssignment> getCourseAssignmentsByCourseId(Long courseId) {
        return courseAssignmentRepository.findByCourseId(courseId);
    }

    @Override
    public void deleteCourseAssignment(Long id) {
        CourseAssignment courseAssignment = getCourseAssignmentById(id);
        courseAssignmentRepository.delete(courseAssignment);
    }

    @Override
    public List<CourseAssignment> getAllCourseAssignments() {
        return courseAssignmentRepository.findAll();
    }
}