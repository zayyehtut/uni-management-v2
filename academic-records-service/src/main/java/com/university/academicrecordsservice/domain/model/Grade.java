package com.university.academicrecordsservice.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.university.common.domain.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "grades")
@Getter
@Setter
public class Grade extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_record_id")
    @JsonBackReference
    private AcademicRecord academicRecord;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    @NotNull(message = "Grade value is required")
    private String grade;

    private String semester;

    @Column(name = "`year`")
    private Integer year;
}
