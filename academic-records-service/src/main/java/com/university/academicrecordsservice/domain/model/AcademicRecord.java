package com.university.academicrecordsservice.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.university.common.domain.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "academic_records")
@Getter
@Setter
public class AcademicRecord extends BaseEntity {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @OneToMany(mappedBy = "academicRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Grade> grades = new ArrayList<>();

    private Double gpa;

    public void addGrade(Grade grade) {
        grades.add(grade);
        grade.setAcademicRecord(this);
    }

    public void removeGrade(Grade grade) {
        grades.remove(grade);
        grade.setAcademicRecord(null);
    }
}
