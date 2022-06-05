package services;

import db.entity.Definition;
import services.api.WordsAPI;

import java.util.List;
import java.util.Set;

public class Dictionary {
    public List<String> getTranslations(String word, Language from, Language to) {
        return null;
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