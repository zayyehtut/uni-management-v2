package com.university.enrollmentservice.presentation.controller;

import com.university.enrollmentservice.domain.model.AvailableCourse;
import com.university.enrollmentservice.domain.repository.AvailableCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/available-courses")
@RequiredArgsConstructor
public class AvailableCourseController {

    private final AvailableCourseRepository availableCourseRepository;

    @GetMapping
    public ResponseEntity<List<AvailableCourse>> getAllAvailableCourses() {
        List<AvailableCourse> availableCourses = availableCourseRepository.findAll();
        return ResponseEntity.ok(availableCourses);
    }
}