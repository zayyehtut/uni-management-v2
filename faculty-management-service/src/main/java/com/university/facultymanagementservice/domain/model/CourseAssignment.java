package com.university.facultymanagementservice.domain.model;

import com.university.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "course_assignments")
@Getter
@Setter
public class CourseAssignment extends BaseEntity {

    @ManyToOne
    @NotNull(message = "Faculty is required")
    private Faculty faculty;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    private String semester;

    @Column(name = "`year`")
    private Integer year;
}
