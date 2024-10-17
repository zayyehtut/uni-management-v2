package com.university.facultymanagementservice.application.service;

import com.university.facultymanagementservice.domain.model.Faculty;

import java.util.List;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);
    Faculty getFacultyById(Long id);
    Faculty updateFaculty(Long id, Faculty facultyDetails);
    void deleteFaculty(Long id);
    List<Faculty> getAllFaculty();
    Faculty getFacultyByEmail(String email);
}