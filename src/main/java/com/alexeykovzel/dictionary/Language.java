package com.alexeykovzel.dictionary;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Language {
    DUTCH("nl"),
    ENGLISH("en"),
    RUSSIAN("ru");

    private final String key;
}