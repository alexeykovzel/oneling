package services;

import db.entity.Definition;
import services.api.WordsAPI;

import java.util.Set;

public class Dictionary {
    public Set<String> getTranslations(String word, Language from, Language to) {
        return null;
    }

    public Set<Definition> getDefinitions(String word, Language from) {
        if (from == Language.ENGLISH) {
            return new WordsAPI().getDefinitions(word);
        }
        return null;
    }

    public Set<String> getExamples(String word, Language from) {
        if (from == Language.ENGLISH) {
            return new WordsAPI().getExamples(word);
        }
        return null;
    }
}