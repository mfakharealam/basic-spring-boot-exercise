package com.learning.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class StudentConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student mariam = new Student(
                    "Mariam",
                    LocalDate.of(1999, 2, 22),
                    "mariam.jamal@some.com"
            );

            Student alex = new Student(
                    "Alex",
                    LocalDate.of(2001, 2, 22),
                    "alex.brad@some.com"
            );

            studentRepository.saveAll(List.of(alex, mariam));
        };
    }
}
