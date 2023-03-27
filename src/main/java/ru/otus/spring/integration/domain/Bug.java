package ru.otus.spring.integration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Bug {
    private final String name;
    private final Integer stage; //-0-гусен,1-кокон,2-бабочка
    private final Integer actionPartOfDay; //-0-ночью,1-днем
}
