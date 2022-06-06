package com.alexeykovzel.features.dictionary;

import com.alexeykovzel.db.models.Definition;
import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;

import java.util.List;

public class OnelingDictionary {

    public List<String> getTranslations(String word) {
        LanguageDetector detector = LanguageDetectorBuilder
                .fromLanguages(Language.ENGLISH, Language.RUSSIAN).build();
        Language from = detector.detectLanguageOf(word);
        Language to = (from == Language.ENGLISH) ? Language.RUSSIAN : Language.ENGLISH;
        return new GoogleTranslateAPI().translate(word, getISOString(to));
    }

    public List<String> getTranslations(String word, Language to) {
        return new GoogleTranslateAPI().translate(word, getISOString(to));
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

    private String getISOString(Language language) {
        return language.getIsoCode639_3().name().toLowerCase();
    }
}