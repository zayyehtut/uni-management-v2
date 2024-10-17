package com.university.facultymanagementservice.domain.repository;

import com.university.facultymanagementservice.domain.model.CourseAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseAssignmentRepository extends JpaRepository<CourseAssignment, Long> {
    List<CourseAssignment> findByFacultyId(Long facultyId);
    List<CourseAssignment> findByCourseId(Long courseId);
}