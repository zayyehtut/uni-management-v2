package com.university.facultymanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FacultyManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FacultyManagementServiceApplication.class, args);
    }
}