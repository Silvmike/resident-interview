package ru.silvmike.interview.resident.average.app;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import ru.silvmike.interview.resident.average.config.RootConfiguration;

@SpringBootConfiguration
@EnableAutoConfiguration
public class AverageApplication {

	public static void main(String[] args) {

		new SpringApplicationBuilder()
			.sources(AverageApplication.class, RootConfiguration.class)
			.profiles(Profiles.STD_OUT_PROCESSOR, Profiles.SAMPLE_FILE, Profiles.STUB_AVERAGE_PROVIDER)
			.run(args);
	}

}
