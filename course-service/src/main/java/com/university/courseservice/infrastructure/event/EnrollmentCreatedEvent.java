package com.university.courseservice.infrastructure.event;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EnrollmentCreatedEvent {
    private Long id;
    private Long courseId;
    private Long studentId;
    private LocalDate enrollmentDate;
    private String status;
}