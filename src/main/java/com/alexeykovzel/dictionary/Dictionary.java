package com.alexeykovzel.dictionary;

import com.alexeykovzel.dictionary.api.GoogleTranslateAPI;
import com.alexeykovzel.dictionary.api.WordsAPI;
import com.alexeykovzel.database.entity.Definition;

import java.util.List;

public class Dictionary {

    public List<String> getTranslations(String word, Language to) {
        return new GoogleTranslateAPI().translate(word, to.getKey());
    }

    public List<Definition> getDefinitions(String word, Language from) {
        if (from == Language.ENGLISH) {
            return new WordsAPI().getDefinitions(word);
        }
        return null;
    }

    public List<String> getExamples(String word, Language from) {
        if (from == Language.ENGLISH) {
            return new WordsAPI().getExamples(word);
        }
        return null;
    }
}