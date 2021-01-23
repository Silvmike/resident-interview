package ru.silvmike.interview.resident.average.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AverageApplication {

	public static void main(String[] args) {

		new SpringApplicationBuilder()
				.sources(AverageApplication.class)
				.run(args);
	}

}
