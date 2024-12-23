package com.project.exam_prep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@CrossOrigin(origins = "http://http://127.0.0.1:5500")
public class ExamPrepApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamPrepApplication.class, args);
	}

}
