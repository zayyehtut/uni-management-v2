package com.university.facultymanagementservice.application.service;

import com.university.common.exception.ResourceNotFoundException;
import com.university.facultymanagementservice.domain.model.Faculty;
import com.university.facultymanagementservice.domain.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    @Override
    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty", "id", id));
    }

    @Override
    public Faculty updateFaculty(Long id, Faculty facultyDetails) {
        Faculty faculty = getFacultyById(id);
        faculty.setFirstName(facultyDetails.getFirstName());
        faculty.setLastName(facultyDetails.getLastName());
        faculty.setEmail(facultyDetails.getEmail());
        faculty.setDepartment(facultyDetails.getDepartment());
        faculty.setPosition(facultyDetails.getPosition());
        faculty.setSpecialization(facultyDetails.getSpecialization());
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        Faculty faculty = getFacultyById(id);
        facultyRepository.delete(faculty);
    }

    @Override
    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    @Override
    public Faculty getFacultyByEmail(String email) {
        return facultyRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty", "email", email));
    }
}