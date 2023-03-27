package ru.otus.spring.integration.Util;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import ru.otus.spring.integration.domain.Bug;

import static org.assertj.core.api.Assertions.*;
import static ru.otus.spring.integration.Util.Util.getDescription;

class UtilTest {

    @Test
    void getDescriptionTest() {
        String expected = "test-fly: трансформировалась в стадию прекрасной бабочки, летает днем";

        Bug bug = new Bug("test-fly", 2, 1);

        Message<Bug> bugMessage = MessageBuilder.withPayload( bug ).build();

        String actual = getDescription(bugMessage);

        assertThat(actual).isEqualTo(expected);
    }
}