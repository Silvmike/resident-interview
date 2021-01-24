package ru.silvmike.interview.resident.average.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import ru.silvmike.interview.resident.average.config.RootConfiguration;

@SpringBootApplication
public class AverageApplication {

	public static void main(String[] args) {

		new SpringApplicationBuilder()
			.sources(AverageApplication.class, RootConfiguration.class)
			.profiles(Profiles.SAMPLE_FILE, Profiles.H2)
			.run(args);
	}

}
