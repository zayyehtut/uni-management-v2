package com.university.academicrecordsservice.infrastructure.event;

import lombok.Data;

@Data
public class GradeSubmittedEvent {
    private Long courseId;
    private String grade;
}