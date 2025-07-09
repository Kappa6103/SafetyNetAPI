package com.safetynet.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(properties = "logging.level.com.safetynet.api=INFO")
class ApiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void loggingStartAppTest(CapturedOutput output) {

		ApiApplication.main(new String[] {
				"--spring.main.web-application-type=none"
		});

		assertTrue(output.getOut().contains("Start of the Safety Net API : )"));

	}

}
