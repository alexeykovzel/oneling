package com.alexeykovzel.features;

import com.alexeykovzel.db.models.Definition;

import java.util.*;

public class PreviewFactory {

    public String getDefinitionPreview(List<Definition> definitions, int limit) {
        List<String> formattedDefinitions = new ArrayList<>();
        // determine the number of definitions in the preview
        int definitionCount = Math.min(limit, definitions.size());

        // return preview with formatted definitions
        for (int i = 0; i < definitionCount; i++) {
            Definition definition = definitions.get(i);
            String partOfSpeech = definition.getPartOfSpeech();
            String partOfSpeechValue = (partOfSpeech == null) ? ""
                    : String.format("*[%s]* ", partOfSpeech.toUpperCase());
            formattedDefinitions.add(partOfSpeechValue + definition.getValue());
        }
        return String.join("\n\n", formattedDefinitions);
    }

    public String getExamplePreview(List<String> examples, int limit) {
        limit = Math.min(examples.size(), limit);
        List<String> formattedExamples = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            formattedExamples.add("- " + examples.get(i));
        }
        return String.join("\n", formattedExamples);
    }

    public String getTranslationPreview(List<String> translations) {
        return String.join(" - ", translations);
    }
}
