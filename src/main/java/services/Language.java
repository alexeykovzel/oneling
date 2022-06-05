package services;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Language {
    ENGLISH(0),
    RUSSIAN(1),
    DUTCH(2);

    private final int key;
}