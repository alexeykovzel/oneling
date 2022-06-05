package bot.handlers;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotHandler {
    void handleInvalidCommandUpdate(Update update);

    void handleCallbackQuery(Update update);

    void handleNonTextMessage(Message message);

    void handleTextMessage(Message message);
}
