package ru.otus.spring.integration.config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import ru.otus.spring.integration.domain.Bug;

import static ru.otus.spring.integration.config.ChannelConstants.*;

@MessagingGateway
public interface Evolution {

    @Gateway(requestChannel = START_CHANNEL, replyChannel = THRIVE_CHANNEL)
    Message<Bug> process(Message<Bug> message);
}
