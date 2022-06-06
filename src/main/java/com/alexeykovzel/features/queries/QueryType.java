package com.alexeykovzel.features.queries;

import com.alexeykovzel.utils.Emoji;
import com.alexeykovzel.features.PreviewFactory;
import com.alexeykovzel.utils.MessageSender;
import com.alexeykovzel.db.models.Definition;
import com.alexeykovzel.features.dictionary.OnelingDictionary;
import com.github.pemistahl.lingua.api.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;
import java.util.function.BiConsumer;

@Getter
@AllArgsConstructor
public enum QueryType {
    /**
     * Translates a word.
     */
    TRANSLATE("1", ((sender, callback) -> {
        String[] args =  Query.decode(callback.getData()).getArgs();
        String word = args[0];

        List<String> translations = new OnelingDictionary().getTranslations(word);
        String response = (translations != null) ? new PreviewFactory().getTranslationPreview(translations)
                : "Sorry, could not find any translations for \"" + word + "\" " + Emoji.CRYING_FACE;

        sender.sendDefaultMessage(response, getChatId(callback));
        sender.answerCallback(callback.getId());
    })),

    /**
     * Sends definitions on a word.
     */
    GET_DEFINITIONS("2",((sender, callback) -> {
        String[] args =  Query.decode(callback.getData()).getArgs();
        String word = args[0];

        List<Definition> definitions = new OnelingDictionary().getDefinitions(word, Language.ENGLISH);
        String response = (definitions != null) ? new PreviewFactory().getDefinitionPreview(definitions, 5)
                : "Sorry, could not find any definitions for \"" + word + "\" " + Emoji.CRYING_FACE;

        sender.sendDefaultMessage(response, getChatId(callback));
        sender.answerCallback(callback.getId());
    })),

    /**
     * Gets examples on a word.
     */
    GET_EXAMPLES("3", ((sender, callback) -> {
        String[] args =  Query.decode(callback.getData()).getArgs();
        String word = args[0];

        List<String> examples = new OnelingDictionary().getExamples(word, Language.ENGLISH);
        String response = (examples != null) ? new PreviewFactory().getExamplePreview(examples, 5)
                : "Sorry, could not find any examples for \"" + word + "\" " + Emoji.CRYING_FACE;

        sender.sendDefaultMessage(response, getChatId(callback));
        sender.answerCallback(callback.getId());
    })),

    /**
     * Sets translation language.
     */
    SET_LANGUAGE("4", ((sender, callback) -> {

    }));

    private final String id;
    private final BiConsumer<MessageSender, CallbackQuery> handler;

    public void execute(MessageSender sender, CallbackQuery callback) {
        handler.accept(sender, callback);
    }

    public static QueryType byId(String id) {
        for (QueryType queryType : values()) {
            if (queryType.id.equals(id)) {
                return queryType;
            }
        }
        return null;
    }

    private static String getChatId(CallbackQuery callback) {
        return callback.getMessage().getChatId().toString();
    }
}