package com.university.academicrecordsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AcademicRecordsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcademicRecordsServiceApplication.class, args);
    }
}