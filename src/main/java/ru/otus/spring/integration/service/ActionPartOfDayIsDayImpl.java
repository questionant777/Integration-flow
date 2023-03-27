package ru.otus.spring.integration.service;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.Bug;

import static ru.otus.spring.integration.Util.Util.getDescription;

@Service
public class ActionPartOfDayIsDayImpl implements ActionPartOfDayIsDay {
    private final HandleInOut handleInOut;

    public ActionPartOfDayIsDayImpl(HandleInOut handleInOut) {
        this.handleInOut = handleInOut;
    }

    public void showWhatDo(Message<Bug> mess) {
        handleInOut.outAndVk(getDescription(mess));
    }

}
