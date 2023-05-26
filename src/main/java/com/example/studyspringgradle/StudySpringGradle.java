package com.example.studyspringgradle;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class StudySpringGradle {

    public static void main(String[] args) {
        new SpringApplicationBuilder(StudySpringGradle.class)
                .properties("spring.config.location=classpath:starter.yml")
                .run(args);
    }
}
