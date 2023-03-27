package ru.otus.spring.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import ru.otus.spring.integration.config.Evolution;
import ru.otus.spring.integration.domain.Bug;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class IntegrationApplicationTests {

	@Autowired
	private Evolution evolution;

	@Test
	void startIntegrationTest() {
		Bug bugExpected = new Bug("test-fly", 2, 1);
		Bug bug = new Bug("test-fly", 0, 1);

		Message<Bug> bugMessage = MessageBuilder.withPayload(bug).build();
		Message<Bug> rcvMessage = evolution.process(bugMessage);

		Bug bugReceived = rcvMessage.getPayload();

		assertThat(bugReceived).usingRecursiveComparison().isEqualTo(bugExpected);
	}
}
