package org.example.dailydiary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DailyDiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyDiaryApplication.class, args);
	}

}
