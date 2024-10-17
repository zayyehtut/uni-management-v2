package com.university.courseservice.domain.model;

import com.university.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "course_analytics")
@Getter
@Setter
public class CourseAnalytics extends BaseEntity {

    @Column(unique = true)
    private Long courseId;
    private int enrollmentCount;
    private double averageGrade;
}