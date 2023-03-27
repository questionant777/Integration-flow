package ru.otus.spring.integration.Util;

import org.springframework.messaging.Message;
import ru.otus.spring.integration.domain.Bug;

public abstract class Util {
    public static String getDescription(Message<Bug> mess) {
        Bug bug = mess.getPayload();

        String s = bug.getName() + ": ";

        if (bug.getStage() == 0)
            s += "рождена в стадии гусеницы, ползает " + (bug.getActionPartOfDay() == 0 ? "ночью":"днем");
        else if (bug.getStage() == 1)
            s += "ировалась в стадию кокона";
        else
            s += "трансформировалась в стадию прекрасной бабочки, летает " + (bug.getActionPartOfDay() == 0 ? "ночью":"днем");

        return s;
    }
}
