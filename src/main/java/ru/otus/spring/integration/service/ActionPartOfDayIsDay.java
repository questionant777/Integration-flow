package ru.otus.spring.integration.service;

import org.springframework.messaging.Message;
import ru.otus.spring.integration.domain.Bug;

public interface ActionPartOfDayIsDay {
    void showWhatDo(Message<Bug> mess);
}
