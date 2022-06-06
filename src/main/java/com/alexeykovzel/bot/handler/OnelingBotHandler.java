package com.alexeykovzel.bot.handler;

import com.alexeykovzel.bot.config.Emoji;
import com.alexeykovzel.bot.feature.query.QueryType;
import com.alexeykovzel.bot.feature.PreviewFactory;
import com.alexeykovzel.bot.feature.query.Query;
import com.alexeykovzel.bot.feature.command.SetLanguageCommand;
import com.alexeykovzel.bot.feature.command.HelpCommand;
import com.alexeykovzel.bot.feature.command.StartCommand;
import com.alexeykovzel.bot.util.Keyboard;
import com.alexeykovzel.database.entity.Definition;
import com.alexeykovzel.dictionary.Dictionary;
import com.alexeykovzel.dictionary.Language;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class OnelingBotHandler extends DefaultBotHandler {
    private final Dictionary dictionary = new Dictionary();
    private final PreviewFactory previewFactory = new PreviewFactory();

    @Override
    public void handleInvalidCommandUpdate(Update update) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        sender.sendDefaultMessage("I don't know such command", chatId);
    }

    @Override
    public void handleCallbackQuery(Update update) {
        CallbackQuery callback = update.getCallbackQuery();
        Query query = Query.decode(callback.getData());
        query.getType().execute(sender, callback);
    }

    @Override
    public void handleNonTextMessage(Message message) {
        String chatId = message.getChatId().toString();
        sender.sendDefaultMessage("Send please text", chatId);
    }

    @Override
    public void handleTextMessage(Message message) {
        String chatId = message.getChatId().toString();
        String word = message.getText();
        List<Definition> definitions = dictionary.getDefinitions(word, Language.ENGLISH);
        if (definitions != null && !definitions.isEmpty()) {

            // Build a word preview with its definitions
            String preview = previewFactory.getDefinitionPreview(definitions, 3);

            // Send a message with options to translate or get examples
            sender.sendDefaultMessage(preview, chatId, new Keyboard().addRow()
                    .addButton("Translate", Query.encode(QueryType.TRANSLATE, word))
                    .addButton("Get Examples", Query.encode(QueryType.GET_EXAMPLES, word)).build());
        } else {
            // Send error message if no definitions found
            sender.sendDefaultMessage(String.format("Ahh, I don't know what is '*%s*' %s",
                    word, Emoji.DISAPPOINTED_BUT_RELIEVED_FACE), chatId);
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
                    "Here comes some help %s", message.getText(), Emoji.AMBULANCE), chatId);
            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[]{});
        });
    }
}
