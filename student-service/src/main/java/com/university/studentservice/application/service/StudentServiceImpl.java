package com.university.studentservice.application.service;

import com.university.common.exception.ResourceNotFoundException;
import com.university.studentservice.domain.model.Student;
import com.university.studentservice.domain.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.cloud.stream.function.StreamBridge;
import lombok.extern.slf4j.Slf4j;


import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StreamBridge streamBridge;


    @Override
    public Student createStudent(Student student) {
        Student savedStudent = studentRepository.save(student);
        streamBridge.send("studentCreatedOutput-out-0", savedStudent);
        log.info("Student created event sent: {}", savedStudent);
        return savedStudent;
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
    }

    @Override
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = getStudentById(id);
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());
        student.setDateOfBirth(studentDetails.getDateOfBirth());
        student.setAddress(studentDetails.getAddress());
        student.setPhoneNumber(studentDetails.getPhoneNumber());
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "email", email));
    }
}