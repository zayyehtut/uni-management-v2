package com.university.facultymanagementservice.presentation.controller;

import com.university.facultymanagementservice.application.service.CourseAssignmentService;
import com.university.facultymanagementservice.domain.model.CourseAssignment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course-assignments")
@RequiredArgsConstructor
public class CourseAssignmentController {

    private final CourseAssignmentService courseAssignmentService;

    @PostMapping
    public ResponseEntity<CourseAssignment> assignCourse(@Valid @RequestBody CourseAssignment courseAssignment) {
        return new ResponseEntity<>(courseAssignmentService.assignCourse(courseAssignment), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseAssignment> getCourseAssignmentById(@PathVariable Long id) {
        return ResponseEntity.ok(courseAssignmentService.getCourseAssignmentById(id));
    }

    @GetMapping("/faculty/{facultyId}")
    public ResponseEntity<List<CourseAssignment>> getCourseAssignmentsByFacultyId(@PathVariable Long facultyId) {
        return ResponseEntity.ok(courseAssignmentService.getCourseAssignmentsByFacultyId(facultyId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<CourseAssignment>> getCourseAssignmentsByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseAssignmentService.getCourseAssignmentsByCourseId(courseId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseAssignment(@PathVariable Long id) {
        courseAssignmentService.deleteCourseAssignment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CourseAssignment>> getAllCourseAssignments() {
        return ResponseEntity.ok(courseAssignmentService.getAllCourseAssignments());
    }
}