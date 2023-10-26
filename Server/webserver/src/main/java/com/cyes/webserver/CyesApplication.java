package com.cyes.webserver;

import com.cyes.webserver.scheduler.QuizScheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CyesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CyesApplication.class, args);
		new QuizScheduler().createQuizSchedule("3");
	}

}
