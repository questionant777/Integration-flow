package ru.otus.spring.integration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import ru.otus.spring.integration.config.Evolution;
import ru.otus.spring.integration.domain.Bug;

import org.apache.commons.lang3.RandomUtils;
import ru.otus.spring.integration.service.HandleInOut;

@SpringBootApplication
@IntegrationComponentScan
@EnableIntegration
public class IntegrationApplication {

	private static final String[] BUGS = {"морфей", "мотылек", "голубянка", "красный адмирал", "монарх ", "парусник", "репница"};

	public static void main(String[] args) throws InterruptedException {
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(IntegrationApplication.class);

		HandleInOut handleInOut = ctx.getBean(HandleInOut.class);

		Evolution evolution = ctx.getBean(Evolution.class);

        while (true) {
            Thread.sleep(1000);

            Bug bug = birthBug();

            Message<Bug> bugMessage = MessageBuilder.withPayload( bug ).build();

            Message<Bug> thriveBug = evolution.process( bugMessage );

            handleInOut.outAndVk("Бабочка " + thriveBug.getPayload().getName() + " летит!");
            handleInOut.outAndVk("Сообщение: " + thriveBug.toString());

        }
	}

	private static Bug birthBug() {
		int i = RandomUtils.nextInt(0, 9);
		return new Bug(
				BUGS[RandomUtils.nextInt(0, BUGS.length)],
				0,
				i % 2 == 0 ? 0 : 1);
	}

}
