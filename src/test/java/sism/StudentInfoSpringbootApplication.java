package sism;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentInfoSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentInfoSpringbootApplication.class, args);
        System.out.println("Student Information System is running on http://localhost:8001");
    }
}