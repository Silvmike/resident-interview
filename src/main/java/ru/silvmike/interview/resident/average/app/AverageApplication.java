package ru.silvmike.interview.resident.average.app;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import ru.silvmike.interview.resident.average.config.RootConfiguration;

@SpringBootConfiguration
@EnableAutoConfiguration
public class AverageApplication {

	public static void main(String[] args) {

		setDefaultProfiles(Profiles.H2, Profiles.SAMPLE_FILE);
		new SpringApplicationBuilder()
			.sources(AverageApplication.class, RootConfiguration.class)
			.build(args)
			.run(args);
	}

	private static void setDefaultProfiles(String... profiles) {
		System.setProperty(
			"spring.profiles.default",
			String.join(",", profiles)
		);
	}

}
