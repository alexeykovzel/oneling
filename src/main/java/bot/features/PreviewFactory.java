package bot.features;

import db.entity.Definition;

import java.util.*;
import java.util.stream.Collectors;

public class PreviewFactory {
    public String getDefinitionPreview(Set<Definition> definitions, int max) {
        List<Definition> definitionList = new ArrayList<>(definitions);
        List<String> formattedDefinitions = new ArrayList<>();
        // determine the number of definitions in the preview
        int definitionCount = Math.min(max, definitions.size());

        // return preview with formatted definitions
        for (int i = 0; i < definitionCount; i++) {
            Definition definition = definitionList.get(i);
            String partOfSpeech = definition.getPartOfSpeech();
            String partOfSpeechValue = (partOfSpeech == null) ? ""
                    : String.format("*[%s]* ", partOfSpeech.toUpperCase());
            formattedDefinitions.add(partOfSpeechValue + definition.getValue());
        }
        return String.join("\n\n", formattedDefinitions);
    }

    public String getExamplePreview(Set<String> examples) {
        return examples.stream().map(example -> "- " + example)
                .collect(Collectors.joining("\n"));
    }

    public String getTranslationPreview(Set<String> translations) {
        return String.join(" - ", translations);
    }
}
