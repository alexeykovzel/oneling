package bot.features.query.handler;

import bot.features.CallbackConverter;
import bot.features.PreviewFactory;
import bot.features.query.Query;
import bot.features.query.QueryType;
import services.Dictionary;
import services.Language;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import bot.utils.MessageSender;

import java.util.List;
import java.util.Set;

public class TranslateQuery implements QueryHandler {
    private final MessageSender sender;

    public TranslateQuery(MessageSender sender) {
        this.sender = sender;
    }

    @Override
    public void execute(CallbackQuery callback) {
        String chatId = callback.getMessage().getChatId().toString();

        // Show "typing..." while processing user's query
        sender.sendTypingAction(chatId);

        // Get user's query from callback data
        Query query = new CallbackConverter().decode(callback.getData());
        String[] args = query.getArgs();

        // Send translations to the user chat
        List<String> translations = new Dictionary().getTranslations(args[0], Language.ENGLISH, Language.RUSSIAN);
        if (translations != null) {
            String preview = new PreviewFactory().getTranslationPreview(translations);
            sender.sendDefaultMessage(preview, chatId);
        }
    }

    @Override
    public QueryType getType() {
        return QueryType.GET_TRANSLATIONS;
    }
}
