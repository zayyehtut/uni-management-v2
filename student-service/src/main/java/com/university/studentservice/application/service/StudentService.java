package com.university.studentservice.application.service;

import com.university.studentservice.domain.model.Student;

import java.util.List;

public interface StudentService {
    Student createStudent(Student student);
    Student getStudentById(Long id);
    Student updateStudent(Long id, Student studentDetails);
    void deleteStudent(Long id);
    List<Student> getAllStudents();
    Student getStudentByEmail(String email);
}