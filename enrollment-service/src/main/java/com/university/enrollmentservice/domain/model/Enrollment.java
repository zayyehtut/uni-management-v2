package com.university.enrollmentservice.domain.model;

import com.university.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "enrollments")
@Getter
@Setter
public class Enrollment extends BaseEntity {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    @NotNull(message = "Enrollment date is required")
    private LocalDate enrollmentDate;

    private String status;

    @PrePersist
    public void prePersist() {
        if (enrollmentDate == null) {
            enrollmentDate = LocalDate.now();
        }
    }
}