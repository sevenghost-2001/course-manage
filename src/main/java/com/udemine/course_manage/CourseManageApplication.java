package com.udemine.course_manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CourseManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseManageApplication.class, args);
	}

}
