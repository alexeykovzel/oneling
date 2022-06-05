package bot.handlers;

import bot.config.EmojiConfig;
import bot.features.CallbackConverter;
import bot.features.query.QueryType;
import bot.features.PreviewFactory;
import bot.features.query.Query;
import bot.features.command.SetLanguageCommand;
import bot.features.command.HelpCommand;
import bot.features.command.StartCommand;
import db.entity.Definition;
import services.Language;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import services.Dictionary;
import bot.utils.KeyboardBuilder;

import java.util.Set;

@Component
public class OnelingBotHandler extends DefaultBotHandler {
    private final Dictionary dictionary = new Dictionary();
    private final PreviewFactory previewFactory = new PreviewFactory();
    private final CallbackConverter encoder = new CallbackConverter();

    @Override
    public void handleInvalidCommandUpdate(Update update) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        sender.sendDefaultMessage("I don't know such command", chatId);
    }

    @Override
    public void handleCallbackQuery(Update update) {
        CallbackQuery callback = update.getCallbackQuery();
        String chatId = callback.getMessage().getChatId().toString();

        // Show "typing..." while processing user's query
        sender.sendTypingAction(chatId);

        // Process user's query using callback data
        Query query = encoder.decode(callback.getData());
        String[] args = query.getArgs();
        String response = null;
        switch (query.getAction()) {
            case GET_TRANSLATIONS:
                Set<String> translations = dictionary.getTranslations(args[0], Language.ENGLISH, Language.RUSSIAN);
                if (translations != null) response = previewFactory.getTranslationPreview(translations);
                break;
            case GET_EXAMPLES:
                Set<String> examples = dictionary.getExamples(args[0], Language.ENGLISH);
                if (examples != null) response = previewFactory.getExamplePreview(examples);
                break;
        }
        response = response == null ? "Failed to process a callback..." : response;
        sender.sendDefaultMessage(response, chatId);
        sender.answerCallback(callback.getId());
    }

    @Override
    public void handleNonTextMessage(Message message) {
        String chatId = message.getChatId().toString();
        sender.sendDefaultMessage("Send pls text", chatId);
    }

    @Override
    public void handleTextMessage(Message message) {
        String chatId = message.getChatId().toString();
        String word = message.getText();
        Set<Definition> definitions = dictionary.getDefinitions(word, Language.ENGLISH);
        if (definitions != null && !definitions.isEmpty()) {

            // Build a word preview with its definitions
            String preview = previewFactory.getDefinitionPreview(definitions, 3);

            // Send a message with options to translate or get examples
            sender.sendDefaultMessage(preview, chatId, new KeyboardBuilder().row()
                    .button("Translate", encoder.encode(QueryType.GET_TRANSLATIONS, word))
                    .button("Get Examples", encoder.encode(QueryType.GET_EXAMPLES, word)).build());
        } else {
            // Send error message if no definitions found
            sender.sendDefaultMessage(String.format("Ahh, I don't know what is '*%s*' %s",
                    word, EmojiConfig.DISAPPOINTED_BUT_RELIEVED_FACE), chatId);
        }
    }

    @Override
    public void initCommandRegistry() {
        HelpCommand helpCommand = new HelpCommand();
        commandRegistry.registerAll(helpCommand, new StartCommand(helpCommand), new SetLanguageCommand());

        // handle invalid commands by sending an error & help message
        commandRegistry.registerDefaultAction((absSender, message) -> {
            String chatId = message.getChatId().toString();
            sender.sendDefaultMessage(String.format("The command '%s' is not known by this bot. " +
                    "Here comes some help %s", message.getText(), EmojiConfig.AMBULANCE), chatId);
            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[]{});
        });
    }
}
